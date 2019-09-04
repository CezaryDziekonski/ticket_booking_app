package com.mordor.model.dto;

import java.util.List;

import com.mordor.model.enitity.MovieScreening;
import com.mordor.model.enitity.Seat;

public class ReservationCreationDTO {
	
	private String name;
	
	private String surname;
	
	private MovieScreening movieScreening;
	
	private List<Seat> seats;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public List<Seat> getSeats() {
		return seats;
	}

	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}

	public MovieScreening getMovieScreening() {
		return movieScreening;
	}

	public void setMovieScreening(MovieScreening movieScreening) {
		this.movieScreening = movieScreening;
	}
	
	
	
	
	
	

}
