package com.mordor.model.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mordor.model.dto.TicketDTO;
import com.mordor.model.enitity.MovieScreening;
import com.mordor.model.enitity.Seat;
import com.mordor.model.enitity.SeatReservation;
import com.mordor.model.enitity.TicketType;
import com.mordor.service.MovieScreeningService;
import com.mordor.service.SeatService;
import com.mordor.service.TicketTypeService;

@Component
public class TicketMapper implements MapperDTO<TicketDTO, SeatReservation> {

	private SeatService seatService;
	
	private MovieScreeningService movieScreeningService;
	
	private TicketTypeService ticketTypeService;
	
	@Autowired
	public TicketMapper(SeatService seatService, MovieScreeningService movieScreeningService,
			TicketTypeService ticketTypeService) {
		this.seatService = seatService;
		this.movieScreeningService = movieScreeningService;
		this.ticketTypeService = ticketTypeService;
	}

	@Override
	public SeatReservation mapToEntity(TicketDTO dto) {
		Seat seat;
		MovieScreening movieScreening;
		TicketType ticketType;

		seat = seatService.findById(dto.getSeatId());
		movieScreening = movieScreeningService.findById(dto.getScreeningId());
		ticketType = ticketTypeService.findById(dto.getTicketTypeId());
		
		SeatReservation seatReservation = new SeatReservation();
		seatReservation.setMovieScreening(movieScreening);
		seatReservation.setSeat(seat);
		seatReservation.setTicketType(ticketType);
		
		return seatReservation;
	}

	@Override
	public TicketDTO mapToDTO(SeatReservation entity) {
		TicketDTO ticketDTO = new TicketDTO();
		ticketDTO.setSeatId(entity.getId());
		ticketDTO.setScreeningId(entity.getMovieScreening().getId());
		ticketDTO.setTicketTypeId(entity.getTicketType().getId());
		
		return ticketDTO;
	}
}
