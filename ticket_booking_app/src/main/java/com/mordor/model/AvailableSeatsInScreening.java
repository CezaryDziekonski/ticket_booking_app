package com.mordor.model;

import com.mordor.model.enitity.Room;
import com.mordor.model.enitity.Seat;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AvailableSeatsInScreening {
	private Room room;
	private List<Seat> availableSeats;
}
