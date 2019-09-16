package com.mordor.model.mapper;

import org.springframework.stereotype.Component;

import com.mordor.model.ReservationConfirmation;
import com.mordor.model.dto.ReservationConfirmationDTO;

@Component
public class ReservationConfirmationMapper implements MapperDTO<ReservationConfirmationDTO, ReservationConfirmation> {

	@Override
	public ReservationConfirmationDTO mapToDTO(ReservationConfirmation entity) {
		return modelMapper.map(entity, ReservationConfirmationDTO.class);
	}

	@Override
	public ReservationConfirmation mapToEntity(ReservationConfirmationDTO dto) {		
		return modelMapper.map(dto, ReservationConfirmation.class);
	}
}