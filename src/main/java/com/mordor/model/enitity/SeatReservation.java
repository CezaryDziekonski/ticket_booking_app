package com.mordor.model.enitity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "seat_reservation",
		uniqueConstraints = @UniqueConstraint (columnNames = {"FK_SEAT_ID", "FK_MOVIE_SCREENING_ID"}))
public class SeatReservation {
	
	@Id
	@Column(name = "seat_reservation_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne()
	@JoinColumn(name = "fk_seat_id", referencedColumnName = "seat_id", foreignKey=@ForeignKey(name = "fk1_seat"))
	@JsonIgnoreProperties("seatReservation")
	private Seat seat;
	
	@ManyToOne()
	@JoinColumn(name = "fk_movie_screening_id", referencedColumnName = "movie_screening_id", foreignKey=@ForeignKey(name = "fk2_movie_screening"))
	@JsonIgnoreProperties("seatReservations")
	private MovieScreening movieScreening;
	
	@ManyToOne()
	@JoinColumn(name = "fk_reservation_id", referencedColumnName = "reservation_id", foreignKey=@ForeignKey(name = "fk4_reservation"))
	private Reservation reservation;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fk_ticket_type_id", referencedColumnName = "ticket_type_id", foreignKey=@ForeignKey(name = "fk5_ticket_type"))
	private TicketType ticketType;
}
