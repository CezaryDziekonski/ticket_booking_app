package com.mordor.service;

import java.util.List;

import com.mordor.model.enitity.Room;
import com.mordor.model.enitity.Seat;

public interface SeatService {
	Seat findById(Long id);
	List<Seat> findByIds(List<Long> idList);
	List<Seat> findByRoom(Room room);
	
}
