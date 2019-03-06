package com.ifood.sideb.musicservice.domain;

import lombok.Data;

@Data
public class Token {

	private String access_token;
    private String scope;
    private String token_type;
    private String expires_in;
}
