package com.mordor.model.dto;

import java.time.Instant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieScreeningDTO {
	private Long id;	
	private Instant screeningTime;	
}
