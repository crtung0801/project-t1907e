package com.edu.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.edu.validation.annotation.CustomVaccineTypeNameContraint;

public class CustomVaccineTypeNameValidator implements ConstraintValidator<CustomVaccineTypeNameContraint, String> {
	@Override
	public void initialize(CustomVaccineTypeNameContraint vaccineTypeName) {
	}

	@Override
	public boolean isValid(String vaccineTypeName, ConstraintValidatorContext context) {
		if (vaccineTypeName==null) {
			return false;
		}
		if (vaccineTypeName.length() >= 6 && vaccineTypeName.length() <= 50 && vaccineTypeName!=null) {
			return true;
		}
		return false;
	}

}
