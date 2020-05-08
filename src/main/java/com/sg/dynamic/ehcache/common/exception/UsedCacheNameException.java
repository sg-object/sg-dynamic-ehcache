package com.sg.dynamic.ehcache.common.exception;

public class UsedCacheNameException extends AbstractException {

	private static final long serialVersionUID = 2629912438840669203L;

	@Override
	public String getErrorCode() {
		// TODO Auto-generated method stub
		return USED_CACHE_NAME;
	}
}
