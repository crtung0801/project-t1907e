package com.edu.utils;

import java.util.Arrays;
import java.util.List;

public class Constant {
	public static final String ROLE_ADMIN = "ROLE_ADMIN";
	public static final String ROLE_USER = "ROLE_USER";

	public static final int NUMBER_PER_PAGE = 5;

	public static final List<String> contentTypes = Arrays.asList("png", "jpeg", "gif", "jpg");

	public static final String SCHEDULE_NOT_FOUND = "Schedule not found for the id: ";
	public static final String EMPLOYEE_NOT_FOUND = "Employee not found for the id: ";
	public static final String VACCINE_TYPE_NOT_FOUND = "Vaccine type not found for the id: ";
	public static final String VACCINE_NOT_FOUND = "Vaccine not found for the id: ";

	/*
	 * exist
	 * 
	 */
	public static final String EXIST_ID = "Id already in use! Please try again!";
	public static final String EXIST_EMAIL = "Email already in use! Please try again!";
	public static final String EXIST_PHONE = "Phone already in use! Please try again!";

	public static final String INVALID_BIRTH_DATE_LESS_18 = "Birth date must be greater 18!";
	public static final String INVALID_BIRTH_DATE_GREATER_NOW = "Birth date invalid!";
	public static final String INVALID_DATE = "Expected Start date must not be later than expected end date!";
	public static final String INVALID_FORMAT_DATE = "Format date must be yyyy-mm-dd!";
	public static final String INVALID_FORMAT_NUMBER = "column number of injection must include digits!";
	public static final String INVALID_FILE_IMAGE = "This file not support! We Support: jpe/jpg/png/gif/bmp!";
	public static final String INVALID_NO_CHOOSE = "No selected to delete!";
	public static final String INVALID_DELETE_ADMIN = "Can not delete ADMIN with Id = ";
	public static final String INVALID_CHOOSE = "Invalid data - please recheck your selects!";
	public static final String INVALID_VACCINE_ID = "Vaccine id invalid!";
	public static final String INVALID_VACCINE_NAME = "Vaccine name invalid!";
	public static final String INVALID_VACCINE_TYPE_ID = "Vaccine type id invalid!";
	public static final String NOT_FOUND_VACCINE_TYPE_ID = "Not found vaccine type!";

	public static final String NULL_EMPLOYEE = "Employee is null!";
	public static final String NULL_VACCINE_TYPE = "Vaccine type is null!";
	public static final String NULL_VACCINE = "Vaccine is null!";
	public static final String NULL_SCHEDULE = "Injection schedule is null!";

	public static final String REGEX_IMAGE = "([^\\s]+(\\.(?i)(jpe?g|png|gif|bmp))$)";
	public static final String REGEX_DATE = "^\\d{4}-\\d{2}-\\d{2}$";
	public static final String REGEX_NUMBER = "\\d+";

	/*
	 * success
	 */
	public static final String SUCCESS_ADD = "Add Successfuly!!!";
	public static final String SUCCESS_FILE = "Upload file successfully!";
	public static final String FAIL_FILE = "You must upload file excel!";
}
