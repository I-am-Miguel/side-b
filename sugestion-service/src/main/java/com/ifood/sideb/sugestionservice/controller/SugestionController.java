package com.ifood.sideb.sugestionservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifood.sideb.sugestionservice.domain.PlayList;
import com.ifood.sideb.sugestionservice.service.SugestionService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
@RequestMapping(path="/sugestion", produces = MediaType.APPLICATION_JSON_VALUE)
public class SugestionController {

	@Autowired
	private SugestionService sugestionService;

	@GetMapping("/city/{city}")
	@HystrixCommand(fallbackMethod = "defaultSugestionCity")
	public ResponseEntity<PlayList> retrieveSugestion(@PathVariable String city) {

		return ResponseEntity.ok(sugestionService.sugestionByCity(city));
	}
	
	@GetMapping("/coordinates/{lat}/{lng}")
	@HystrixCommand(fallbackMethod = "defaultSugestionCoordinates")
	public ResponseEntity<PlayList> retrieveSugestion(@PathVariable String lat,@PathVariable String lng) {

		return ResponseEntity.ok(sugestionService.sugestionByCoordinates(lat, lng));
	}

	public ResponseEntity<PlayList> defaultSugestionCity(String city) {

		return ResponseEntity.ok(sugestionService.playListDefault());
	}
	
	public ResponseEntity<PlayList> defaultSugestionCoordinates(String lat, String lng) {

		return ResponseEntity.ok(sugestionService.playListDefault());
	}
}
