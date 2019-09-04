package com.mordor.model.converter;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import com.mordor.Utils.ResourceNotFoundException;
import com.mordor.model.dto.ReservationDTO;
import com.mordor.model.enitity.Reservation;
import com.mordor.service.ReservationService;


@Component
@Qualifier("reservationConverter")
public class ReservationDtoConverter implements ConverterDTO<ReservationDTO, Reservation > {
	
	private static final ModelMapper modelMapper = new ModelMapper();
	
	@Override
	public Reservation convertToEntity(ReservationDTO dto) throws ResourceNotFoundException {
		Reservation reservation = modelMapper.map(dto, Reservation.class);
		
		
		return reservation;
	}
	
	@Override
	public ReservationDTO convertToDto(Reservation entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
