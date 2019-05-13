package com.yakov.coupons.enums;

/**
 * Types of possible exceptions
 * @author Yakov
 *
 */
public enum ErrorType {
	SYSTEM_ERROR(701), 					//Errors that can happen while exchanging data with DB.
	NAME_ALREADY_EXISTS(703),			//Appears when trying to use name which already exists in DB.
	EMAIL_ALREADY_EXISTS(704),			//Appears when trying to use email which already exists in DB.
	MISSING_STRING(713),				//Required String came empty.
	INCORRECT_NAME_SPELLING(723),		//Forbidden name spelling 
	INCORRECT_EMAIL_SPELLING(733),		//
	INCORRECT_DATE_SPELLING(743),		//
	INCORRECT_PASSWORD_SPELLING(743),	//
	INCORRECT_VALUE(744),				//Value doesn't match requirements.
	NOT_FOUND(773),				//Trying to use object that doesn't exist in Data Base.
	NO_MATCH_FOUND(775);			//
	
	
	private final int errorCode;
	
	private ErrorType(int errorCode) {
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return this.errorCode;
	}
	
	public String getErrorMessage() {
		return this.name();
	}
	
	public static ErrorType fromString(final String s) {
		return ErrorType.valueOf(s);
	}
	
	
}
