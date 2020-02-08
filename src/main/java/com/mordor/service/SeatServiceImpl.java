package com.mordor.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.mordor.dao.SeatDAO;
import com.mordor.exception.ResourceNotFoundException;
import com.mordor.model.enitity.Room;
import com.mordor.model.enitity.Seat;

@Service
public class SeatServiceImpl implements SeatService{
	
	private SeatDAO seatDAO;
	
	@Autowired
	public SeatServiceImpl(SeatDAO seatDAO) {
		this.seatDAO = seatDAO;
	}

	@Override
	public Seat findById(Long id) {
		return seatDAO.findById(id).orElseThrow( 
				() -> new ResourceNotFoundException("Seat with id: " + id + " not found"));
	}
	
	@Override
	public List<Seat> findByIds(List<Long> idList) {
		return seatDAO.findByIdIn(idList);
	}

	@Override
	public List<Seat> findByRoom(Room room) {
		return Lists.newArrayList(seatDAO.findByRoom(room));
	}	
}
