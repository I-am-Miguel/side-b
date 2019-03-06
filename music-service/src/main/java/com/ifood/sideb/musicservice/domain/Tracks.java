package com.ifood.sideb.musicservice.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Tracks {
	
	private String href;
    private List<Items> items;
}
