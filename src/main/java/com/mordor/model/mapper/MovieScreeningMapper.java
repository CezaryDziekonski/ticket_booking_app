package com.mordor.model.mapper;

import java.util.List;

import org.modelmapper.ModelMapper;

import com.mordor.model.dto.ScreeningsDTO;
import com.mordor.model.enitity.MovieScreening;

public interface MovieScreeningMapper {
	static final ModelMapper modelMapper = new ModelMapper();
	
	List<ScreeningsDTO> mapToDTO(List<MovieScreening> movieScreenings);
}
