package com.mordor.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mordor.model.enitity.Movie;

@Repository
public interface MovieDAO extends CrudRepository<Movie, Long> {	

}
