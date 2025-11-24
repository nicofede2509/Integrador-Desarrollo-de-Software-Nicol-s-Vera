package com.example.examenmercado.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Builder
@Table(name = "dna_records")
@NoArgsConstructor
@AllArgsConstructor
public class DnaRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "is_mutant", nullable = false)
    private boolean isMutant;

    @Column(name = "dna_hash", unique = true, nullable = false)
    private String dnaHash;
}
