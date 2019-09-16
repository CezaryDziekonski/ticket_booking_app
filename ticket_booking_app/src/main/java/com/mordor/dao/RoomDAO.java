package com.mordor.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mordor.model.enitity.Room;

@Repository
public interface RoomDAO extends CrudRepository<Room, Long> {

}
