package com.mordor.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.mordor.Utils.ResourceNotFoundException;
import com.mordor.dao.SeatReservationDAO;
import com.mordor.model.enitity.MovieScreening;
import com.mordor.model.enitity.Seat;
import com.mordor.model.enitity.SeatReservation;

@Service
public class SeatReservationServiceImpl implements SeatReservationService {
	
	@Autowired
	private SeatReservationDAO seatReservationDAO;
	
	@Override
	public void save(SeatReservation seatReservation) {
		seatReservationDAO.save(seatReservation);
	}
	
	@Override
	public List<SeatReservation> findByMovieScreening(List<MovieScreening> list) {
		return seatReservationDAO.findByMovieScreeningIn(list);
	}
	@Override
	public List<Seat> findReservedSeats(MovieScreening movieScreening) throws ResourceNotFoundException {
		List<SeatReservation> seatReservations = Lists.newArrayList(seatReservationDAO.findByMovieScreening(movieScreening));
		List<Seat> occupiedSeats = seatReservations.stream()
				.map(s -> s.getSeat())
				.collect(Collectors.toList());
		
		return occupiedSeats;
	}
}
