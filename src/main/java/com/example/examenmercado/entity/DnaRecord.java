package com.example.examenmercado.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "dna_records")
public class DnaRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "createdAt", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(nullable = false)
    private boolean isMutant;

    @Column(unique = true, length = 64)
    private String dnaHash;
}
