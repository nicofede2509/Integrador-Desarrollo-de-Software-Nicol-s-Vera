package com.example.examenmercado.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class ValidDnaSequenceValidator implements ConstraintValidator<ValidDnaSequence, String[]> {

    private static final Pattern DNA_PATTERN = Pattern.compile("^[ATCG]+$");
    private static final int MIN_SIZE = 4;

    @Override
    public boolean isValid(String[] dna, ConstraintValidatorContext context) {

        if(dna == null || dna.length == 0){
            return false;
        }

        int n = dna.length;
        if (n < MIN_SIZE) {
            return false;
        }
        for (String row : dna) {
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