package com.rest.services.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Converter {

	public static java.sql.Date convertStringToDate(String date, String dateFormat) throws ParseException {
		DateFormat format = new SimpleDateFormat(dateFormat, Locale.ENGLISH);

		java.sql.Date tempDate = new java.sql.Date(format.parse(date).getTime());

		return tempDate;
	}
}
