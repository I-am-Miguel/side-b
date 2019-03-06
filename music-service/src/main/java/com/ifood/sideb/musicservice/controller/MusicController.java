package com.ifood.sideb.musicservice.controller;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifood.sideb.musicservice.domain.PlayList;
import com.ifood.sideb.musicservice.service.MusicService;
import com.ifood.sideb.musicservice.service.exception.MusicException;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class MusicController {

	private final Logger logger = LoggerFactory.getLogger(MusicController.class);
	private CacheControl cacheControl = CacheControl.maxAge(3, TimeUnit.MINUTES);
	
	@Autowired
	private MusicService musicService;
	
	@GetMapping(path="/genre/{genre}")
	public ResponseEntity<PlayList> retrieveMusicsByGenre(@PathVariable String genre) throws MusicException{
		logger.info("Retrieve Tracks By Genre: ".concat(genre));
		return ResponseEntity.ok().cacheControl(cacheControl).body(musicService.tracksByGenre(genre));
	}
}
