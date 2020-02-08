package com.mordor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mordor.dao.TicketTypeDAO;
import com.mordor.exception.ResourceNotFoundException;
import com.mordor.model.enitity.TicketType;

@Service
public class TicketTypeServiceImpl implements TicketTypeService {

	TicketTypeDAO ticketTypeDAO;
	
	@Autowired
	public TicketTypeServiceImpl(TicketTypeDAO ticketTypeDAO) {
		this.ticketTypeDAO = ticketTypeDAO;
	}
	
	@Override
	public TicketType findById(Long id) {
		return ticketTypeDAO.findById(id).orElseThrow( () -> 
			new ResourceNotFoundException("Ticket type with id: " + id +" not found"));
	}
}
