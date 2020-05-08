package com.sg.dynamic.ehcache.common.exception;

public class NotFoundCacheException extends AbstractException {

	private static final long serialVersionUID = 220707920811857987L;

	@Override
	public String getErrorCode() {
		// TODO Auto-generated method stub
		return NOT_FOUND_CACHE;
	}
}
