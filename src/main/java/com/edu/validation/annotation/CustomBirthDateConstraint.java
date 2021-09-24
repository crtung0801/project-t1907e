package com.edu.validation.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.edu.validation.validator.CustomBirthDateValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CustomBirthDateValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomBirthDateConstraint {

    String message() default "Birth day not null";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
