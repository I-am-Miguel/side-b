package com.ifood.sideb.sugestionservice.domain.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Sys {

	private String country;
}
