package com.ifood.sideb.weatherservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.google.common.collect.Lists;
import com.ifood.sideb.weatherservice.config.WeatherConfiguration;
import com.ifood.sideb.weatherservice.domain.WeatherResponse;
import com.ifood.sideb.weatherservice.repository.WeatherResponseRepository;
import com.ifood.sideb.weatherservice.service.exception.BadRequestException;
import com.ifood.sideb.weatherservice.service.exception.NotFoundException;
import com.ifood.sideb.weatherservice.service.exception.WeatherException;

@Service
public class WeatherService {

	@Autowired private RestTemplate restTemplate;
	@Autowired private WeatherConfiguration configuration;
	@Autowired private WeatherResponseRepository repository;

	public WeatherResponse retrieveByCity(String city) throws WeatherException {
		try {
			if(!city.replaceAll(" ", "").trim().chars().allMatch(Character::isAlphabetic)) throw new Exception("City name is not letters");
			
			Optional<WeatherResponse> cache = repository.findByNameIgnoreCase(city.toUpperCase());
			if(cache.isPresent() ) {
				if(cache.get().isValid()) {
					return cache.get();
				}
				repository.delete(cache.get());
			}
			
			String uri = configuration.getCity();
			ResponseEntity<WeatherResponse> responseEntity = restTemplate.getForEntity(uri, WeatherResponse.class, city,configuration.getKey());
			WeatherResponse response = responseEntity.getBody();
			normalizeResponse(response);
			return repository.save(response);
			
		}catch (HttpClientErrorException e) {
			throw new NotFoundException(Lists.newArrayList(new ObjectError("City Name",e.getMessage())));
		}catch (Exception e) {
			throw new BadRequestException(Lists.newArrayList(new ObjectError("City Name",e.getMessage())));
		}
	}

	public WeatherResponse retrieveByCoordinates(String lat, String lng) throws WeatherException {
		try {
			if(lat.chars().anyMatch(Character::isAlphabetic)
					|| lng.chars().anyMatch(Character::isAlphabetic)) throw new Exception("Coordinates Values is not number");

			Optional<WeatherResponse> cache = repository.findByLatAndLon(lat, lng);
			if(cache.isPresent() ) {
				if(cache.get().isValid()) {
					return cache.get();
				}
				repository.delete(cache.get());
			}

			String uri = configuration.getCoordinates();
			ResponseEntity<WeatherResponse> responseEntity = restTemplate.getForEntity(uri, WeatherResponse.class, lat, lng, configuration.getKey());
			WeatherResponse response = responseEntity.getBody();
			normalizeResponse(response);
			return repository.save(response);
		}catch (HttpClientErrorException e) {
			throw new NotFoundException(Lists.newArrayList(new ObjectError("Coordinates Values",e.getMessage())));
		}catch (Exception e) {
			throw new BadRequestException(Lists.newArrayList(new ObjectError("Coordinates Values",e.getMessage())));
		}
	}

	private void normalizeResponse(WeatherResponse response) {
		response.setCreation(System.currentTimeMillis());
		response.setName(response.getName().toUpperCase());
		response.setLat(response.getCoord().getLat());
		response.setLon(response.getCoord().getLon());
	}
	
}
