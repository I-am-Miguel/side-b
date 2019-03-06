package com.ifood.sideb.weatherservice.controller.exception;

import java.util.List;

import org.springframework.validation.ObjectError;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetails {

	private String title;
	private Integer status;
	private Long timestamp;
	private String message;
	private List<ObjectError> cause;

}
