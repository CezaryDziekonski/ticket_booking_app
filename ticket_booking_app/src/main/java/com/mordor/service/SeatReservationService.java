package com.mordor.service;

import java.util.List;

import com.mordor.exception.ResourceNotFoundException;
import com.mordor.model.dto.AvailableSeatDTO;
import com.mordor.model.dto.TicketDTO;
import com.mordor.model.enitity.MovieScreening;
import com.mordor.model.enitity.Seat;
import com.mordor.model.enitity.SeatReservation;

public interface SeatReservationService {
	void save(SeatReservation seatReservation);
	List<SeatReservation> findByMovieScreening(List<MovieScreening> list);
	List<Seat> findReservedSeats(MovieScreening movieScreening) throws ResourceNotFoundException;
	boolean checkLeftOverSeat(List<TicketDTO> ticketList);
	List<Seat> findFreeSeats(MovieScreening movieScreening);
	List<SeatReservation> mapToSeatReservation(List<TicketDTO> ticketList);
	AvailableSeatDTO findAvailableSeats(Long movieScreeningId);
}
