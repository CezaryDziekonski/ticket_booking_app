package com.mordor.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mordor.model.enitity.MovieScreening;

@Repository
public interface MovieScreeningDAO extends CrudRepository<MovieScreening, Long> {

}
