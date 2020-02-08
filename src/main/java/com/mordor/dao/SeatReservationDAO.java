package com.mordor.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.mordor.model.enitity.MovieScreening;
import com.mordor.model.enitity.SeatReservation;

@Repository
public interface SeatReservationDAO extends CrudRepository<SeatReservation, Long>{
	Iterable<SeatReservation> findByMovieScreening(MovieScreening movieScreening);
	List<SeatReservation> findByMovieScreeningIn(List<MovieScreening> list);
}
