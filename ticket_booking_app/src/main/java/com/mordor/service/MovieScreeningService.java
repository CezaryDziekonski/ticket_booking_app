package com.mordor.service;


import java.time.Instant;
import java.util.List;
import com.mordor.Utils.ResourceNotFoundException;
import com.mordor.model.dto.AvailableSeatInScreeningDTO;
import com.mordor.model.dto.ScreeningsDTO;
import com.mordor.model.enitity.MovieScreening;
import com.mordor.model.enitity.Seat;

public interface MovieScreeningService {
	List<ScreeningsDTO> findAll();
	List<ScreeningsDTO> findWithinDate(Instant timeBegin, Instant timeEnd);
	MovieScreening findById(Long id) throws ResourceNotFoundException;
	
	public AvailableSeatInScreeningDTO findAvailableSeats(Long movieScreeningId) throws ResourceNotFoundException;
}
