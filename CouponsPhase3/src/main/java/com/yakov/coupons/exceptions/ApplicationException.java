package com.yakov.coupons.exceptions;

import com.yakov.coupons.enums.ErrorType;

/**
 * 
 * @author Yakov
 * Exception used this project.
 */
public class ApplicationException extends RuntimeException {
	private static final long serialVersionUID = 2582441738805996952L;
	ErrorType et; //Enum that shows which type of exception occurred.

	/**
	 * New Application exception constructor
	 * @param et enum error type
	 * @param message string message
	 */
	public ApplicationException(ErrorType et, String message) {
		super(message);
		this.et = et;
	}
	
	/**
	 * Creates new Application exception based on another exception. 
	 * @param e exception 
	 * @param et
	 * @param message
	 */
	public ApplicationException(Exception e, ErrorType et, String message) {
		super(message, e);
		this.et = et;
	}

	
	ErrorType getErrorType(){
		return this.et;
	}
	
	String getErrorMessage() {
		return super.getMessage();
	}
	
}
