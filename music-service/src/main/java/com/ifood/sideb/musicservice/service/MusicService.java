package com.ifood.sideb.musicservice.service;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.ObjectError;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.google.common.collect.Lists;
import com.ifood.sideb.musicservice.config.MusicConfiguration;
import com.ifood.sideb.musicservice.domain.PlayList;
import com.ifood.sideb.musicservice.domain.Token;
import com.ifood.sideb.musicservice.service.exception.BadRequestException;
import com.ifood.sideb.musicservice.service.exception.MusicException;
import com.ifood.sideb.musicservice.service.exception.NotFoundException;

@Service
public class MusicService {

	private final Logger logger = LoggerFactory.getLogger(MusicService.class);
	
	@Autowired private RestTemplate restTemplate;
	@Autowired private MusicConfiguration musicConfiguration;

	@PostConstruct
	public void configureRestTemplate(){
		restTemplate.setInterceptors(Lists.newArrayList(new BasicAuthorizationInterceptor(musicConfiguration.getClientId(), musicConfiguration.getClientSecret())));
	}
	
	public PlayList tracksByGenre(String genre) throws MusicException{
		try{
			if(!genre.replaceAll(" ", "").trim().chars().allMatch(Character::isAlphabetic)) throw new Exception("Genre is not letters");
			
			String uri = musicConfiguration.getHostTrack();
			
			ResponseEntity<PlayList> response = restTemplate.exchange(uri, HttpMethod.GET, getAuthorization(), PlayList.class, genre);
			
			logger.info("Returned Tracks to PlayList");
			return response.getBody();
		}catch (HttpClientErrorException e) {
			throw new NotFoundException(Lists.newArrayList(new ObjectError("Genre ",e.getMessage())));
		}catch (Exception e) {
			throw new BadRequestException(Lists.newArrayList(new ObjectError("Genre ",e.getMessage())));
		}
	}
	
	private HttpEntity<String> getAuthorization() {
		ResponseEntity<Token> response = generatedToken();

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + response.getBody().getAccess_token());
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		logger.info("Generated Authorization Bearer");
		return new HttpEntity<String>(headers);
	}

	private ResponseEntity<Token> generatedToken() {
		HttpHeaders headers = new HttpHeaders();

		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("grant_type", "client_credentials");

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
		ResponseEntity<Token> response = restTemplate.exchange(musicConfiguration.getHostToken(), HttpMethod.POST, request, Token.class);
		logger.info("Generated Token Access");
		return response;
	}
}
