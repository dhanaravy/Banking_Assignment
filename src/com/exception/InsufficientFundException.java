package com.exception;

public class InsufficientFundException extends Exception {

	private static final long serialVersionUID = 1L;
	String message;
	
	public InsufficientFundException(String string) {
		this.message=string;
	}

	public String getMessage() {
		return message;
	}
	
}
