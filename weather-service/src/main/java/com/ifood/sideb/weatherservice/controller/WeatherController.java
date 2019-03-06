package com.ifood.sideb.weatherservice.controller;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifood.sideb.weatherservice.domain.WeatherResponse;
import com.ifood.sideb.weatherservice.service.WeatherService;
import com.ifood.sideb.weatherservice.service.exception.WeatherException;

import io.swagger.annotations.Api;

@RestController
@Api(value = "Controller Weather", produces = MediaType.APPLICATION_JSON_VALUE)
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class WeatherController {

	private final Logger logger = LoggerFactory.getLogger(WeatherController.class);
	private CacheControl cacheControl = CacheControl.maxAge(5, TimeUnit.MINUTES);

	@Autowired
	private WeatherService weatherService;

	@GetMapping("/city/{city}")
	public ResponseEntity<WeatherResponse> retrieveWeatherByCity(@PathVariable String city) throws WeatherException {
		logger.info("Retrieve Weather By City: ".concat(city));
		return ResponseEntity.ok().cacheControl(cacheControl).body(weatherService.retrieveByCity(city));
	}

	@GetMapping("/coordinates/{lat}/{lng}")
	public ResponseEntity<WeatherResponse> retrieveWeatherByLatLng(@PathVariable String lat, @PathVariable String lng) throws WeatherException  {
		logger.info("Retrieve Weather By Latitude: ".concat(lat).concat(" and Longitude: ").concat(lng));
		return ResponseEntity.ok().cacheControl(cacheControl).body(weatherService.retrieveByCoordinates(lat, lng));
	}
}
