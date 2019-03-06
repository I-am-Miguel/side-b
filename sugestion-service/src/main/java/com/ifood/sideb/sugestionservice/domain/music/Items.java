package com.ifood.sideb.sugestionservice.domain.music;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Items {

	private String name;
}
