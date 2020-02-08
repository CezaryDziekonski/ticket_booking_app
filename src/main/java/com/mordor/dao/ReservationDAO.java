package com.mordor.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mordor.model.enitity.Reservation;

@Repository
public interface ReservationDAO extends CrudRepository<Reservation, Long> {

}
