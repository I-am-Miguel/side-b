package com.ifood.sideb.sugestionservice.service;

public enum ErrorMessage {

	BAD_REQUEST_NAME_CITY("We were unable to complete the connection with the name of the city informed, we are very sorry, please enjoy our playlists"),
	BAD_REQUEST_COORDINATES("We were unable to complete the link with the entered coordinates, we are very sorry, please enjoy our playlists"), 
	NOT_FOUND_NAME_CITY("We could not find a city with the name informed, we are very sorry, please enjoy our playlists"),
	NOT_FOUND_COORDINATES("We could not find a location with the coordinates entered, we are very sorry, please enjoy our playlists"),
	UNAVAILABLE_SERVICE("One of our services is currently unavailable, we are very sorry, please enjoy our playlists");

	private String description;
	
	private ErrorMessage(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

}
