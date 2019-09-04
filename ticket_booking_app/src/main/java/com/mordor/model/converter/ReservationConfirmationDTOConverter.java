package com.mordor.model.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import com.mordor.Utils.ResourceNotFoundException;
import com.mordor.model.ReservationConfirmation;
import com.mordor.model.dto.ReservationConfirmationDTO;

@Component
public class ReservationConfirmationDTOConverter implements ConverterDTO<ReservationConfirmationDTO, ReservationConfirmation> {
	
	private static final ModelMapper modelMapper = new ModelMapper();
	
	@Override
	public ReservationConfirmationDTO convertToDto(ReservationConfirmation entity) {
		ReservationConfirmationDTO reservationConfirmationDTO = modelMapper.map(entity, ReservationConfirmationDTO.class);
		return reservationConfirmationDTO;
	}
	@Override
	public ReservationConfirmation convertToEntity(ReservationConfirmationDTO dto) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

}
