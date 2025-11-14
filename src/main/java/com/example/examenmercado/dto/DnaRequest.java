package com.example.examenmercado.dto;

import com.example.examenmercado.validation.ValidDnaSequence;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DnaRequest {
    @Schema(
            description = "Secuencia de ADN representada como matriz cuadrada de NxN",
            example = "[\"ATGCGA\",\"CAGTGC\",\"TTATGT\",\"AGAAGG\",\"CCCCTA\",\"TCACTG\"]",
            required = true
    )
    @NotEmpty(message = "La secuencia de ADN no puede estar vacía")
    @NotNull(message = "La secuencia de ADN no puede ser nula")
    @ValidDnaSequence
    private String[] dna;
}
