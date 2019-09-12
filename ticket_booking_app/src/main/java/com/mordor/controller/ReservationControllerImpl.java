package com.mordor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import com.mordor.model.dto.ReservationConfirmationDTO;
import com.mordor.model.dto.ReservationDTO;
import com.mordor.service.ReservationService;

@Component
public class ReservationControllerImpl implements ReservationController{
	
	private ReservationService reservationService;
	
	@Autowired
	public ReservationControllerImpl(ReservationService reservationService) {
		this.reservationService = reservationService;
	}
	
	@Override
	public ResponseEntity<ReservationConfirmationDTO> newReservation(ReservationDTO reservationDTO) {
		ReservationConfirmationDTO confirmation = reservationService.saveReservation(reservationDTO);
		
		return new ResponseEntity<>(confirmation, HttpStatus.CREATED);
	}
	
	@Override
	public void confirmReservation(Long reservationId) {
		reservationService.confirmReservation(reservationId);
	}
}
