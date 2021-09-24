package com.edu.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.edu.validation.annotation.CustomPhoneConstraint;

public class CustomPhoneValidator
        implements ConstraintValidator<CustomPhoneConstraint, String> {

    @Override
    public void initialize(CustomPhoneConstraint phone) {
    }

    @Override
    public boolean isValid(String phone, ConstraintValidatorContext constraintValidatorContext) {
        return phone != null && phone.matches("(84|0[3|5|7|8|9])+([0-9]{8})\\b");
    }

}
