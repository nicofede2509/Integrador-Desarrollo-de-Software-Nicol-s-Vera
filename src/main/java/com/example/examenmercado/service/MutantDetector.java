package com.example.examenmercado.service;

import org.springframework.stereotype.Service;

@Service
public class MutantDetector {
    public boolean isMutant(String[] adn){
        /*if (!isValidDna(dna)){
            return false;
        }*/
        int contador = 0;
        int n = adn.length;
        char[][] secuenciaAdn = new char[n][n];
        for (int i = 0; i < n; i++) {
            secuenciaAdn[i] = adn[i].toCharArray();
        }
        final int SEQUENCE_LENGTH = 4;
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                if(j <= n - SEQUENCE_LENGTH){
                    if(checkHorizontal(secuenciaAdn, i, j)){
                        contador++;
                    }
                }
                if (i <= n - SEQUENCE_LENGTH) {
                    if (checkVertical(secuenciaAdn, i, j)) {
                        contador++;
                    }
                }
                if (i <= n - SEQUENCE_LENGTH && j <= n - SEQUENCE_LENGTH) {
                    if (checkDiagonal(secuenciaAdn, i, j)) {
                        contador++;
                    }
                }
                if (i >= SEQUENCE_LENGTH - 1 && j <= n - SEQUENCE_LENGTH) {
                    if (checkDiagonalContraria(secuenciaAdn, i, j)) {
                        contador++;
                    }
                }
                if (contador>1){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkHorizontal(char[][] matrix, int row, int col) {
        final char base = matrix[row][col];
        return matrix[row][col + 1] == base &&
                matrix[row][col + 2] == base &&
                matrix[row][col + 3] == base;
    }

    private boolean checkVertical(char[][] matrix, int row, int col) {
        final char base = matrix[row][col];
        return matrix[row + 1][col] == base &&
                matrix[row + 2][col] == base &&
                matrix[row + 3][col] == base;
    }

    private boolean checkDiagonal(char[][] matrix, int row, int col) {
        final char base = matrix[row][col];
        return matrix[row + 1][col + 1] == base &&
                matrix[row + 2][col + 2] == base &&
                matrix[row + 3][col + 3] == base;
    }

    private boolean checkDiagonalContraria(char[][] matrix, int row, int col) {
        final char base = matrix[row][col];
        return matrix[row - 1][col + 1] == base &&
                matrix[row - 2][col + 2] == base &&
                matrix[row - 3][col + 3] == base;
    }
}