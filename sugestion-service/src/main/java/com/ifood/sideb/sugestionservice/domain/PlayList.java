package com.ifood.sideb.sugestionservice.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PlayList {

	private String message;
	private List<String> results;
}
