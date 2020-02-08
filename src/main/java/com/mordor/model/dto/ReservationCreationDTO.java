package com.mordor.model.dto;

import java.util.List;

import com.mordor.model.enitity.MovieScreening;
import com.mordor.model.enitity.Seat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationCreationDTO {	
	private String name;	
	private String surname;	
	private MovieScreening movieScreening;	
	private List<Seat> seats;
}
