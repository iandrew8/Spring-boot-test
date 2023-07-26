package com.springboot.systems.test.controllers.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ErrorResponse {
		
	private String message;
	private String trace;

	public ErrorResponse(String message, String trace) {
		this.message = message;
		this.trace = trace;
	}

}
