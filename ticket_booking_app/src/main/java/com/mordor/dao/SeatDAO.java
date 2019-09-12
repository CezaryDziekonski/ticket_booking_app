package com.mordor.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mordor.model.enitity.Room;
import com.mordor.model.enitity.Seat;

@Repository
public interface SeatDAO extends CrudRepository<Seat, Long> {
	Iterable<Seat> findByRoom(Room room);
	List<Seat> findByIdIn(List<Long> ids);
}
