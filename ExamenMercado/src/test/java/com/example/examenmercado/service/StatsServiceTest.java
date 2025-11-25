package com.example.examenmercado.service;

import com.example.examenmercado.dto.StatsResponse;
import com.example.examenmercado.repository.DnaRecordRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StatsServiceTest {

    @Mock //Crea una base de datos ficticia, asì evitamos usar una base de datos real.
    private DnaRecordRepository dnaRecordRepository;

    @InjectMocks //Inyecta el mock en la clase que vamos a probar.
    private StatsService statsService;

    @Test
    @DisplayName("Debe calcular el ratio correctamente (ej: 40/100 = 0.4)")
    public void testGetStatsConDatos() {
        when(dnaRecordRepository.countByIsMutant(true)).thenReturn(40L);
        when(dnaRecordRepository.countByIsMutant(false)).thenReturn(100L);

        StatsResponse stats = statsService.getStats(null, null);

        assertEquals(40, stats.getContadorMutantes());
        assertEquals(100, stats.getContadorHumanos());
        assertEquals(0.4, stats.getRatio());
    }

    @Test
    @DisplayName("Debe manejar la división por cero.")
    void testGetStatsSinHumanos() {
        when(dnaRecordRepository.countByIsMutant(true)).thenReturn(50L);
        when(dnaRecordRepository.countByIsMutant(false)).thenReturn(0L);

        StatsResponse stats = statsService.getStats(null, null);

        assertEquals(50, stats.getContadorMutantes());
        assertEquals(0, stats.getContadorHumanos());
        assertEquals(50.0, stats.getRatio());
    }

    @Test
    @DisplayName("Debe devolver 0.0 si no hay datos en la Base de Datos.")
    void testGetStatsSinDatos() {
        when(dnaRecordRepository.countByIsMutant(true)).thenReturn(0L);
        when(dnaRecordRepository.countByIsMutant(false)).thenReturn(0L);

        StatsResponse stats = statsService.getStats(null, null);

        assertEquals(0, stats.getContadorMutantes());
        assertEquals(0, stats.getContadorHumanos());
        assertEquals(0.0, stats.getRatio());
    }

    @Test
    @DisplayName("Debe calcular el ratio con decimales correctamente.")
    void testGetStatsConRatioDecimal() {
        when(dnaRecordRepository.countByIsMutant(true)).thenReturn(1L);
        when(dnaRecordRepository.countByIsMutant(false)).thenReturn(3L);

        StatsResponse stats = statsService.getStats(null, null);

        assertEquals(1, stats.getContadorMutantes());
        assertEquals(3, stats.getContadorHumanos());
        assertEquals(1.0 / 3.0, stats.getRatio());
    }

    @Test
    @DisplayName("Debe calcular el ratio 1.0 si los contadores son iguales")
    void testGetStatsConRatioUno() {
        when(dnaRecordRepository.countByIsMutant(true)).thenReturn(75L);
        when(dnaRecordRepository.countByIsMutant(false)).thenReturn(75L);

        StatsResponse stats = statsService.getStats(null, null);

        assertEquals(75, stats.getContadorMutantes());
        assertEquals(75, stats.getContadorHumanos());
        assertEquals(1.0, stats.getRatio());
    }
}