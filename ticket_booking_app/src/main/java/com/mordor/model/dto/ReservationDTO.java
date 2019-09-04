package com.mordor.model.dto;

import java.util.List;

public class ReservationDTO {
	
	private String name;
	
	private String surname;
	
	private List<TicketDTO> tickets;
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public List<TicketDTO> getTickets() {
		return tickets;
	}

	public void setTickets(List<TicketDTO> tickets) {
		this.tickets = tickets;
	}

	

	
	

}
