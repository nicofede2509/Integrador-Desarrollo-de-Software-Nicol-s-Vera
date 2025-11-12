package com.example.examenmercado.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class ValidDnaSequenceValidator implements ConstraintValidator<ValidDnaSequence, String[]> {

    private static final Pattern DNA_PATTERN = Pattern.compile("^[ATCG]+$");
    private static final int MIN_SIZE = 4;

    @Override
    public void initialize(ValidDnaSequence constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String[] dna, ConstraintValidatorContext context) {

        if(dna == null || dna.length == 0){
            return false;
        }

        final int n = dna.length;
        if (n < MIN_SIZE) {
            return false;
        }
        for (String row : dna) {

            if (row == null || row.length() != n) {
                return false;
            }

            for (char c : row.toCharArray()) {
                if (c != 'A' && c != 'T' && c != 'C' && c != 'G') {
                    return false;
                }
            }
        }
        return true;
    }
}