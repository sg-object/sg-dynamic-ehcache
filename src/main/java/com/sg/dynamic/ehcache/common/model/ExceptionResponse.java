package com.sg.dynamic.ehcache.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionResponse {

	private String errorCode;
	
	private String message;
}
