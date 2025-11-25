package com.example.examenmercado.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Slf4j
@Service
public class MutantDetector {
    public boolean isMutant(String[] adn){
        // Validamos la cadena ingresada, esto es solo para los Tests unitarios con JUnit.
        if (!validarDatos(adn)) {
            return false;
        }
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
                    log.debug("La cadena corresponde a la de un MUTANTE.");
                    return true;
                }
            }
        }
        log.debug("La cadena corresponde a un humano.");
        return false;
    }

    private boolean checkHorizontal(char[][] matrix, int row, int col) {
        final char base = matrix[row][col];
        boolean found = matrix[row][col + 1] == base &&
                matrix[row][col + 2] == base &&
                matrix[row][col + 3] == base;
        if (found) {
            log.debug("Horizontal encontrada: {}{}{}{} en Fila {} Col {}", base, base, base, base, row, col);
        }
        return found;
    }

    private boolean checkVertical(char[][] matrix, int row, int col) {
        final char base = matrix[row][col];
        boolean found = matrix[row + 1][col] == base &&
                matrix[row + 2][col] == base &&
                matrix[row + 3][col] == base;
        if (found) {
            log.debug("Vertical encontrada: {}{}{}{} en Fila {}, Col {}", base, base, base, base, row, col);
        }
        return found;
    }

    private boolean checkDiagonal(char[][] matrix, int row, int col) {
        final char base = matrix[row][col];
        boolean found = matrix[row + 1][col + 1] == base &&
                matrix[row + 2][col + 2] == base &&
                matrix[row + 3][col + 3] == base;
        if (found) {
            log.debug("Diagonal encontrada: {}{}{}{} en Fila {} Col {}", base, base, base, base, row, col);
        }
        return found;
    }

    private boolean checkDiagonalContraria(char[][] matrix, int row, int col) {
        final char base = matrix[row][col];
        boolean found = matrix[row - 1][col + 1] == base &&
                matrix[row - 2][col + 2] == base &&
                matrix[row - 3][col + 3] == base;
        if (found) {
            log.debug("Diagonal encontrada: {}{}{}{} en Fila {} Col {}", base, base, base, base, row, col);
        }
        return found;
    }

    private boolean validarDatos(String[] adn){
        final Pattern DNA_PATTERN = Pattern.compile("^[ATCG]+$");
        final int MIN_SIZE = 4;
        if (adn == null || adn.length == 0) {
            return false;
        }

        int n = adn.length;
        if (n < MIN_SIZE) {
            return false;
        }

        for (String row : adn) {
            if (row == null || row.length() != n) {
                return false;
            }
            if (!DNA_PATTERN.matcher(row).matches()) {
                return false;
            }
        }
        return true;
    }
}