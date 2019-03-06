package com.ifood.sideb.weatherservice.service.exception;

import java.util.List;

import org.springframework.validation.ObjectError;

public class WeatherException extends Exception {

	private static final long serialVersionUID = 1L;
	protected List<ObjectError> allErros;

	public WeatherException(String message) {
		super(message);
	}

	public List<ObjectError> getAllErros() {
		return allErros;
	}
}
