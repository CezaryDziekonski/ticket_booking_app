package com.mordor.Utils;

public class ResourceNotFoundException extends Exception{
	public static final String SeatNotFound = "Seat not found with id ";
	public static final String TicketTypeNotFound = "Ticket Type not found with id ";
	public static final String MovieScreeningNotFound = "Movie Screening not found with id ";
	public static final String ReservationNotFound = "Reservation not found with id ";
	public static final String RoomNotFound = "Room not found with id ";
	
	public ResourceNotFoundException (String errorMessage) {
		super(errorMessage);
	}
}
