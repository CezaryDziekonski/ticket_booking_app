package com.mordor.model;

import java.util.List;

import com.mordor.model.enitity.Reservation;
import com.mordor.model.enitity.SeatReservation;

public class UserReservation {
	
	private List<SeatReservation> seatReservation;
	
	private Reservation reservation;


	public List<SeatReservation> getSeatReservation() {
		return seatReservation;
	}

	public void setSeatReservation(List<SeatReservation> seatReservation) {
		this.seatReservation = seatReservation;
	}

	public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}
	
	

}
