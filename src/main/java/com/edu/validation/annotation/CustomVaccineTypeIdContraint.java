package com.edu.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;


import com.edu.validation.validator.CustomVaccineTypeIdValidator;
@Documented
@Constraint(validatedBy = CustomVaccineTypeIdValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomVaccineTypeIdContraint {
	String message() default "This field must be required";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
