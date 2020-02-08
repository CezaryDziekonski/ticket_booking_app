package com.mordor.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mordor.model.dto.ReservationConfirmationDTO;
import com.mordor.model.dto.ReservationDTO;

@RestController
@RequestMapping("/api")
public interface ReservationController {
	
	@RequestMapping(value = "/reservation", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ReservationConfirmationDTO> newReservation(@RequestBody @Valid ReservationDTO reservationDTO);
	
	@RequestMapping(value = "/confirmReservation", method = RequestMethod.POST)
	@ResponseBody
	public void confirmReservation(@RequestParam Long reservationId);
}
