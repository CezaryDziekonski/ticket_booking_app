package com.mordor.model;

import java.util.List;
import com.mordor.model.enitity.Reservation;
import com.mordor.model.enitity.SeatReservation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserReservation {
	private List<SeatReservation> seatReservation;
	private Reservation reservation;
}
