package com.ifood.sideb.musicservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties("music-service")
public class MusicConfiguration {

	private String hostTrack;
	private String hostToken;
	private String clientId;
	private String clientSecret;
}
