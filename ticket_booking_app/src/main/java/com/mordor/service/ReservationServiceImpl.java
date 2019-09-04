package com.mordor.service;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;
import com.mordor.Utils.ResourceNotFoundException;
import com.mordor.Utils.RunnableExpirationTask;
import com.mordor.Utils.WrongReservationException;
import com.mordor.dao.ReservationDAO;
import com.mordor.dao.SeatReservationDAO;
import com.mordor.model.ReservationConfirmation;
import com.mordor.model.converter.ConverterDTO;
import com.mordor.model.dto.ReservationConfirmationDTO;
import com.mordor.model.dto.ReservationDTO;
import com.mordor.model.dto.TicketDTO;
import com.mordor.model.enitity.MovieScreening;
import com.mordor.model.enitity.Reservation;
import com.mordor.model.enitity.Seat;
import com.mordor.model.enitity.SeatReservation;

@Service
public class ReservationServiceImpl implements ReservationService{
	
	@Autowired
	ReservationDAO reservationDAO;
	
	@Autowired
	SeatReservationDAO seatReservationDAO;        
	
	@Autowired
	SeatReservationService seatReservationService;
	
	@Autowired
	SeatService seatService;
	
	@Autowired
	MovieScreeningService movieScreeningService;
	
	@Autowired
	ThreadPoolTaskScheduler threadPoolTaskScheduler;
	
	@Autowired
	private ConverterDTO<ReservationConfirmationDTO, ReservationConfirmation> reservationConfirmationConverter;
	
	@Autowired
	private ConverterDTO<ReservationDTO, Reservation> reservationConverter;
	
	@Autowired
	@Qualifier("ticketConverter")
	private ConverterDTO<TicketDTO, SeatReservation> ticketConverter;
	
	@PostConstruct
	private void init() {
		threadPoolTaskScheduler.setPoolSize(10);
	}
	
	
	@Override
	public ReservationConfirmationDTO saveReservation(ReservationDTO reservationDTO) throws MalformedURLException, ResourceNotFoundException, WrongReservationException {
		Reservation reservation = reservationConverter.convertToEntity(reservationDTO);
		
		if(checkLeftOverSeat(reservationDTO.getTickets())) {
			throw new WrongReservationException("Left over seats");
		}
		
		Instant timeofReservation = Instant.now(Clock.offset(Clock.systemUTC(), Duration.ofHours(2)));
		
		reservation.setExpirationTime(ReservationService.getExpirationTime(timeofReservation));
		
		List<SeatReservation> seatReservations = reservationDTO.getTickets().stream()
				.map(ticketDTO -> {
					try {
						return ticketConverter.convertToEntity(ticketDTO);
					} catch (ResourceNotFoundException e) {
						e.printStackTrace();
					}
					return null;
				})
				.collect(Collectors.toList());
		
		List<Seat> seatsToReserve = seatReservations.stream()
				.map(s -> s.getSeat())
				.collect(Collectors.toList());
		
		Map<MovieScreening, List<SeatReservation>> seatsPerMovieScreening = seatReservations.stream()
				.collect(Collectors.groupingBy(SeatReservation::getMovieScreening, Collectors.toList()));
		
		for(Map.Entry<MovieScreening, List<SeatReservation>> entry : seatsPerMovieScreening.entrySet()) {
			List<Seat> reservedSeats = seatReservationService.findReservedSeats(entry.getKey());
			List<Seat> seatToReserveInReservedSeats = new ArrayList<>();
			seatToReserveInReservedSeats.addAll(seatsToReserve);
			seatToReserveInReservedSeats.retainAll(reservedSeats);
			if(seatToReserveInReservedSeats.size() > 0) {
				throw new WrongReservationException("Seat reserved");
			}
			Instant screeningTime = movieScreeningService.findById(entry.getKey().getId()).getScreeningTime();
			if(!isReservationPossibleDueToScreeningTime(screeningTime, timeofReservation)) {
				throw new WrongReservationException("Reservation too late");
			}
			
		}
		seatReservations.stream()
			.forEach(s -> s.setReservation(reservation));
		
		
		reservationDAO.save(reservation);
		seatReservations.stream()
			.forEach(s -> seatReservationService.save(s));
		
		/* set expiration schedulers */
		//different expiration time of Reservation for every moviescreening 
		for(Map.Entry<MovieScreening, List<SeatReservation>> entry : seatsPerMovieScreening.entrySet()) {
			setExpirationTimeScheduler(reservation, entry.getKey().getScreeningTime());
		}
		
		ReservationConfirmation reservationConfirmation = new ReservationConfirmation();
		reservationConfirmation.setExpirationTime(reservation.getExpirationTime());
		reservationConfirmation.setTotalAmount(calcTotalAmount(seatReservations));
		reservationConfirmation.setConfirmationLink(new URL(getConfirmationURL(reservation.getId())));
		
		return reservationConfirmationConverter.convertToDto(reservationConfirmation);
	}
	
