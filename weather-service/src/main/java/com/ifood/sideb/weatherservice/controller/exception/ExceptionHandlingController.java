package com.ifood.sideb.weatherservice.controller.exception;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ifood.sideb.weatherservice.service.exception.BadRequestException;
import com.ifood.sideb.weatherservice.service.exception.NotFoundException;
import com.ifood.sideb.weatherservice.service.exception.WeatherException;

@ControllerAdvice
public class ExceptionHandlingController {

	private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlingController.class);
	
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ErrorDetails> handlerNotFoundException(WeatherException e, HttpServletRequest request) {
		ErrorDetails error = generateErrorDetails(HttpStatus.NOT_FOUND, e);

		logger.error("Resources not Found for the Parameters Reported");
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
	
	
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ErrorDetails> handlerBadRequestException(WeatherException e, HttpServletRequest request) {
		ErrorDetails error = generateErrorDetails(HttpStatus.BAD_REQUEST, e);

		logger.error("Request With Invalid Syntax");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	
	private static ErrorDetails generateErrorDetails(HttpStatus status, WeatherException e) {
		return ErrorDetails.builder()
					.status(status.value())
					.title(status.name())
					.timestamp(System.currentTimeMillis())
					.message(e.getMessage())
					.cause(e.getAllErros())
					.build();
	}
}
