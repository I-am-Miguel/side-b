package com.ifood.sideb.musicservice.service.exception;

import java.util.List;

import org.springframework.validation.ObjectError;

public class MusicException extends Exception {

	private static final long serialVersionUID = 1L;
	protected List<ObjectError> allErros;

	public MusicException(String message) {
		super(message);
	}

	public List<ObjectError> getAllErros() {
		return allErros;
	}
}
