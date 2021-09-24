package com.edu.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConvertDate {
	public static Date convertDateFromString(String dateString) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				return dateFormat.parse(dateString);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return null;
	}
}
