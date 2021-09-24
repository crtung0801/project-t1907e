package com.edu.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.edu.validation.annotation.CustomBirthDateConstraint;

public class CustomBirthDateValidator
        implements ConstraintValidator<CustomBirthDateConstraint, String> {

    @Override
    public void initialize(CustomBirthDateConstraint birthDate) {
    }

    @Override
    public boolean isValid(String birthDate, ConstraintValidatorContext constraintValidatorContext) {
        return !birthDate.isEmpty();
    }

}
