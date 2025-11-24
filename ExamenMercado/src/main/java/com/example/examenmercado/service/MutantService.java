package com.example.examenmercado.service;

import com.example.examenmercado.entity.DnaRecord;
import com.example.examenmercado.exception.DnaHashCalculationException;
import com.example.examenmercado.repository.DnaRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MutantService {
    private final MutantDetector mutantDetector;
    private final DnaRecordRepository repository;
    public boolean analyzeDna(String[] adn){
        String dnaHash = calculateDnaHash(adn);
        Optional<DnaRecord> existing = repository.findByDnaHash(dnaHash);
        if(existing.isPresent()){
            return existing.get().isMutant();
        }
        boolean isMutant = mutantDetector.isMutant(adn);
        DnaRecord record = DnaRecord.builder()
                .dnaHash(dnaHash)
                .isMutant(isMutant)
                .createdAt(LocalDateTime.now())
                .build();
        repository.save(record);
        return isMutant;
    }

    private String calculateDnaHash(String[] adn){
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String dnaString = String.join("", adn);
            byte[] hashBytes = digest.digest(dnaString.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        }catch (NoSuchAlgorithmException e){
            throw new DnaHashCalculationException("Error al calcular el hash", e);
        }
    }
}
