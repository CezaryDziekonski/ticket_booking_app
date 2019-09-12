package com.mordor.model.enitity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "seat")
public class Seat {
	
	@Id
	@Column(name = "seat_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@EqualsAndHashCode.Exclude
	@Column(name = "seat_row")
	private int row;
	
	@EqualsAndHashCode.Exclude
	@Column(name = "seat_number")
	private int seatNumber;
	
	@EqualsAndHashCode.Exclude
	@ManyToOne()
	@JoinColumn(name = "fk_room_id", referencedColumnName = "room_id", foreignKey=@ForeignKey(name = "fk1_room"))
	@JsonIgnore
	private Room room;
}
