package com.example.examenmercado.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class StatsResponse {
    @JsonProperty("count_human_dna")
    private double contadorHumanos;
    @JsonProperty("count_muitant_dna")
    private double contadorMutantes;
    private double ratio;

}
