package com.springboot.systems.system_wide.services.exceptions;

public class ValidationFailedException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public ValidationFailedException() {
	}
	
	public ValidationFailedException(String message){
        super(message);
	}
	
}
