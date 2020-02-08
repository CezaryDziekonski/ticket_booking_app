package com.mordor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SeatReservedException extends RuntimeException {
	
	public SeatReservedException() {
		super();
	}
	
	public SeatReservedException (String errorMessage) {
		super(errorMessage);
	}	
}
