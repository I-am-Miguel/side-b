package com.ifood.sideb.weatherservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties("weather-service")
public class WeatherConfiguration {
	
	private String city;
	private String coordinates;
	private String key;
	
}