	@Override
	public void confirmReservation(Long id) throws ResourceNotFoundException {
		Reservation reservation = reservationDAO.findById(id).orElseThrow(() -> new ResourceNotFoundException(ResourceNotFoundException.ReservationNotFound + id));
		reservation.setConfirmed(true);
		reservationDAO.save(reservation);
	}
	
	@Override
	public Reservation findById(Long id) throws ResourceNotFoundException {
		return reservationDAO.findById(id).orElseThrow(() -> new ResourceNotFoundException(ResourceNotFoundException.ReservationNotFound + id));
	}
	
	@Override
		public void deleteById(Long id) {
			reservationDAO.deleteById(id);
	}
	
	private boolean isReservationPossibleDueToScreeningTime(Instant screeningTime, Instant timeofReservation) {
		if(screeningTime.minus(timeofReservation.atZone(ZoneOffset.UTC).getMinute(),ChronoUnit.MINUTES).atZone(ZoneOffset.UTC).getMinute() <= durationToExpirationOfReservation) {
			return false;
		}
		return true;
	}
	
	private void setExpirationTimeScheduler(Reservation reservation, Instant screeningTime) {
		Instant expirationDueToScreeningTime = screeningTime.minus(durationToExpirationOfReservation, ChronoUnit.MINUTES);
		
		//delete reservation due to reservation expiration Time
		if(reservation.getExpirationTime().compareTo(expirationDueToScreeningTime) < 0) {
			threadPoolTaskScheduler.schedule(new RunnableExpirationTask(reservation), 
					Date.from(reservation.getExpirationTime()));
		}
		//delete due to screening time
		else {
			threadPoolTaskScheduler.schedule(new RunnableExpirationTask(reservation), 
					Date.from(expirationDueToScreeningTime));
		}	
	}
	
	private String getConfirmationURL(Long reservationId) {
		URIBuilder builder = new URIBuilder();
		builder.setScheme("http")
			.setHost("localhost:8080")
			.setPath("/confirmReservation")
			.addParameter("reservationId", Long.toString(reservationId));
		return builder.toString();
	}
	
	private double calcTotalAmount(List<SeatReservation> seatReservations) {
		double total = 0;
		
		total = seatReservations.stream()
				.mapToDouble(s -> s.getTicketType().getPrice())
				.sum();
		
		return total;
	}
	private boolean checkLeftOverSeat(List<TicketDTO> ticketList) {
		//different screenings in one reservation
		Map<Long, List<TicketDTO>> ticketsPerScreening = ticketList.stream()
				.collect(Collectors.groupingBy(TicketDTO::getScreeningId, Collectors.toList()));
		
		for(Map.Entry<Long, List<TicketDTO>> entry : ticketsPerScreening.entrySet()) {
			MovieScreening movieScreening = null;
			List<Seat> reservedSeats = null;
			try {
				movieScreening = movieScreeningService.findById(entry.getKey());
				reservedSeats = seatReservationService.findReservedSeats(movieScreening);
			} catch (ResourceNotFoundException e) {
				e.printStackTrace();
			}
			if(movieScreening != null) {
				
				List<Long> seatsToReserveIds = entry.getValue().stream()
						.map(s -> s.getSeatId())
						.collect(Collectors.toList());
				
				
				List<Seat> seatsToReserve = seatService.findByIds(seatsToReserveIds);
				
				List<Seat> seatsSimulation = new ArrayList<>();
				seatsSimulation.addAll(reservedSeats);
				seatsSimulation.addAll(seatsToReserve);
				
				Map<Integer, List<Seat>> seatsPerRow = seatsSimulation.stream()
						.collect(Collectors.groupingBy(Seat::getRow, Collectors.toList()));
				
				for(Map.Entry<Integer, List<Seat>> rowEntry : seatsPerRow.entrySet()) {
					List<Seat> sortedSeats = rowEntry.getValue().stream()
						.sorted(Comparator.comparing(Seat::getSeatNumber))
						.collect(Collectors.toList());
					
					for (int i = 0; i < sortedSeats.size() - 1; i++) {
						//check left over seat
						if (sortedSeats.get(i+1).getSeatNumber() - sortedSeats.get(i).getSeatNumber() == 2) {
							return true;
						}
					}
				}
			}
			
		}
		return false;
	}
	
}
