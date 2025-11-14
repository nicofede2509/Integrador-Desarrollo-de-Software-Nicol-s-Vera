package com.example.examenmercado.service;

import com.example.examenmercado.dto.StatsResponse;
import com.example.examenmercado.repository.DnaRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatsService {
    private final DnaRecordRepository repositorio;

    public StatsResponse getRatio(){
        long contadorMutantes = repositorio.countByIsMutant(true);
        long contadorHumanos = repositorio.countByIsMutant(false);
        double ratio = (contadorHumanos == 0) ? contadorMutantes : (double) contadorMutantes/contadorHumanos;
        return new StatsResponse(contadorHumanos, contadorMutantes, ratio);
    }
}
