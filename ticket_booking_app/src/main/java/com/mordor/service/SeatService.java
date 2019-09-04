package com.mordor.service;

import java.util.List;
import java.util.Optional;

import com.mordor.Utils.ResourceNotFoundException;
import com.mordor.model.enitity.MovieScreening;
import com.mordor.model.enitity.Seat;

public interface SeatService {
	Optional<Seat> findById(Long id);
	List<Seat> findByIds(List<Long> idList);
	public List<Seat> findFreeSeats(MovieScreening movieScreening) throws ResourceNotFoundException;
	
}
