package com.example.examenmercado.dto;

import com.example.examenmercado.validation.ValidDnaSequence;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DnaRequest {
    @NotEmpty(message = "La secuencia de ADN no puede estar vacía")
    @NotNull(message = "La secuencia de ADN no puede ser nula")
    @ValidDnaSequence
    private String[] dna;
}
