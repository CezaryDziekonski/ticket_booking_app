package com.mordor.model.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import com.mordor.Utils.ResourceNotFoundException;
import com.mordor.model.dto.TicketDTO;
import com.mordor.model.enitity.MovieScreening;
import com.mordor.model.enitity.Seat;
import com.mordor.model.enitity.SeatReservation;
import com.mordor.model.enitity.TicketType;
import com.mordor.service.MovieScreeningService;
import com.mordor.service.SeatService;
import com.mordor.service.TicketTypeService;

@Component
@Qualifier("ticketConverter")
public class TicketDtoConverter implements ConverterDTO<TicketDTO, SeatReservation> {

	@Autowired
	private SeatService seatService;
	
	@Autowired
	private MovieScreeningService movieScreeningService;
	
	@Autowired
	private TicketTypeService ticketTypeService;
	
	
	@Override
	public SeatReservation convertToEntity(TicketDTO dto) throws ResourceNotFoundException {
		Seat seat;
		MovieScreening movieScreening;
		TicketType ticketType;

		seat = seatService.findById(dto.getSeatId()).orElseThrow(() -> new ResourceNotFoundException(ResourceNotFoundException.SeatNotFound + dto.getSeatId()));
		movieScreening = movieScreeningService.findById(dto.getScreeningId());
		ticketType = ticketTypeService.findById(dto.getTicketTypeId());
		
		SeatReservation seatReservation = new SeatReservation();
		seatReservation.setMovieScreening(movieScreening);
		seatReservation.setSeat(seat);
		seatReservation.setTicketType(ticketType);
		
		return seatReservation;
	}

	@Override
	public TicketDTO convertToDto(SeatReservation entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
