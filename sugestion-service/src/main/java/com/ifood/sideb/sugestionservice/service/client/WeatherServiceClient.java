package com.ifood.sideb.sugestionservice.service.client;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ifood.sideb.sugestionservice.domain.weather.WeatherResponse;

@FeignClient(name="weather-service")
@RibbonClient(name="weather-service")
public interface WeatherServiceClient {

	@GetMapping("/city/{city}")
	public ResponseEntity<WeatherResponse> retrieveWeatherByCity(@PathVariable("city") String city);
	
	@GetMapping("/coordinates/{lat}/{lng}")
	public ResponseEntity<WeatherResponse> retrieveWeatherByLatLng(@PathVariable("lat") String lat, @PathVariable("lng") String lng);
}
