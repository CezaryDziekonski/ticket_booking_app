package com.mordor.model.converter;

import java.util.List;

import com.mordor.model.dto.ScreeningsDTO;
import com.mordor.model.enitity.MovieScreening;

public interface MovieScreeningDtoConverter {
	public List<ScreeningsDTO> entityListToDto(List<MovieScreening> movieScreenings);
}
