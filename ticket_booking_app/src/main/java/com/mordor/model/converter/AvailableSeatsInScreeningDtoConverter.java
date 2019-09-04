package com.mordor.model.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.mordor.Utils.ResourceNotFoundException;
import com.mordor.model.AvailableSeatsInScreening;
import com.mordor.model.dto.AvailableSeatInScreeningDTO;

@Component
public class AvailableSeatsInScreeningDtoConverter implements ConverterDTO<AvailableSeatInScreeningDTO, AvailableSeatsInScreening>{
	
	private static final ModelMapper modelMapper = new ModelMapper();
	
	@Override
	public AvailableSeatInScreeningDTO convertToDto(AvailableSeatsInScreening entity) {
		return modelMapper.map(entity, AvailableSeatInScreeningDTO.class);
	}
	
	@Override
	public AvailableSeatsInScreening convertToEntity(AvailableSeatInScreeningDTO dto) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

}
