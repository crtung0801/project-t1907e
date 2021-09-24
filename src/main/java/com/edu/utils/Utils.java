package com.edu.utils;

import java.time.LocalDate;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.edu.exception.HandlerDateException;
import com.edu.model.Vaccine;
import com.edu.model.VaccineType;
import com.edu.service.VaccineService;
import com.edu.service.VaccineTypeService;

public class Utils {

	public static boolean checkInActive(List<String> ids, VaccineService vaccineService) {
		for (String id : ids) {
			Vaccine vaccine = vaccineService.findById(id);
			if (!vaccine.isActive()) {
				return false;
			}
		}
		return true;
	}

	public static boolean checkInActive(List<String> ids, VaccineTypeService vaccineTypeService) {
		for (String id : ids) {
			VaccineType vaccineType = vaccineTypeService.findById(id);
			if (!vaccineType.isStatus()) {
				return false;
			}
		}
		return true;
	}

	public static boolean compare(LocalDate startDate, LocalDate endDate) {
		if (startDate == null) {
			return false;
		}

		if (endDate == null) {
			return false;
		}

		return endDate.compareTo(startDate) >= 0;
	}

	public static String date(String date) throws Exception {

		if (date == null)
			return null;

		if (date.isBlank())
			return null;

		if (!date.matches(Constant.REGEX_DATE))
			throw new HandlerDateException(Constant.INVALID_FORMAT_DATE);

		return date;
	}

	public static Integer number(String number) throws Exception {

		if (number == null)
			return 0;

		if (number.isBlank())
			return 0;

		if (!number.matches(Constant.REGEX_NUMBER))
			throw new NumberFormatException(Constant.INVALID_FORMAT_NUMBER);

		return Integer.parseInt(number);
	}

}
