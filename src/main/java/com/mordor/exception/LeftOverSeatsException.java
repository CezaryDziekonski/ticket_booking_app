package com.mordor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class LeftOverSeatsException extends RuntimeException {

	public LeftOverSeatsException () {
		super();
	}
	
	public LeftOverSeatsException (String errorMessage) {
		super(errorMessage);
	}
}
