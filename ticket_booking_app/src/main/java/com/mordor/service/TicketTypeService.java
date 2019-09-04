package com.mordor.service;

import com.mordor.Utils.ResourceNotFoundException;
import com.mordor.model.enitity.TicketType;


public interface TicketTypeService {
	TicketType findById(Long id) throws ResourceNotFoundException;

}
