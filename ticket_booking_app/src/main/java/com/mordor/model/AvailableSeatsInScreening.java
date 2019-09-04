package com.mordor.model;

import java.util.List;

import com.mordor.model.enitity.Room;
import com.mordor.model.enitity.Seat;

public class AvailableSeatsInScreening {
	
	private Room room;
	private List<Seat> availableSeats;

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public List<Seat> getAvailableSeats() {
		return availableSeats;
	}

	public void setAvailableSeats(List<Seat> availableSeats) {
		this.availableSeats = availableSeats;
	}
	


}
