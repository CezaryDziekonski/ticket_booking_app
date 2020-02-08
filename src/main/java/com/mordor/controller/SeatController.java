package com.mordor.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mordor.model.dto.AvailableSeatDTO;

@RequestMapping("/api")
@RestController
public interface SeatController {

	@RequestMapping(value = "/seat", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<AvailableSeatDTO> findAvailableSeats(@RequestParam Long screeningId);
}
