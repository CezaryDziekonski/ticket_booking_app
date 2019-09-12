package com.mordor.service;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mordor.dao.ReservationDAO;
import com.mordor.dao.SeatReservationDAO;
import com.mordor.exception.ResourceNotFoundException;
import com.mordor.exception.LeftOverSeatsException;
import com.mordor.model.ReservationConfirmation;
import com.mordor.model.dto.ReservationConfirmationDTO;
import com.mordor.model.dto.ReservationDTO;
import com.mordor.model.enitity.MovieScreening;
import com.mordor.model.enitity.Reservation;
import com.mordor.model.enitity.Seat;
import com.mordor.model.enitity.SeatReservation;
import com.mordor.model.mapper.MapperDTO;
import com.mordor.utils.ReservationExpirationTask;

@Service
public class ReservationServiceImpl implements ReservationService{
	
	private ReservationDAO reservationDAO;	        	
	
	private SeatReservationService seatReservationService;	
	
	private MovieScreeningService movieScreeningService;
	
	private ReservationExpirationTask reservationExpirationTask;		
	
	private MapperDTO<ReservationConfirmationDTO, ReservationConfirmation> reservationConfirmationMapper;	
	
	private MapperDTO<ReservationDTO, Reservation> reservationMapper;
		
	@Autowired
	public ReservationServiceImpl(ReservationDAO reservationDAO, SeatReservationDAO seatReservationDAO,
			SeatReservationService seatReservationService,
			MovieScreeningService movieScreeningService, ReservationExpirationTask reservationExpirationTask,
			MapperDTO<ReservationConfirmationDTO, ReservationConfirmation> reservationConfirmationMapper,
			MapperDTO<ReservationDTO, Reservation> reservationMapper) {
		this.reservationDAO = reservationDAO;
		this.seatReservationService = seatReservationService;
		this.movieScreeningService = movieScreeningService;
		this.reservationExpirationTask = reservationExpirationTask;
		this.reservationConfirmationMapper = reservationConfirmationMapper;
		this.reservationMapper = reservationMapper;
	}

	@Override
	public ReservationConfirmationDTO saveReservation(ReservationDTO reservationDTO) {
		Reservation reservation = reservationMapper.mapToEntity(reservationDTO);
		List<SeatReservation> seatReservations = seatReservationService.mapToSeatReservation(reservationDTO.getTickets());
		
		seatReservationService.checkLeftOverSeat(reservationDTO.getTickets());
		
		Instant timeofReservation = Instant.now(Clock.offset(Clock.systemUTC(), Duration.ofHours(2)));
		reservation.setExpirationTime(ReservationService.getExpirationTime(timeofReservation));
		
		List<Seat> seatsToReserve = seatReservations.stream()
				.map(s -> s.getSeat())
				.collect(Collectors.toList());
		
		Map<MovieScreening, List<SeatReservation>> seatsPerMovieScreening = seatReservations.stream()
				.collect(Collectors.groupingBy(SeatReservation::getMovieScreening, Collectors.toList()));
		
		seatsPerMovieScreening.entrySet().stream()
			.forEach(s ->{
				checkFreeSeatReservation(s.getKey(), seatsToReserve);
				checkTimeToScreening(s.getKey(), timeofReservation);
			});
		
		seatReservations.stream()
			.forEach(s -> s.setReservation(reservation));
		
		
		reservationDAO.save(reservation);
		seatReservations.stream()
			.forEach(s -> seatReservationService.save(s));
		
		seatsPerMovieScreening.entrySet().stream()
			.forEach(s -> reservationExpirationTask.schedule(reservation, s.getKey().getScreeningTime()));
		
		ReservationConfirmation reservationConfirmation = new ReservationConfirmation();
		reservationConfirmation.setExpirationTime(reservation.getExpirationTime());
		reservationConfirmation.setTotalAmount(calcTotalAmount(seatReservations));
		reservationConfirmation.setConfirmationLink(getConfirmationURL(reservation.getId()));
		
		return reservationConfirmationMapper.mapToDTO(reservationConfirmation);
	}
	
	@Override
	public void confirmReservation(Long id) {
		Reservation reservation = findById(id);
		reservation.setConfirmed(true);
		reservationDAO.save(reservation);
	}
	
	@Override
	public Reservation findById(Long id) {
		return reservationDAO.findById(id).orElseThrow(() -> new ResourceNotFoundException("Reservation with id: " + id +" not found"));
	}
	
	@Override
		public void deleteById(Long id) {
			reservationDAO.deleteById(id);
	}
	
	private void checkFreeSeatReservation(MovieScreening movieScreening, List<Seat> seatsToReserve) {
		List<Seat> reservedSeats = seatReservationService.findReservedSeats(movieScreening);
		List<Seat> seatToReserveInReservedSeats = new ArrayList<>();
		seatToReserveInReservedSeats.addAll(seatsToReserve);
		seatToReserveInReservedSeats.retainAll(reservedSeats);
		if(seatToReserveInReservedSeats.size() > 0) {
			 new LeftOverSeatsException("Seat reserved");
		}
	}
	
	private void checkTimeToScreening(MovieScreening movieScreening, Instant timeofReservation) {
		Instant screeningTime = movieScreeningService.findById(movieScreening.getId()).getScreeningTime();
		if(!isReservationPossibleDueToScreeningTime(screeningTime, timeofReservation)) {
			 new LeftOverSeatsException("Reservation too late");
		}
	}
	
	private boolean isReservationPossibleDueToScreeningTime(Instant screeningTime, Instant timeofReservation) {
		if(screeningTime.minus(timeofReservation.atZone(ZoneOffset.UTC).getMinute(),ChronoUnit.MINUTES).atZone(ZoneOffset.UTC).getMinute() <= durationToExpirationOfReservation) {
			return false;
		}
		return true;
	}
	
	private String getConfirmationURL(Long reservationId) {
		URIBuilder builder = new URIBuilder();
		builder.setScheme("http")
			.setHost("localhost:8080")
			.setPath("/api/confirmReservation")
			.addParameter("reservationId", Long.toString(reservationId));
		return builder.toString();
	}
	
	private BigDecimal calcTotalAmount(List<SeatReservation> seatReservations) {
		BigDecimal total;
		
		total = seatReservations.stream()
				.map(s -> s.getTicketType().getPrice())
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		
		return total;
	}
}
