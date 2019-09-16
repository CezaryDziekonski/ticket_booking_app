package com.mordor.model.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScreeningsDTO {
	private String movieTitle;	
	private List<MovieScreeningDTO> screeningsList;	
}
