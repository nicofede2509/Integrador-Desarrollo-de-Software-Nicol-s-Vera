package com.example.examenmercado.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class ErrorResponse {
    private LocalDateTime timestamp;
    private int codigoStatus;
    private String error;
    private String path;
}
