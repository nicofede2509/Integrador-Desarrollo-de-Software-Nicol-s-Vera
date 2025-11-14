package com.example.examenmercado.controller;

import com.example.examenmercado.dto.DnaRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mutants")
public class MutantController {
    @Operation(summary = "Obtiene la lista de todos los productos existentes.")
    @ApiResponse(responseCode = "200", description = "Lista obtenida de forma exitosa.")
    @GetMapping
    public ResponseEntity<List<DnaRequest>> getAllMutants() {
        return ResponseEntity.ok().body(List.of());
    }
}
