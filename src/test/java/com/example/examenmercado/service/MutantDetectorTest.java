package com.example.examenmercado.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MutantDetectorTest {

    private MutantDetector detector;

    @BeforeEach
    @DisplayName("Prepara una nueva instancia del detector antes de cada test")
    void setUp() {
        detector = new MutantDetector();
    }


    //Test 1
    @Test
    @DisplayName("Debe detectar mutante con 2 secuencias, 1 horizontal y 1 diagonal descendente.")
    public void testEsMutante_ConSecuenciaHorizontalYDiagonalDescendente() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        boolean rdo = detector.isMutant(dna);
        assertTrue(rdo, "Esta cadena representa a un mutante.");
    }

    //Test 2
    @Test
    @DisplayName("Debe detectar mutante con 2 secuencias verticales.")
    public void testEsMutante_ConDosSecuenciasVerticales() {
        String[] dna = {
                "ATGCGA",
                "AAGTGG",
                "ATATGT",
                "AGAAGG",
                "CCCCTG",
                "TCACTG"
        };
        boolean rdo = detector.isMutant(dna);
        assertTrue(rdo, "Esta cadena representa a un mutante.");
    }

    //Test 3
    @Test
    @DisplayName("Debe detectar mutante con 2 secuencias horizontales.)")
    public void testEsMutante_ConDosSecuenciasHorizontales() {
        String[] dna = {
                "AAAAGC",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TGGGGG"
        };
        boolean rdo = detector.isMutant(dna);
        assertTrue(rdo, "Esta cadena representa a un mutante.");
    }

    //Test 4
    @Test
    @DisplayName("Debe detectar mutante con 2 secuencias diagonales cualesquiera")
    public void testEsMutante_ConDosDiagonales() {
        String[] dna = {
                "ATGTGA",
                "CATGAC",
                "TTATGT",
                "TGAAAG",
                "CCCCTA",
                "TCACTG"
        };
        boolean rdo = detector.isMutant(dna);
        assertTrue(rdo, "Esta cadena representa a un mutante.");
    }

    //Test 5
    @Test
    @DisplayName("Debe detectar mutante en la matriz 4x4 (el tamaño mínimo permitido).")
    public void testEsMutanteMatrizMinima4x4() {
        String[] dna = {
                "AAAA",
                "ATTT",
                "AGGG",
                "ACCC"
        };
        boolean rdo = detector.isMutant(dna);
        assertTrue(rdo, "Esta cadena representa a un mutante.");
    }

    //Test 6
    @Test
    @DisplayName("Debe detectar mutante si toda la matriz es igual")
    public void testEsMutanteMatrizRepletaDelMismoCaracter() {
        String[] dna = {
                "AAAAAA",
                "AAAAAA",
                "AAAAAA",
                "AAAAAA",
                "AAAAAA",
                "AAAAAA"
        };
        boolean rdo = detector.isMutant(dna);
        assertTrue(rdo, "Esta cadena representa a un mutante.");
    }

    //Test 7
    @Test
    @DisplayName("No debe detectar mutante si no existen secuencias")
    public void testEsHumanoCeroSecuencias() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATTT",
                "AGACGG",
                "GCGTCA",
                "TCACTG"
        };
        boolean rdo = detector.isMutant(dna);
        assertFalse(rdo);
    }

    //Test 8
    @Test
    @DisplayName("No debe detectar mutante si hay una única secuencia")
    public void testEsHumanoUnaSolaSecuencia() {
        String[] dnaUnaSecuencia = {
                "ATGCGA",
                "CAGTGC",
                "TTATAT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        boolean rdo = detector.isMutant(dnaUnaSecuencia);
        assertTrue(rdo);
    }

    //Test 9
    @Test
    @DisplayName("No debe detectar mutante si hay 1 secuencia de 5 o más.")
    public void testEsHumanoSecuenciasMasLargasNoCuentanDoble() {
        String[] dna = {
                "AAAAAG",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCTAG",
                "TCACTG"
        };
        boolean rdo = detector.isMutant(dna);
        assertTrue(rdo, "Las secuencias de más de 4 caracteres no influyen en el resultado final");
    }

    //Test 10
    @Test
    @DisplayName("No debe detectar mutante si hay secuencias de 3 caracteres.")
    public void testEsHumanoSecuenciasDeTresNoCuentan() {
         String[] dna = {
                "AAATGC",
                "CCCTAG",
                "GGGTAT",
                "TTTAGG",
                "CAGTCA",
                "TCACTG"
        };
        boolean rdo = detector.isMutant(dna);
        assertFalse(rdo, "ADN sin secuencias de 4 caracteres válidos.");
    }

    //Test 11
    @Test
    @DisplayName("Debe devolver false si el ADN es NULO")
    public void testValidacionDnaNulo() {
        boolean rdo = detector.isMutant(null);
        assertFalse(rdo, "ADN nulo.");
    }

    //Test 12
    @Test
    @DisplayName("Debe devolver false si el ADN está VACÍO")
    public void testValidacionDnaVacio() {
        String[] dna = {

        };
        boolean rdo = detector.isMutant(dna);
        assertFalse(rdo, "ADN vacío.");
    }

    //Test 13
    @Test
    @DisplayName("Debe devolver false si la matriz es rectangular (NxM).")
    public void testValidacionMatrizNoCuadrada() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT"
        };
        boolean rdo = detector.isMutant(dna);
        assertFalse(rdo, "Matriz de tamaño inválido.");
    }

    //Test 14
    @Test
    @DisplayName("Debe devolver false si la matriz es rectangular (MxN)")
    public void testValidacionMatrizNoCuadrada2() {
        String[] dna = {
                "ATG",
                "CAG",
                "TTA",
                "AGA",
                "CCC",
                "TCA"
        };
        boolean rdo = detector.isMutant(dna);
        assertFalse(rdo, "Matriz de tamaño inválido.");
    }

    //Test 15
    @Test
    @DisplayName("Debe devolver false si hay caracteres inválidos (ej: 'X').")
    public void testValidacionCaracteresInvalidos() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAXGG",
                "CCCCTA",
                "TCACTG"
        };
        boolean rdo = detector.isMutant(dna);
        assertFalse(rdo, "Carácter 'X' inválido.");
    }

    //Test 16
    @Test
    @DisplayName("Debe devolver false si una fila es nula")
    public void testValidacionFilaNula() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                 null,
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        boolean rdo = detector.isMutant(dna);
        assertFalse(rdo, "Un ADN con una fila nula no es válido.");
    }

    //Test 17
    @Test
    @DisplayName("Debe devolver false si la matriz es muy chica (ej: 3x3)")
    public void testValidacionMatrizInsuficiente() {
        String[] dna = {
                "ATG",
                "CAG",
                "TTA"
        };
        boolean rdo = detector.isMutant(dna);
        assertFalse(rdo, "Matriz sin cadenas de 4 caracteres, ADN inválido.");
    }
}