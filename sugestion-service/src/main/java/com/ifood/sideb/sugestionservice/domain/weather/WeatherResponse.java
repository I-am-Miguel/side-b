package com.ifood.sideb.sugestionservice.domain.weather;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponse {

	private String id;
	private String dt;
	private String cod;
	private String visibility;
	private String name;
	private String base;
	private List<Weather> weather;
	private Main main;
	private Sys sys;
	private BigDecimal tempCelcius;

}
