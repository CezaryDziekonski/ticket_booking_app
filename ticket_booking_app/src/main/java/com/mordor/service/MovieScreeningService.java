package com.mordor.service;

import java.time.Instant;
import java.util.List;

import com.mordor.model.dto.ScreeningsDTO;
import com.mordor.model.enitity.MovieScreening;

public interface MovieScreeningService {
	List<ScreeningsDTO> findAll();
	List<ScreeningsDTO> findWithinDate(Instant timeBegin, Instant timeEnd);
	MovieScreening findById(Long id);
}
