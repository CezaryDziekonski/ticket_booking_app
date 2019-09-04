package com.mordor.model.dto;

import java.util.List;

public class ScreeningsDTO {

	private String movieTitle;
	
	private List<MovieScreeningDTO> screeningsList;

	public String getMovieTitle() {
		return movieTitle;
	}

	public void setMovieTitle(String movieTitle) {
		this.movieTitle = movieTitle;
	}

	public List<MovieScreeningDTO> getScreeningsList() {
		return screeningsList;
	}

	public void setScreeningsList(List<MovieScreeningDTO> screeningsList) {
		this.screeningsList = screeningsList;
	}

	
	
	
}
