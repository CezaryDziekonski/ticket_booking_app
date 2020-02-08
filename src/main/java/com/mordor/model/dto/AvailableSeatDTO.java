package com.mordor.model.dto;

import java.util.List;

import com.mordor.model.enitity.Room;
import com.mordor.model.enitity.Seat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AvailableSeatDTO {
	private Room room;
	private List<Seat> availableSeats;
}
