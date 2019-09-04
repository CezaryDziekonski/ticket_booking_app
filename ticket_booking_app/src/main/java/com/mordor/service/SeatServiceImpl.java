package com.mordor.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.mordor.Utils.ResourceNotFoundException;
import com.mordor.dao.SeatDAO;
import com.mordor.model.enitity.MovieScreening;
import com.mordor.model.enitity.Seat;

@Service
public class SeatServiceImpl implements SeatService{
	
	@Autowired
	private SeatDAO seatDAO;
	
	@Autowired
	private SeatReservationService seatReservationService;
	
	
	@Override
	public Optional<Seat> findById(Long id) {
		return seatDAO.findById(id);
	}
	
	@Override
	public List<Seat> findByIds(List<Long> idList) {
		return seatDAO.findByIdIn(idList);
	}
	@Override
	public List<Seat> findFreeSeats(MovieScreening movieScreening) throws ResourceNotFoundException {
		List<Seat> occupiedSeats = seatReservationService.findReservedSeats(movieScreening);
		List<Seat> allSeatsInRoom = Lists.newArrayList(seatDAO.findByRoom(movieScreening.getRoom()));
		List<Seat> freeSeats = allSeatsInRoom.stream()
				.filter(e -> !occupiedSeats.contains(e))
				.collect(Collectors.toList());
		
		return freeSeats;
	}
	
	
}
