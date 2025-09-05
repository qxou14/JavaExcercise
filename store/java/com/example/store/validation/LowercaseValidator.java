package com.example.store.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

// first parameter: custom validation
// 2nd parameter : type of data we want to apply annotation on. i.e we want to apply on string
public class LowercaseValidator implements ConstraintValidator<Lowercase, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if(value == null) return true;
        return value.equals(value.toLowerCase());
    }
}
