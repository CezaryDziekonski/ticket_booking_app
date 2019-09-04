package com.mordor.model.dto;

import java.time.Instant;

public class MovieScreeningDTO {

	private Long id;
	
	private Instant screeningTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Instant getScreeningTime() {
		return screeningTime;
	}

	public void setScreeningTime(Instant screeningTime) {
		this.screeningTime = screeningTime;
	}

	
	
	
}
