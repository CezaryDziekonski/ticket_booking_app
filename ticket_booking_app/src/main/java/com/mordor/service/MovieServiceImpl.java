package com.mordor.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.mordor.dao.MovieDAO;
import com.mordor.model.enitity.Movie;


@Service
public class MovieServiceImpl implements MovieService{

	@Autowired
	private MovieDAO movieDAO;

	@Override
	public List<Movie> findAll() {
		return Lists.newArrayList(movieDAO.findAll());
	}	

}
