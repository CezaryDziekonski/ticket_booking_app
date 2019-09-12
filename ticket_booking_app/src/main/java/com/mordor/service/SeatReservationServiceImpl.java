package com.mordor.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.mordor.dao.SeatReservationDAO;
import com.mordor.exception.LeftOverSeatsException;
import com.mordor.model.AvailableSeatsInScreening;
import com.mordor.model.dto.AvailableSeatDTO;
import com.mordor.model.dto.TicketDTO;
import com.mordor.model.enitity.MovieScreening;
import com.mordor.model.enitity.Seat;
import com.mordor.model.enitity.SeatReservation;
import com.mordor.model.mapper.MapperDTO;
import com.mordor.model.mapper.TicketMapper;

@Service
public class SeatReservationServiceImpl implements SeatReservationService {
	
	private SeatReservationDAO seatReservationDAO;
	
	private MovieScreeningService movieScreeningService;
	
	private SeatService seatService;
	
	private MapperDTO<TicketDTO, SeatReservation> ticketMapper;
	
	private MapperDTO<AvailableSeatDTO, AvailableSeatsInScreening> availableSeatMapper;
	
	@Autowired
	public SeatReservationServiceImpl(SeatReservationDAO seatReservationDAO,
			MovieScreeningService movieScreeningService, 
			SeatService seatService, 
			TicketMapper ticketMapper, 
			MapperDTO<AvailableSeatDTO, AvailableSeatsInScreening> availableSeatMapper) {
		this.seatReservationDAO = seatReservationDAO;
		this.movieScreeningService = movieScreeningService;
		this.seatService = seatService;
		this.ticketMapper = ticketMapper;
		this.availableSeatMapper = availableSeatMapper;
	}

	@Override
	public void save(SeatReservation seatReservation) {
		seatReservationDAO.save(seatReservation);
	}
	
	@Override
	public List<SeatReservation> findByMovieScreening(List<MovieScreening> list) {
		return seatReservationDAO.findByMovieScreeningIn(list);
	}
	@Override
	public List<Seat> findReservedSeats(MovieScreening movieScreening) {
		List<SeatReservation> seatReservations = Lists.newArrayList(seatReservationDAO.findByMovieScreening(movieScreening));
		List<Seat> occupiedSeats = seatReservations.stream()
				.map(s -> s.getSeat())
				.collect(Collectors.toList());
		
		return occupiedSeats;
	}

	@Override
	public boolean checkLeftOverSeat(List<TicketDTO> ticketList) {
		//different screenings in one reservation
		Map<Long, List<TicketDTO>> ticketsPerScreening = ticketList.stream()
				.collect(Collectors.groupingBy(TicketDTO::getScreeningId, Collectors.toList()));
		
		for(Map.Entry<Long, List<TicketDTO>> entry : ticketsPerScreening.entrySet()) {
			List<Long> seatsToReserveIds = ticketList.stream()
					.map(s -> s.getSeatId())
					.collect(Collectors.toList());
			
			MovieScreening movieScreening = movieScreeningService.findById(entry.getKey());
			
			List<Seat> reservedSeats = findReservedSeats(movieScreening);
			List<Seat> seatsToReserve = seatService.findByIds(seatsToReserveIds);
	
			if(checkLeftOverSeat(seatsToReserve, reservedSeats)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public AvailableSeatDTO findAvailableSeats(Long movieScreeningId) {
		MovieScreening movieScreening = movieScreeningService.findById(movieScreeningId);
		
		List<Seat> freeSeats = findFreeSeats(movieScreening);
		
		AvailableSeatsInScreening availableSeatsInScreening = new AvailableSeatsInScreening();
		availableSeatsInScreening.setAvailableSeats(freeSeats);
		availableSeatsInScreening.setRoom(movieScreening.getRoom());
		
		AvailableSeatDTO result = availableSeatMapper.mapToDTO(availableSeatsInScreening);
		
		return result;
	}
		
	@Override
	public List<Seat> findFreeSeats(MovieScreening movieScreening) {
		List<Seat> occupiedSeats = findReservedSeats(movieScreening);
		List<Seat> allSeatsInRoom = seatService.findByRoom(movieScreening.getRoom());
		List<Seat> freeSeats = allSeatsInRoom.stream()
				.filter(e -> !occupiedSeats.contains(e))
				.collect(Collectors.toList());
		
		return freeSeats;
	}

	@Override
	public List<SeatReservation> mapToSeatReservation(List<TicketDTO> ticketList) {
		List<SeatReservation> seatReservations = ticketList.stream()
				.map(ticketDTO -> {
						return ticketMapper.mapToEntity(ticketDTO);})
				.collect(Collectors.toList());
		
		return seatReservations;
	}

	private boolean checkLeftOverSeat(List<Seat> seatsToReserve, List<Seat> reservedSeats) {
		List<Seat> seatsSimulation = new ArrayList<>();
		seatsSimulation.addAll(reservedSeats);
		seatsSimulation.addAll(seatsToReserve);
		
		Map<Integer, List<Seat>> seatsPerRow = seatsSimulation.stream()
				.collect(Collectors.groupingBy(Seat::getRow, Collectors.toList()));
		
		for(Map.Entry<Integer, List<Seat>> rowEntry : seatsPerRow.entrySet()) {
			List<Seat> sortedSeats = rowEntry.getValue().stream()
					.sorted(Comparator.comparing(Seat::getSeatNumber))
					.collect(Collectors.toList());
			
			for(int i = 0; i < sortedSeats.size()-1; i++) {
				if(sortedSeats.get(i+1).getSeatNumber() - sortedSeats.get(i).getSeatNumber() == 2) {
					new LeftOverSeatsException(
							"Left over seat beetween seat id: " + sortedSeats.get(i) + 
							" and seat id:" + sortedSeats.get(i+1));
					return true;
				}
			}
		}
		return false;
	}
}
