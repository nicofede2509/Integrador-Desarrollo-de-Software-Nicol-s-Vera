package com.example.examenmercado.service;

import com.example.examenmercado.entity.DnaRecord;
import com.example.examenmercado.repository.DnaRecordRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MutantServiceTest {

    @Mock //Simula un detector de mutantes.
    private MutantDetector mutantDetector;

    @Mock // Simulador una base de datos para evitar usar la base de datos real.
    private DnaRecordRepository repository;

    @InjectMocks // Clase que se va a testear.
    private MutantService mutantService;

    // Datos de prueba.
    private final String[] dnaMutante = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
    private final String[] dnaHumano = {"ATGCGA", "CAGTGC", "TTATTT", "AGACGG", "GCGTCA", "TCACTG"};

    @Test
    @DisplayName("Debe analizar un ADN mutante nuevo y guardarlo en la Base de Datos.")
    void testAnalizarAdnMutanteNuevo() {
        when(repository.findByDnaHash(anyString())).thenReturn(Optional.empty());
        when(mutantDetector.isMutant(dnaMutante)).thenReturn(true);

        boolean resultado = mutantService.analyzeDna(dnaMutante);

        assertTrue(resultado, "El resultado debería ser mutante (true)");

        verify(mutantDetector, times(1)).isMutant(dnaMutante);
        verify(repository, times(1)).save(any(DnaRecord.class));
    }

    @Test
    @DisplayName("Debe analizar un ADN humano nuevo y guardarlo en la Base de Datos.")
    void testAnalizarAdnHumanoNuevo() {
        when(repository.findByDnaHash(anyString())).thenReturn(Optional.empty());
        when(mutantDetector.isMutant(dnaHumano)).thenReturn(false);

        boolean resultado = mutantService.analyzeDna(dnaHumano);

        assertFalse(resultado, "El resultado debería ser humano (false)");

        verify(mutantDetector, times(1)).isMutant(dnaHumano);
        verify(repository, times(1)).save(any(DnaRecord.class));
    }

    @Test
    @DisplayName("Debe devolver resultado de la cachè si el ADN ya fue analizado")
    void testDevolverResultadoDeCache() {
        DnaRecord registroCacheado = DnaRecord.builder()
                .dnaHash("hash_existente")
                .isMutant(true)
                .createdAt(LocalDateTime.now())
                .build();

        when(repository.findByDnaHash(anyString())).thenReturn(Optional.of(registroCacheado));

        boolean resultado = mutantService.analyzeDna(dnaMutante);

        assertTrue(resultado, "Debería devolver true desde la caché");

        verify(mutantDetector, never()).isMutant(any());
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Debe guardar el registro con el Hash y el resultado correctos")
    void testGuardarRegistroCorrectamente() {
        when(repository.findByDnaHash(anyString())).thenReturn(Optional.empty());
        when(mutantDetector.isMutant(dnaHumano)).thenReturn(false);

        // Capturador de argumentos para verificar lo que se guarda en la base de datos.
        ArgumentCaptor<DnaRecord> captor = ArgumentCaptor.forClass(DnaRecord.class);

        mutantService.analyzeDna(dnaHumano);

        verify(repository).save(captor.capture());
        DnaRecord recordGuardado = captor.getValue();

        assertNotNull(recordGuardado.getDnaHash(), "El Hash no puede ser nulo");
        assertEquals(64, recordGuardado.getDnaHash().length(), "El Hash SHA-256 debe tener 64 caracteres");
        assertFalse(recordGuardado.isMutant(), "Se debería haber guardado como humano (false)");
    }
}