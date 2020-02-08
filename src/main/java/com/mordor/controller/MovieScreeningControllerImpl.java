package com.mordor.controller;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.mordor.model.dto.ScreeningsDTO;
import com.mordor.service.MovieScreeningService;

@Component
public class MovieScreeningControllerImpl implements MovieScreeningController{
	
	private MovieScreeningService movieScreeningService;
	
	@Autowired
	public MovieScreeningControllerImpl(MovieScreeningService movieScreeningService) {
		this.movieScreeningService = movieScreeningService;
	}
	
	@Override
	public List<ScreeningsDTO>getScreenings(Instant timeBegin, Instant timeEnd) {
		List<ScreeningsDTO> movieScreenings = movieScreeningService.findWithinDate(timeBegin, timeEnd);
	
		return movieScreenings;
	}
}
