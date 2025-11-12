package com.example.examenmercado.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MutantDetectorTest {
    @Test
    public void isMutantTest(){
        MutantDetector detector = new MutantDetector();
        boolean rdo = detector.isMutant(new String[]{"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"});
        assertTrue(rdo);
    }
}
