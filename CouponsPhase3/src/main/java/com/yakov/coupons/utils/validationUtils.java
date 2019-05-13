package com.yakov.coupons.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.yakov.coupons.exceptions.ApplicationException;
import com.yakov.coupons.enums.ErrorType;

public class validationUtils {
	/**
	 * Checks spelling of name string throws exception if fails.
	 * @param name string to check
	 * @throws ApplicationException
	 */
	public static void validateNameSpelling(String name) throws ApplicationException {
		//regexp string to check: no spaces at start or end of the string, no double spaces
		//no symbols other the english letters or numbers or !@#$%^&*()_-=+\|{}[]:;"'<,>.?/ or space.
		String forbiddenSymbols = "^ +.*|.* +$|.* {2,}.*|.*[^A-Za-z0-9!@#$%^&*()_\\-=+\\\\|{}\\[\\]:;\"'<,>\\.?/\\ ]+.*|.*\\n.*";
		//If we find any letter that is not allowed we throw exception
		if(name.matches(forbiddenSymbols)) {
			throw new ApplicationException(ErrorType.INCORRECT_NAME_SPELLING, DateUtils.getCurrentDateAndTime() +
					"\nForbidden symbol in the provided name.");
		}
	}
	
	/**
	 * Checks if string contains any symbols throws exception if no symbols found.
	 * @param str string to check
	 * @throws ApplicationException
	 */
	public static void validateAbsence(String str) throws ApplicationException {
		if (str == null || str.equals("")) {
			throw new ApplicationException(ErrorType.MISSING_STRING, DateUtils.getCurrentDateAndTime() +
					"\nUser hasn't provided any input or empty input provided.");
		}
	}
	
	/**
	 * Checks if there are any symbols of new line, if there are throws exception.
	 * @param password string to check
	 * @throws ApplicationException
	 */
	public static void validatePasswordSpelling(String password) throws ApplicationException {
		if (password.length() < 4 || password.matches(".*\n.*")) {
			throw new ApplicationException(ErrorType.INCORRECT_PASSWORD_SPELLING, DateUtils.getCurrentDateAndTime() +
					"\nProvided password spelling is not allowed.");			
		}
	}
	
	/**
	 * Checks email spelling if fails throws exception
	 * @param email email string to check
	 * @throws ApplicationException
	 */
	public static void validateEmailSpelling(String email) throws ApplicationException {
		//regexp that checks if string contains any symbols that are not english letters or numbers,
		//or these : !@#$%^&*()_-=+\|{}[]:;"'<,>.?/ or more then one @ symbol or more than 2 . symbols in a row.
		String forbiddenSymbols = ".*[^A-Za-z0-9!@#$%&'*+\\-\\/=?^_`{|}~\\.]+.*|.*\\.{2,}.*|.*@.*@.*";
		//regexp that checks right order of email name symbols
		//it should start with symbols then there should be @ symbol, some other symbols, and after . symbol there should be from 2 to five symbols.
		String order = "^.+@.+\\..{2,5}$";
		if (email.matches(forbiddenSymbols) || !email.matches(order)) {
			throw new ApplicationException(ErrorType.INCORRECT_EMAIL_SPELLING, DateUtils.getCurrentDateAndTime() +
			"\nProvided email spelling is not allowed");
		}
	} 

	/**
	 * Checks date spelling.
	 * @param date string of date to check
	 * @throws ApplicationException
	 */
	public static void validateDateSpelling(String date) throws ApplicationException {
		Integer month = Integer.parseInt(date.substring(5, 7));
		if(month > 12 || month < 1)  {
			throw new ApplicationException(ErrorType.INCORRECT_DATE_SPELLING, DateUtils.getCurrentDateAndTime() + 
				"\nProvided month is invalid.");
		}
		
		SimpleDateFormat pattern = new SimpleDateFormat("yyyy-mm-dd");
		//we do not allow more then 31(30,29) days in a month.
		pattern.setLenient(false);
		try {
			pattern.parse(date);
		} catch (ParseException e) {
			throw new ApplicationException(ErrorType.INCORRECT_DATE_SPELLING, DateUtils.getCurrentDateAndTime() + 
					"\nProvided date format is invalid");
		}
		
		//date should only contain bumbers and - symbol
		String allowedSymbols = "[0-9\\-]+";
		//date should start with 4 digits year, followed by 2 digits month and days.
		String order = "^.{4}\\-.{2}\\-.{2}$";
		if (!date.matches(allowedSymbols) || !date.matches(order)) {
			throw new ApplicationException(ErrorType.INCORRECT_DATE_SPELLING, DateUtils.getCurrentDateAndTime() + 
					"\nProvided date spelling is invalid");
		}
	}
	
	/**
	 * Checks if end date if after start date
	 * @param end end date string to check
	 * @param start start date string to check
	 * @throws ApplicationException
	 */
	public static void endOlderThenStart(String end, String start) throws ApplicationException {
		Integer startInt = Integer.parseInt(start.replace("-", ""));
		Integer endInt = Integer.parseInt(end.replace("-", ""));
		
		if(startInt > endInt) {
			throw new ApplicationException(ErrorType.INCORRECT_VALUE, DateUtils.getCurrentDateAndTime() + 
					"\nAttempt to set start date that starts after end date");
		}
	}
}