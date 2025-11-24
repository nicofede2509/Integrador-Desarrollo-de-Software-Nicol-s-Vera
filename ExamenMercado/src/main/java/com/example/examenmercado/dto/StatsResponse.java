package com.example.examenmercado.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Estadísticas de verificaciones de ADN´s")
public class StatsResponse {
    @JsonProperty("count_human_dna")
    @Schema(description = "Cantidad de ADN mutante verificado")
    private Long contadorHumanos;
    @Schema(description = "Cantidad de ADN humano verificado")
    @JsonProperty("count_mutant_dna")
    private Long contadorMutantes;
    @Schema(description = "Ratio: mutantes / humanos")
    private double ratio;



}
