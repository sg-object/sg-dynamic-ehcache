package com.sg.dynamic.ehcache.common.exception;

public abstract class AbstractException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public static final String NOT_FOUND_CACHE = "0001";
	
	public static final String NOT_FOUND_CACHE_DATA = "0002";
	
	public static final String USED_CACHE_NAME = "0003";
	
	public static final String INTERNAL_SERVER_ERROR = "9999";

	public abstract String getErrorCode();
}
