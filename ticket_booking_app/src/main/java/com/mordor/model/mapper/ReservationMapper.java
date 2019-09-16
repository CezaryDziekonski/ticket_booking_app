package com.mordor.model.mapper;

import org.springframework.stereotype.Component;

import com.mordor.exception.ResourceNotFoundException;
import com.mordor.model.dto.ReservationDTO;
import com.mordor.model.enitity.Reservation;

@Component
public class ReservationMapper implements MapperDTO<ReservationDTO, Reservation > {
	
	@Override
	public Reservation mapToEntity(ReservationDTO dto) throws ResourceNotFoundException {
		return modelMapper.map(dto, Reservation.class);
	}
	
	@Override
	public ReservationDTO mapToDTO(Reservation entity) {
		return modelMapper.map(entity, ReservationDTO.class);
	}
}
