package com.ifood.sideb.weatherservice.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ifood.sideb.weatherservice.domain.WeatherResponse;

@Repository
public interface WeatherResponseRepository extends CrudRepository<WeatherResponse, String>{
	
	Optional<WeatherResponse> findByNameIgnoreCase(String name);
	
	Optional<WeatherResponse> findByLatAndLon(String lat, String lng);
}
