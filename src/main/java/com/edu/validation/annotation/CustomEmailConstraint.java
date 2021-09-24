package com.edu.validation.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.edu.validation.validator.CustomEmailValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CustomEmailValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomEmailConstraint {

    String message() default "Wrong email!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
