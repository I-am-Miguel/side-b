package com.ifood.sideb.weatherservice.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@RedisHash("WeatherResponse")
public class WeatherResponse implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id private String id;
	@Indexed private String lat;
	@Indexed private String lon;
	private String dt;
	private String cod;
	private String visibility;
	@Indexed private String name;
	private String base;
	@Reference private List<Weather> weather;
	private Main main;
	private Sys sys;
	private Coord coord;
	
	@JsonIgnore
	private Long creation;
	
	public BigDecimal getTempCelcius() {
		if(Objects.isNull(main)) return BigDecimal.ZERO;
		return new BigDecimal(main.getTemp()).subtract(new BigDecimal("273.15"));
	}

	@JsonIgnore
	public boolean isValid() {
		long minutes = TimeUnit.MILLISECONDS.toMinutes((creation - System.currentTimeMillis()));
		return minutes <= 5;
	}
}
