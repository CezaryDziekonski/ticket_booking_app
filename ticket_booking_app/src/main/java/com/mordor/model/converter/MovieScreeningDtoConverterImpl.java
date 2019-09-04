package com.mordor.model.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import com.mordor.model.dto.MovieScreeningDTO;
import com.mordor.model.dto.ScreeningsDTO;
import com.mordor.model.enitity.Movie;
import com.mordor.model.enitity.MovieScreening;

@Component
public class MovieScreeningDtoConverterImpl implements MovieScreeningDtoConverter {

	private static final ModelMapper modelMapper = new ModelMapper();
	
	public List<ScreeningsDTO> entityListToDto(List<MovieScreening> movieScreenings) {
		List<ScreeningsDTO> screeningsDTOs = new ArrayList<ScreeningsDTO>();	
		Map<Movie, List<MovieScreening>> screeningPerMovie = movieScreenings.stream()
				.collect(Collectors.groupingBy(MovieScreening::getMovie, Collectors.toList()));
		
		for(Map.Entry<Movie, List<MovieScreening>> entry : screeningPerMovie.entrySet()) {
			ScreeningsDTO sc = new ScreeningsDTO();
			sc.setMovieTitle(entry.getKey().getTitle());
			
			List<MovieScreeningDTO> ms = entry.getValue().stream()
				.map(s -> modelMapper.map(s, MovieScreeningDTO.class))
				.collect(Collectors.toList());
				
			sc.setScreeningsList(ms);
			screeningsDTOs.add(sc);
		}
		
		return screeningsDTOs;
	}
}
