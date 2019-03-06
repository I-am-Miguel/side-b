package com.ifood.sideb.sugestionservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ifood.sideb.sugestionservice.domain.Genre;
import com.ifood.sideb.sugestionservice.domain.PlayList;
import com.ifood.sideb.sugestionservice.domain.music.PlayListResponse;
import com.ifood.sideb.sugestionservice.domain.weather.WeatherResponse;
import com.ifood.sideb.sugestionservice.service.client.MusicServiceClient;
import com.ifood.sideb.sugestionservice.service.client.WeatherServiceClient;

@Service
public class SugestionService {

	private final Logger logger = LoggerFactory.getLogger(SugestionService.class);

	@Autowired private MusicServiceClient musicClient;
	@Autowired private WeatherServiceClient weatherServiceClient;

	private ErrorMessage error;

	public PlayList sugestionByCity(String city) {

		Genre genre = retieveGenreByCity(city);
		PlayList playList = retrieveMusicByGenre(genre);
		logger.info("PlayList generated: city -> {}, genre -> {} ",city, genre.getGenre());
		return playList;
	}
	
	public PlayList sugestionByCoordinates(String lat, String lng) {
		Genre genre = retieveGenreByCoordinates(lat,lng);
		PlayList playList = retrieveMusicByGenre(genre);
		logger.info("PlayList generated: lat -> {}, lng -> {}, genre -> {} ", lat, lng, genre.getGenre());
		return playList;
	}
	
	private PlayList retrieveMusicByGenre(Genre genre) {
		try {
			ResponseEntity<PlayListResponse> response = musicClient.retrieveMusicsByGenre(genre.getGenre());
			logger.info("Music Service Successfully Connected: genre -> {} ", genre.getGenre());
			return PlayListMapper.of(response.getBody(),genre.getMessage());
		}catch (Exception e) {
			error = ErrorMessage.UNAVAILABLE_SERVICE;
		}
		throw new RuntimeException();
	}


	private Genre retieveGenreByCity(String city) {

		try {
			ResponseEntity<WeatherResponse> response = weatherServiceClient.retrieveWeatherByCity(city);
			logger.info("Weather Service Successfully Connected: city -> {}", city);
			return retriveGenreByTemp(response.getBody());
		}catch (feign.FeignException e) {
			if(HttpStatus.NOT_FOUND.value() == e.status()) {
				error = ErrorMessage.NOT_FOUND_NAME_CITY;
			}else{
				error = ErrorMessage.BAD_REQUEST_NAME_CITY;
			}
		}catch (Exception e) {
			error = ErrorMessage.UNAVAILABLE_SERVICE;
		}
		throw new RuntimeException();
	}
	

	private Genre retieveGenreByCoordinates(String lat, String lng) {

		try {
			ResponseEntity<WeatherResponse> response = weatherServiceClient.retrieveWeatherByLatLng(lat, lng);
			logger.info("Weather Service Successfully Connected: lat -> {}, lng -> {}", lat, lng);
			return retriveGenreByTemp(response.getBody());
		}catch (feign.FeignException e) {
			if(HttpStatus.NOT_FOUND.value() == e.status()) {
				error = ErrorMessage.NOT_FOUND_COORDINATES;
			}else{
				error = ErrorMessage.BAD_REQUEST_COORDINATES;
			}
		}catch (Exception e) {
			error = ErrorMessage.UNAVAILABLE_SERVICE;
		}
		throw new RuntimeException();
	}
	
	private Genre retriveGenreByTemp(WeatherResponse weather) {

		return Genre.genreByTemperature(weather.getTempCelcius().doubleValue());
	}
	
	public PlayList playListDefault() {
		PlayList playList = PlayListMapper.playListDefault();
		playList.setMessage(error.getDescription());
		return playList;
	}

}
