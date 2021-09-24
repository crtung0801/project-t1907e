package com.edu.validation.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.edu.validation.validator.CustomPhoneValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CustomPhoneValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomPhoneConstraint {

    String message() default "Wrong phone number!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
