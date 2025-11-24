package com.example.examenmercado.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidDnaSequenceValidator.class)
public @interface ValidDnaSequence {
    String message() default "La secuencia de ADN debe ser una matriz cuadrada que contenga los caracteres A, T, C, G.";

    Class<?>[] groups() default {};

    Class<?extends Payload>[] payload() default {};
}
