package com.mordor.Utils;

public class WrongReservationException extends Exception {

	public WrongReservationException (String errorMessage) {
		super(errorMessage);
	}
}
