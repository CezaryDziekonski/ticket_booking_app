package com.mordor.model.enitity;

import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "movie_screening")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
property = "id")
public class MovieScreening {
	@Id
	@Column(name = "movie_screening_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne()
	@JoinColumn(name = "fk_movie_id", referencedColumnName = "movie_id", foreignKey=@ForeignKey(name = "fk1_movie"))
	@JsonIgnoreProperties("id")
	private Movie movie;
	
	@ManyToOne()
	@JoinColumn(name = "fk_room_id", referencedColumnName = "room_id", foreignKey=@ForeignKey(name = "fk2_room"))
	@JsonIgnore
	private Room room;
	
	@Column(name = "screening_time")
	private Instant screeningTime;
	
	public MovieScreening() {
		super();
	}

	public MovieScreening(Movie movie, Room room, Instant screeningTime) {
		super();
		this.movie = movie;
		this.room = room;
		this.screeningTime = screeningTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public Instant getScreeningTime() {
		return screeningTime;
	}

	public void setScreeningTime(Instant screeningTime) {
		this.screeningTime = screeningTime;
	}
	
	
}
