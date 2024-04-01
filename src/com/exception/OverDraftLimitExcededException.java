package com.exception;

public class OverDraftLimitExcededException extends Exception{

	private static final long serialVersionUID = 1L;
	String message;
	
	public OverDraftLimitExcededException(String string) {
		this.message=string;
	}

	public String getMessage() {
		return message;
	}
	
	
}
