package com.mordor.service;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.common.collect.Lists;
import com.google.common.collect.Streams;
import com.mordor.dao.MovieScreeningDAO;
import com.mordor.exception.ResourceNotFoundException;
import com.mordor.model.dto.MovieScreeningDTO;
import com.mordor.model.dto.ScreeningsDTO;
import com.mordor.model.enitity.MovieScreening;
import com.mordor.model.mapper.MovieScreeningMapper;

@Service
public class MovieScreeningServiceImpl implements MovieScreeningService{
	
	private MovieScreeningDAO movieScreeningDAO;
	
	private MovieScreeningMapper movieScreeningMapper;

	@Autowired
	public MovieScreeningServiceImpl(MovieScreeningDAO movieScreeningDAO,
			MovieScreeningMapper movieScreeningMapper) {
		this.movieScreeningDAO = movieScreeningDAO;
		this.movieScreeningMapper = movieScreeningMapper;
		
	}

	@Override
	public List<ScreeningsDTO> findAll() {
		List<MovieScreening> movieScreenings = Lists.newArrayList(movieScreeningDAO.findAll());
		
		List<ScreeningsDTO> result = movieScreeningMapper.mapToDTO(movieScreenings);	
		result = sortByTitle(result);
		result = sortByScreeningTime(result);
		
		return result;
	}
	
	@Override
	public List<ScreeningsDTO> findWithinDate(Instant timeBegin, Instant timeEnd) {
		List<MovieScreening> movieWithinTimeInterval = Streams.stream(movieScreeningDAO.findAll())
				.filter(movieSc -> ((MovieScreening) movieSc).getScreeningTime().compareTo(timeBegin) >= 0 && ((MovieScreening) movieSc).getScreeningTime().compareTo(timeEnd) <= 0)
				.collect(Collectors.toList());
		
		List<ScreeningsDTO> result = movieScreeningMapper.mapToDTO(movieWithinTimeInterval);
		result = sortByTitle(result);
		result = sortByScreeningTime(result);
		
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
	public MovieScreening findById(Long id) {
		return movieScreeningDAO.findById(id).orElseThrow(() -> new ResourceNotFoundException("Movie screening with id: " + id +" not found"));
	}
}
