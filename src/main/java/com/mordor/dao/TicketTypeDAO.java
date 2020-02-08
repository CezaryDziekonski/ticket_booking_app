package com.mordor.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.mordor.model.enitity.TicketType;

@Repository
public interface TicketTypeDAO extends CrudRepository<TicketType, Long>{
	
}
