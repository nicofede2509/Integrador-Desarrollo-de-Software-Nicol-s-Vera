package com.example.examenmercado.controller;

import com.example.examenmercado.dto.DnaRequest;
import com.example.examenmercado.dto.StatsResponse;
import com.example.examenmercado.service.MutantService;
import com.example.examenmercado.service.StatsService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


@RestController
@RequiredArgsConstructor
@Tag(name = "Detector de mutantes", description = "API para detección de mutantes")
public class MutantController {
    private final MutantService mutantService;
    private final StatsService statsService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mutante detectado."),
            @ApiResponse(responseCode = "403", description = "Humano detectado."),
            @ApiResponse(responseCode = "400", description = "ADN inválido."),
    })
    @PostMapping("/mutant")
    public ResponseEntity<Void> checkMutant(@Validated @RequestBody DnaRequest request){ // Aquí se usan ValidDnaSequence para la capa de APIS.
        boolean isMutant = mutantService.analyzeDna(request.getDna());
        return isMutant ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @GetMapping("/stats")
    public ResponseEntity<StatsResponse> getStats(
            @RequestParam(required = false) String dateStart,
            @RequestParam(required = false) String dateEnd
    ) {
        // Convertimos manualmente de String a LocalDate para evitar errores automáticos 400
        LocalDate start = (dateStart != null) ? LocalDate.parse(dateStart) : null;
        LocalDate end = (dateEnd != null) ? LocalDate.parse(dateEnd) : null;

        StatsResponse stats = statsService.getStats(start, end);
        return ResponseEntity.ok(stats);
    }

    @DeleteMapping("/mutant")
    public ResponseEntity<Void> deleteMutant(@Validated @RequestBody DnaRequest request){
        mutantService.deleteDnaRecord(request.getDna());
        return ResponseEntity.noContent().build();
    }

}
