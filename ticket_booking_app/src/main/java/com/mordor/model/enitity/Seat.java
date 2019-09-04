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

@Entity
@Table(name = "seat")

public class Seat {
	@Id
	@Column(name = "seat_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "seat_row")
	private int row;
	
	@Column(name = "seat_number")
	private int seatNumber;
	
	@ManyToOne()
	@JoinColumn(name = "fk_room_id", referencedColumnName = "room_id", foreignKey=@ForeignKey(name = "fk1_room"))
	@JsonIgnore
	private Room room;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(int seatNumber) {
		this.seatNumber = seatNumber;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	@Override
	public boolean equals(Object o)
    { 
        if (o == this) { 
            return true; 
        } 
        if (!(o instanceof Seat)) { 
            return false; 
        } 
        Seat c = (Seat) o;  
        return (Long.compare(id, c.getId()) == 0);
          
    } 
	
}
