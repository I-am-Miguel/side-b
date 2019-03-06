package com.ifood.sideb.sugestionservice.domain;

import java.util.Arrays;
import java.util.Objects;

public enum Genre {

	PARTY("Party", 30D, null,"The weather is perfect for a party. Temperature: {temp}"), 

	POP("Pop", 15D, 30D,"It is a little warm out there, "
			+ "what do you think of the playList to cheer up. Temperature: {temp}"), 

	ROCK("Rock", 10D, 15D,"It is a bit chilly out there, "
			+ "what do you think of playList to warm up. Temperature: {temp}"), 

	CLASSIC("Classical", null, 10D,"It is freezing outside, "
			+ "how about lying down listening to this playlist. Temperature: {temp}");

	private String genre; 
	private Double minTemp;
	private Double maxTemp;
	private String message;
	
	public static Genre genreByTemperature(Double temp) {
		
		  Genre genreFilter = Arrays.stream(Genre.values())
			.filter(g -> Objects.isNull(g.getMaxTemp()) || g.getMaxTemp() > temp)
			.filter(g -> Objects.isNull(g.getMinTemp()) || g.getMinTemp() <= temp)
			.findFirst().orElse(POP);
		  
		  genreFilter.setMessage(genreFilter.getMessage().replace("{temp}", temp.toString()));
		  return genreFilter;
	}
	

	private Genre(String genre, Double minTemp, Double maxTemp, String message) {
		this.genre = genre;
		this.minTemp = minTemp;
		this.maxTemp = maxTemp;
		this.message = message;
	}

	public String getGenre() {
		return genre;
	}

	public Double getMinTemp() {
		return minTemp;
	}

	public Double getMaxTemp() {
		return maxTemp;
	}

	public String getMessage() {
		return message;
	}

	private void setMessage(String message) {
		this.message = message;
	}
	
}
