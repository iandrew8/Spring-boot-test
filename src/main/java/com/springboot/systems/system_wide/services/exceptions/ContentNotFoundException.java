package com.springboot.systems.system_wide.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ContentNotFoundException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public ContentNotFoundException() {
		super("Content not Found");
	}
	
	public ContentNotFoundException(String message){
        super(message);
	}
	
}
