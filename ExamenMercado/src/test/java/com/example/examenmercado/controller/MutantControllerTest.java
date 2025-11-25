package com.example.examenmercado.controller;

import com.example.examenmercado.dto.DnaRequest;
import com.example.examenmercado.dto.StatsResponse;
import com.example.examenmercado.repository.DnaRecordRepository;
import com.example.examenmercado.service.MutantService;
import com.example.examenmercado.service.StatsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MutantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MutantService mutantService;

    @MockBean
    private StatsService statsService;

    @Test
    @DisplayName("POST /mutant debe devolver 200 OK si el ADN es mutante")
    void testPostMutant_Devuelve200_SiEsMutante() throws Exception {
        String[] dna = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
        DnaRequest requestBody = new DnaRequest(dna);

        when(mutantService.analyzeDna(any())).thenReturn(true);

        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("POST /mutant debe devolver 403 Forbidden si el ADN es humano")
    void testPostMutant_Devuelve403_SiEsHumano() throws Exception {
        String[] dna = {"ATGCGA", "CAGTGC", "TTATTT", "AGACGG", "GCGTCA", "TCACTG"};
        DnaRequest requestBody = new DnaRequest(dna);

        when(mutantService.analyzeDna(any())).thenReturn(false);

        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("POST /mutant debe devolver 400 Bad Request si el ADN es NULO")
    void testPostMutant_Devuelve400_SiDnaEsNulo() throws Exception {
        String jsonBody = "{\"dna\":null}";

        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /mutant debe devolver 400 Bad Request si el ADN no es NxN")
    void testPostMutant_Devuelve400_SiDnaNoEsCuadrado() throws Exception {
        String[] dna = {"ATGC", "CAGT", "TTAT"};
        DnaRequest requestBody = new DnaRequest(dna);

        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /mutant debe devolver 400 Bad Request si tiene caracteres inválidos")
    void testPostMutant_Devuelve400_SiDnaTieneCaracteresInvalidos() throws Exception {
        String[] dna = {"ATGC", "CAGT", "TTAX", "AGAC"};
        DnaRequest requestBody = new DnaRequest(dna);

        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /stats debe devolver 200 OK y las estadísticas")
    void testGetStats_Devuelve200_Y_Estadisticas() throws Exception {
        StatsResponse statsDePrueba = new StatsResponse(100L, 40L, 0.4);

        // Usamos null, null para coincidir con el nuevo método getStats(fecha1, fecha2)
        when(statsService.getStats(null, null)).thenReturn(statsDePrueba);

        mockMvc.perform(get("/stats")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count_mutant_dna").value(40))
                .andExpect(jsonPath("$.count_human_dna").value(100))
                .andExpect(jsonPath("$.ratio").value(0.4));
    }

    @Test
    @DisplayName("DELETE /mutant debe devolver 204 No Content")
    void testDeleteMutant_Devuelve204() throws Exception {
        String[] dna = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
        DnaRequest requestBody = new DnaRequest(dna);
                mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("GET /health debe devolver 200 OK y estado UP")
    void testHealthCheck() throws Exception {
        mockMvc.perform(get("/health")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"));
    }

    @Test
    @DisplayName("GET /stats con rango de fechas debe devolver 200 OK")
    void testGetStats_ConFechas() throws Exception {
        StatsResponse statsResponse = new StatsResponse(10L, 5L, 0.5);

        when(statsService.getStats(any(), any())).thenReturn(statsResponse);

        mockMvc.perform(get("/stats")
                        .param("dateStart", "2025-01-01")
                        .param("dateEnd", "2025-12-31")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count_mutant_dna").value(5));
    }

    @Test
    @DisplayName("Test de cobertura para DTOs (ToString/Equals)")
    void testDtoCoverage() {
        DnaRequest req1 = new DnaRequest(new String[]{"ATGC"});
        DnaRequest req2 = new DnaRequest(new String[]{"ATGC"});

        assertEquals(req1, req2);
        assertEquals(req1.hashCode(), req2.hashCode());
        assertNotNull(req1.toString());
    }
}