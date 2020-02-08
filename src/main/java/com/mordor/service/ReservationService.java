package com.mordor.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import com.mordor.model.dto.ReservationConfirmationDTO;
import com.mordor.model.dto.ReservationDTO;
import com.mordor.model.enitity.Reservation;

public interface ReservationService {
	
	final static long durationToExpirationOfReservation = 15;

	static Instant getExpirationTime(Instant currentTime) {
		Instant expirationTime = currentTime.plus(durationToExpirationOfReservation, ChronoUnit.MINUTES);
		return expirationTime;
	}
	
	Reservation findById(Long id);
	void deleteById(Long id);
	void confirmReservation(Long id);
	ReservationConfirmationDTO saveReservation(ReservationDTO reservationDTO);
}
