package com.yakov.coupons.beans;

/**
 * Bean class necessary for Exception handler work.
 * @author Yakov
 *
 */
public class ErrorBean {
	private int internalCode;
	private String internalMessage;
	private String externalMessage;
	

	public ErrorBean() {
		super();
	}
	
	public ErrorBean(int intCode, String intMessage, String extMessage){
		this.internalCode = intCode;
		this.internalMessage = intMessage;
		this.externalMessage = extMessage;
	}

	public int getInternalCode() {
		return internalCode;
	}

	public void setInternalCode(int internalCode) {
		this.internalCode = internalCode;
	}

	public String getInternalMessage() {
		return internalMessage;
	}

	public void setInternalMessage(String internalMessage) {
		this.internalMessage = internalMessage;
	}

	public String getExternalMessage() {
		return externalMessage;
	}

	public void setExternalMessage(String externalMessage) {
		this.externalMessage = externalMessage;
	}

}
