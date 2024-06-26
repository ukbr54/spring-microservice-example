package com.example.authentication.validation.validator;

import com.example.authentication.validation.annotation.NullOrNotBlank;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NullOrNotBlankValidator implements ConstraintValidator<NullOrNotBlank,String> {
    @Override
    public void initialize(NullOrNotBlank constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if(null == value) return true;
        if(value.length() == 0) return false;
        boolean isAllWhitespace = value.matches("^\\s*$");
        return !isAllWhitespace;
    }
}
