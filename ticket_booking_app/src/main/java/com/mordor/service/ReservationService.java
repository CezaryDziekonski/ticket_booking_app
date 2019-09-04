package com.mordor.service;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import com.mordor.Utils.ResourceNotFoundException;
import com.mordor.Utils.WrongReservationException;
import com.mordor.model.dto.ReservationConfirmationDTO;
import com.mordor.model.dto.ReservationDTO;
import com.mordor.model.enitity.Reservation;


public interface ReservationService {
	
	public final static long durationToExpirationOfReservation = 15;

	public static Instant getExpirationTime(Instant currentTime) {
		Instant expirationTime = currentTime.plus(durationToExpirationOfReservation, ChronoUnit.MINUTES);
		return expirationTime;
	}
	
	public Reservation findById(Long id) throws ResourceNotFoundException;
	public void deleteById(Long id);
	public void confirmReservation(Long id) throws ResourceNotFoundException;
	public ReservationConfirmationDTO saveReservation(ReservationDTO reservationDTO) throws MalformedURLException, ResourceNotFoundException, WrongReservationException;
	
	

}
