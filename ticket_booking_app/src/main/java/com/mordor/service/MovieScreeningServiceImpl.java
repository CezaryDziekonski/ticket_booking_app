package com.mordor.service;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.common.collect.Lists;
import com.google.common.collect.Streams;
import com.mordor.Utils.ResourceNotFoundException;
import com.mordor.dao.MovieScreeningDAO;
import com.mordor.dao.SeatReservationDAO;
import com.mordor.model.AvailableSeatsInScreening;
import com.mordor.model.converter.ConverterDTO;
import com.mordor.model.converter.MovieScreeningDtoConverter;
import com.mordor.model.dto.AvailableSeatInScreeningDTO;
import com.mordor.model.dto.MovieScreeningDTO;
import com.mordor.model.dto.ScreeningsDTO;
import com.mordor.model.enitity.MovieScreening;
import com.mordor.model.enitity.Seat;

@Service
public class MovieScreeningServiceImpl implements MovieScreeningService{
	
	@Autowired
	private MovieScreeningDAO movieScreeningDAO;
	
	@Autowired
	private SeatReservationDAO seatReservationDAO;
	
	@Autowired
	SeatService seatService;
	
	@Autowired
	private MovieScreeningDtoConverter movieScreeningConverter;
	
	@Autowired
	private ConverterDTO<AvailableSeatInScreeningDTO, AvailableSeatsInScreening> availableSeatConverter;

	@Override
	public List<ScreeningsDTO> findAll() {
		List<MovieScreening> movieScreenings = Lists.newArrayList(movieScreeningDAO.findAll());
		
		List<ScreeningsDTO> result = movieScreeningConverter.entityListToDto(movieScreenings);	
		result = sortByTitle(result);
		result = sortByScreeningTime(result);
		
		return result;
	}
	
	@Override
	public List<ScreeningsDTO> findWithinDate(Instant timeBegin, Instant timeEnd) {
		List<MovieScreening> movieWithinTimeInterval = Streams.stream(movieScreeningDAO.findAll())
				.filter(movieSc -> ((MovieScreening) movieSc).getScreeningTime().compareTo(timeBegin) >= 0 && ((MovieScreening) movieSc).getScreeningTime().compareTo(timeEnd) <= 0)
				.collect(Collectors.toList());
		
		List<ScreeningsDTO> result = movieScreeningConverter.entityListToDto(movieWithinTimeInterval);
		result = sortByTitle(result);
		result = sortByScreeningTime(result);
		
		return result;
	}

	@Override
	public AvailableSeatInScreeningDTO findAvailableSeats(Long movieScreeningId) throws ResourceNotFoundException {
		MovieScreening movieScreening = movieScreeningDAO.findById(movieScreeningId).orElseThrow( () -> 
		new ResourceNotFoundException(ResourceNotFoundException.MovieScreeningNotFound + movieScreeningId));
		
		List<Seat> freeSeats = seatService.findFreeSeats(movieScreening);
		
		AvailableSeatsInScreening availableSeatsInScreening = new AvailableSeatsInScreening();
		availableSeatsInScreening.setAvailableSeats(freeSeats);
		availableSeatsInScreening.setRoom(movieScreening.getRoom());
		
		AvailableSeatInScreeningDTO result = availableSeatConverter.convertToDto(availableSeatsInScreening);
		
		return result;
	}
	
	private List<ScreeningsDTO> sortByScreeningTime(List<ScreeningsDTO> list) {
		for(ScreeningsDTO sd : list) {
			List<MovieScreeningDTO> sorted = sd.getScreeningsList().stream()
					.sorted(Comparator.comparing(MovieScreeningDTO::getScreeningTime))
					.collect(Collectors.toList());
			sd.setScreeningsList(sorted);
		}
		return list;
	}
	
	private List<ScreeningsDTO> sortByTitle(List<ScreeningsDTO> list) {
		List<ScreeningsDTO> sorted = list.stream()
				.sorted(Comparator.comparing(ScreeningsDTO::getMovieTitle))
				.collect(Collectors.toList());
		return sorted;
	}
	
	@Override
	public MovieScreening findById(Long id) throws ResourceNotFoundException {
		return movieScreeningDAO.findById(id).orElseThrow(() -> new ResourceNotFoundException(ResourceNotFoundException.MovieScreeningNotFound + id));
	}

	
	


	


}
