package com.mordor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mordor.Utils.ResourceNotFoundException;
import com.mordor.dao.TicketTypeDAO;
import com.mordor.model.enitity.TicketType;

@Service
public class TicketTypeServiceImpl implements TicketTypeService {

	@Autowired
	TicketTypeDAO ticketTypeDAO;
	
	
	@Override
	public TicketType findById(Long id) throws ResourceNotFoundException {
		return ticketTypeDAO.findById(id).orElseThrow( () -> new ResourceNotFoundException(ResourceNotFoundException.TicketTypeNotFound + id));
	}

}
