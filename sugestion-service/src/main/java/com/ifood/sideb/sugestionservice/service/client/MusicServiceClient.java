package com.ifood.sideb.sugestionservice.service.client;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ifood.sideb.sugestionservice.domain.music.PlayListResponse;

@FeignClient(name="music-service")
@RibbonClient(name="music-service")
public interface MusicServiceClient {

	@GetMapping(path="/genre/{genre}")
	public ResponseEntity<PlayListResponse> retrieveMusicsByGenre(@PathVariable("genre") String genre);
	
}
