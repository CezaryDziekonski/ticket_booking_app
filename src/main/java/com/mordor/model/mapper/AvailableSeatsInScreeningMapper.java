package com.mordor.model.mapper;

import org.springframework.stereotype.Component;

import com.mordor.exception.ResourceNotFoundException;
import com.mordor.model.AvailableSeatsInScreening;
import com.mordor.model.dto.AvailableSeatDTO;

@Component
public class AvailableSeatsInScreeningMapper implements MapperDTO<AvailableSeatDTO, AvailableSeatsInScreening>{
	
	@Override
	public AvailableSeatDTO mapToDTO(AvailableSeatsInScreening entity) {
		return modelMapper.map(entity, AvailableSeatDTO.class);
	}
	
	@Override
	public AvailableSeatsInScreening mapToEntity(AvailableSeatDTO dto) throws ResourceNotFoundException {
		return modelMapper.map(dto, AvailableSeatsInScreening.class);
	}
}
