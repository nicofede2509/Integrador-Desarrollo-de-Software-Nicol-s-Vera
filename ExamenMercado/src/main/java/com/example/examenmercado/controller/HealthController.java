package com.example.examenmercado.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@Tag(name = "Health Check", description = "Endpoint para verificar el estado del servicio")
public class HealthController {
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> checkHealth(){
        Map<String, Object> response = Map.of(
                "status", "UP",
                "timestamp", LocalDateTime.now(),
                "service", "Mutant Detector API",
                "version", "1.0.0"
        );
        return ResponseEntity.ok(response);
    }
}
