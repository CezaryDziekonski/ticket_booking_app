package com.mordor.service;

import java.util.List;

import com.mordor.Utils.ResourceNotFoundException;
import com.mordor.model.enitity.MovieScreening;
import com.mordor.model.enitity.Seat;
import com.mordor.model.enitity.SeatReservation;

public interface SeatReservationService {
	void save(SeatReservation seatReservation);
	List<SeatReservation> findByMovieScreening(List<MovieScreening> list);
	List<Seat> findReservedSeats(MovieScreening movieScreening) throws ResourceNotFoundException;
}
