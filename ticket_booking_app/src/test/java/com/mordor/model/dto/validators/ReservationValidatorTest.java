package com.mordor.model.dto.validators;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.DataBinder;
import org.springframework.validation.FieldError;
import com.mordor.model.dto.ReservationDTO;
import com.mordor.model.dto.TicketDTO;

@SpringBootTest
class ReservationValidatorTest {

	private ReservationValidator validator = new ReservationValidator();
	
	private ReservationDTO reservationDTO = new ReservationDTO();
	
	private DataBinder binder;

	@BeforeEach
	public void setUp()  {
		reservationDTO.setName("Michał");
		reservationDTO.setSurname("Jasnowski");
		
		List<TicketDTO> tickets = new ArrayList<>();
 		TicketDTO ticket = new TicketDTO();
 		ticket.setSeatId((long) 1);
 		ticket.setScreeningId((long) 1);
 		ticket.setTicketTypeId((long) 1);
		tickets.add(ticket);
		reservationDTO.setTickets(tickets);
		
		binder = new DataBinder(reservationDTO);
		binder.setValidator(validator);
	}
	
	@Test
	void testNameValidation() {
		FieldError fe;
		binder.validate();
		fe = binder.getBindingResult().getFieldError();
		assertNull(fe);
		
		setUp();
		reservationDTO.setName("Mi");
		binder.validate();
		fe = binder.getBindingResult().getFieldError();
		assertEquals("Mi", fe.getRejectedValue());
		
		setUp();
		reservationDTO.setName("michal");
		binder.validate();
		fe = binder.getBindingResult().getFieldError();
		assertEquals("michal", fe.getRejectedValue());
	}
	
	@Test
	void testSurnameValidation() {
		FieldError fe;
		
		reservationDTO.setSurname("Ja");
		binder.validate();
		fe = binder.getBindingResult().getFieldError();
		assertEquals("Ja", fe.getRejectedValue());
		
		setUp();
		reservationDTO.setSurname("jasnowski");
		binder.validate();
		fe = binder.getBindingResult().getFieldError();
		assertEquals("jasnowski", fe.getRejectedValue());
		
		setUp();
		reservationDTO.setSurname("jasnowski michalski");
		binder.validate();
		fe = binder.getBindingResult().getFieldError();
		assertEquals("jasnowski michalski", fe.getRejectedValue());
		
		setUp();
		reservationDTO.setSurname("Jasnowski-Michalski");
		binder.validate();
		fe = binder.getBindingResult().getFieldError();
		assertNull(fe);
	}
	
	@Test
	void testNumberOfTicketValidation() {
		FieldError fe;
		
		reservationDTO.setTickets(new ArrayList<TicketDTO>());
		binder.validate();
		fe = binder.getBindingResult().getFieldError();
		assertEquals("tickets", fe.getField());
	}

}
