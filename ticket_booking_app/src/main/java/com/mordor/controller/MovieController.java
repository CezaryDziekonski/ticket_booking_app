package com.mordor.controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.DataBinder;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import com.mordor.Utils.ResourceNotFoundException;
import com.mordor.Utils.WrongReservationException;
import com.mordor.model.dto.AvailableSeatInScreeningDTO;
import com.mordor.model.dto.ReservationConfirmationDTO;
import com.mordor.model.dto.ReservationDTO;
import com.mordor.model.dto.ScreeningsDTO;
import com.mordor.service.MovieScreeningService;
import com.mordor.service.ReservationService;

@RestController
public class MovieController {
	
	@Autowired
	private MovieScreeningService movieScreeningService;
	
	@Autowired
	private ReservationService reservationService;
	
	@Autowired
	@Qualifier("reservationValidator")
	private Validator reservationValidator;
	
	
	@RequestMapping(value = "/")
	public String index() {
		
		return "Ticket Booking App";
	}
	
	@RequestMapping(value = "/screeningsAll", method = RequestMethod.GET)
	@ResponseBody
	public List<ScreeningsDTO> getScreenings() {
		
		return movieScreeningService.findAll();
	}
	
	@RequestMapping(value = "/screenings", method = RequestMethod.GET)
	@ResponseBody
	public List<ScreeningsDTO>getScreenings(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant timeBegin, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant timeEnd) {
		List<ScreeningsDTO> movieScreenings = movieScreeningService.findWithinDate(timeBegin, timeEnd);
	
		return movieScreenings;
	}
	
	@RequestMapping(value = "/getSeats", method = RequestMethod.GET)
	@ResponseBody
	public AvailableSeatInScreeningDTO getScreeningRoom(@RequestParam Long screeningId) {
		try {
			return movieScreeningService.findAvailableSeats(screeningId);
		}catch(ResourceNotFoundException ex) {
			throw new ResponseStatusException(
			          HttpStatus.NOT_FOUND, "Movie screening not found", ex);
		}
	}
	@RequestMapping(value = "/createReservation", method = RequestMethod.POST)
	@ResponseBody
	public ReservationConfirmationDTO newReservation(@RequestBody ReservationDTO reservationDTO) throws MalformedURLException {
		DataBinder binder = new DataBinder(reservationDTO);
		binder.setValidator(reservationValidator);
		binder.validate();
		
		if(binder.getBindingResult().hasErrors()) {
			StringBuilder sb = new StringBuilder();
			for (ObjectError error : binder.getBindingResult().getAllErrors()) {
			    if ( error instanceof FieldError) {
			        FieldError fe = (FieldError) error;
			        sb.append( fe.getField());
			        sb.append( ": ");
			        sb.append(fe.getCode());
			    }
			}
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, sb.toString());
		}else {
			try {
				return reservationService.saveReservation(reservationDTO);
			}catch(ResourceNotFoundException ex) {
				throw new ResponseStatusException(
				          HttpStatus.NOT_FOUND, "Resource not found", ex);	
			}catch(WrongReservationException ex) {
				throw new ResponseStatusException(
				          HttpStatus.BAD_REQUEST, "Seat reserved", ex);
			}
		}
	}
	
	@RequestMapping(value = "/confirmReservation", method = RequestMethod.POST)
	@ResponseBody
	public void confirmReservation(@RequestParam Long reservationId) {
		try {
			 reservationService.confirmReservation(reservationId);
		}catch(ResourceNotFoundException ex) {
			throw new ResponseStatusException(
			          HttpStatus.NOT_FOUND, "Reservation not found", ex);	
		}
	}
	
}
