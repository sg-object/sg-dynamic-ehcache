package com.sg.dynamic.ehcache.common.exception;

public class NotFoundCacheDataException extends AbstractException {

	private static final long serialVersionUID = 938284803544491939L;

	@Override
	public String getErrorCode() {
		// TODO Auto-generated method stub
		return NOT_FOUND_CACHE_DATA;
	}
}
