package com.example.examenmercado.service;

import com.example.examenmercado.dto.StatsResponse;
import com.example.examenmercado.repository.DnaRecordRepository;
import org.springframework.stereotype.Service;

@Service
public class StatsService {
    private final DnaRecordRepository repositorio;

    public StatsService(DnaRecordRepository repositorio) {
        this.repositorio = repositorio;
    }

    public StatsResponse getRatio(){
        long contadorMutantes = repositorio.countByIsMutant(true);
        long contadorHumanos = repositorio.countByIsMutant(false);
        double ratio = (contadorHumanos == 0) ? contadorMutantes : (double) contadorMutantes/contadorHumanos;
        return new StatsResponse(contadorMutantes, contadorHumanos, ratio);
    }
}
