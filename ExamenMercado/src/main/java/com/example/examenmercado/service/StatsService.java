package com.example.examenmercado.service;

import com.example.examenmercado.dto.StatsResponse;
import com.example.examenmercado.repository.DnaRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class StatsService {
    private final DnaRecordRepository repositorio;

    public StatsResponse getStats(LocalDateTime fechaInicio, LocalDateTime fechaFin){
        long contadorMutantes = 0;
        long contadorHumanos = 0;
        if(fechaInicio != null && fechaFin != null){
            LocalDateTime inicio = fechaInicio.toLocalDate().atStartOfDay();
            LocalDateTime fin = fechaFin.toLocalDate().atTime(LocalTime.MAX);
            contadorMutantes = repositorio.countByIsMutantAndCreatedAtBetween(true, inicio, fin);
            contadorHumanos = repositorio.countByIsMutantAndCreatedAtBetween(false, inicio, fin);
        }else{
            contadorMutantes = repositorio.countByIsMutant(true);
            contadorHumanos = repositorio.countByIsMutant(false);
        }
        double ratio = (contadorHumanos == 0) ? contadorMutantes : (double) contadorMutantes/contadorHumanos;
        return new StatsResponse(contadorHumanos, contadorMutantes, ratio);
    }
}
