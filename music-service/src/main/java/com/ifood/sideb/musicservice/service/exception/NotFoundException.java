package com.ifood.sideb.musicservice.service.exception;

import java.util.List;

import org.springframework.validation.ObjectError;

public class NotFoundException extends MusicException {

	private static final long serialVersionUID = 1L;

	public NotFoundException() {
		super("Resources not Found for the Parameters Reported");
	}

	public NotFoundException(List<ObjectError> allErrors) {
		this();
		super.allErros = allErrors;
	}

}
