package com.mordor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ReservationTimeException extends RuntimeException {
	
	public ReservationTimeException() {
		super();
	}
	
	public ReservationTimeException (String errorMessage) {
		super(errorMessage);
	}	
}