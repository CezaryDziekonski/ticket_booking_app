package com.mordor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.mordor.model.dto.AvailableSeatDTO;
import com.mordor.service.SeatReservationService;

@Component
public class SeatControllerImpl implements SeatController {

	private SeatReservationService seatReservationService;
	
	@Autowired
	public SeatControllerImpl(SeatReservationService seatReservationService) {
		this.seatReservationService = seatReservationService;
	}
	
	@Override
	public ResponseEntity<AvailableSeatDTO> findAvailableSeats(Long screeningId) {
			return new ResponseEntity<>(seatReservationService.findAvailableSeats(screeningId), HttpStatus.FOUND);
	}
}
