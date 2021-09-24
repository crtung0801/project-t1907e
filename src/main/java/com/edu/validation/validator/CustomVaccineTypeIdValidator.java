package com.edu.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.edu.validation.annotation.CustomVaccineTypeIdContraint;

public class CustomVaccineTypeIdValidator implements ConstraintValidator<CustomVaccineTypeIdContraint, String> {

	@Override
	public void initialize(CustomVaccineTypeIdContraint vaccineTypeId) {
	}

	@Override
	public boolean isValid(String vaccineTypeId, ConstraintValidatorContext context) {
		if (vaccineTypeId == null) {
			return false;
		}
		return true;

	}

}
