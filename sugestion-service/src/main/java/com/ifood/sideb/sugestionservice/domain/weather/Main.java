package com.ifood.sideb.sugestionservice.domain.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Main {
	
	private String temp;
	private String pressure;
	private String humidity;
	private String temp_min;
	private String temp_max;

}
