package com.mordor.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import com.mordor.Utils.ResourceNotFoundException;
import com.mordor.Utils.RunnableExpirationTask;
import com.mordor.Utils.WrongReservationException;
import com.mordor.dao.ReservationDAO;
import com.mordor.model.ReservationConfirmation;
import com.mordor.model.converter.ConverterDTO;
import com.mordor.model.converter.ReservationDtoConverter;
import com.mordor.model.dto.ReservationConfirmationDTO;
import com.mordor.model.dto.ReservationDTO;
import com.mordor.model.dto.TicketDTO;
import com.mordor.model.enitity.MovieScreening;
import com.mordor.model.enitity.Reservation;
import com.mordor.model.enitity.Seat;
import com.mordor.model.enitity.SeatReservation;
import com.mordor.model.enitity.TicketType;

import java.sql.Date;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class ReservationServiceImplTest {
	@Mock
	private ReservationDtoConverter reservationConverter;
	
	@Mock
	private ConverterDTO<TicketDTO, SeatReservation> ticketConverter;

	@Mock
	private SeatReservationService seatReservationService;
	
	@Mock
	private MovieScreeningService movieScreeningService;
	
	@Mock
	private SeatService seatService;
	
	@Mock
	private ReservationDAO reservationDAO;
	
	@Mock
	private ThreadPoolTaskScheduler threadPoolTaskScheduler;
	
	@Mock
	private ConverterDTO<ReservationConfirmationDTO, ReservationConfirmation> reservationConfirmationConverter;
	
	
	
	@InjectMocks
	private ReservationService reservationService = new ReservationServiceImpl();
	
	private ReservationDTO reservationDTO;
	
	private TicketDTO ticket;
	
	private MovieScreening movieScreening;

	@BeforeEach
	public void setUp()  {
		setUpReservation();
		
		ReservationDtoConverter rConverter = new ReservationDtoConverter();
		
		Reservation reservation = null;
		try {
			reservation = rConverter.convertToEntity(reservationDTO);
		} catch (ResourceNotFoundException e1) {
			e1.printStackTrace();
		}
		
		reservation.setId((long) 1);
		
		SeatReservation seatReservation = new SeatReservation();
		seatReservation.setMovieScreening(movieScreening);
		seatReservation.setReservation(reservation);
		TicketType ticketType = new TicketType();
		ticketType.setPrice(25.0);
		seatReservation.setTicketType(ticketType);
		
		setUpReservedSeats();
		setUpSeatToReserve(5);
		try {
			Mockito.when(movieScreeningService.findById(movieScreening.getId())).thenReturn(movieScreening);
			Mockito.when(reservationConverter.convertToEntity(reservationDTO)).thenReturn(reservation);
			Mockito.when(ticketConverter.convertToEntity(ticket)).thenReturn(seatReservation);
		} catch (ResourceNotFoundException e) {
			e.printStackTrace();
		}
		Mockito.when(reservationDAO.save(Mockito.any())).thenReturn(null);
		Mockito.when(threadPoolTaskScheduler.schedule(Mockito.any(RunnableExpirationTask.class), Mockito.any(Date.class))).thenReturn(null);
		
		
	}
	
	@Test
	void shouldThrowExceptionWhenReservationTimeLessThen15ToMovieScreening() {
		Instant screeningTime = Instant.now(Clock.offset(Clock.systemUTC(), Duration.ofHours(2))).plus(5, ChronoUnit.MINUTES);
		movieScreening.setScreeningTime(screeningTime);
		assertThrows(WrongReservationException.class, () -> reservationService.saveReservation(reservationDTO));
	}
	
	@Test
	void testLeftOverSeats() {
		setUpSeatToReserve(4);
		assertThrows(WrongReservationException.class, () -> reservationService.saveReservation(reservationDTO));
	}
	
	private void setUpReservedSeats() {
		List<Seat> reservedSeats = new ArrayList<Seat>();
		Seat seat = new Seat();
		seat.setId((long)2);
		seat.setRow(1);
		seat.setSeatNumber(2);
		reservedSeats.add(seat);
		
		try {
			Mockito.when(seatReservationService.findReservedSeats(Mockito.any())).thenReturn(reservedSeats);
		} catch (ResourceNotFoundException e) {
			e.printStackTrace();
		}
	}
	private void setUpSeatToReserve(long id) {
		List<Seat> seatsToReserve = new ArrayList<Seat>();
		Seat seatToReserve = new Seat();
		seatToReserve.setId(id);
		seatToReserve.setRow(1);
		seatToReserve.setSeatNumber((int) id);
		seatsToReserve.add(seatToReserve);
		
		Mockito.when(seatService.findByIds(Mockito.any())).thenReturn(seatsToReserve);
	}
	private void setUpReservation() {
		movieScreening = new MovieScreening();
		movieScreening.setId((long) 1);
		Instant screeningTime = Instant.now(Clock.offset(Clock.systemUTC(), Duration.ofHours(2))).plus(20, ChronoUnit.MINUTES);
		movieScreening.setScreeningTime(screeningTime);
		
		reservationDTO = new ReservationDTO();
		reservationDTO.setName("Michał");
		reservationDTO.setSurname("Janicki");
		List<TicketDTO> tickets = new ArrayList<>();
 		ticket = new TicketDTO();
 		ticket.setSeatId((long) 4);
 		ticket.setScreeningId((long) 1);
 		ticket.setTicketTypeId((long) 1);
		tickets.add(ticket);
		reservationDTO.setTickets(tickets);
	}

}
