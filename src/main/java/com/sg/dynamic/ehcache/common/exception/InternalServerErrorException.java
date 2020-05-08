package com.sg.dynamic.ehcache.common.exception;

public class InternalServerErrorException extends AbstractException {
	
	private static final long serialVersionUID = -6130706007186012896L;

	@Override
	public String getErrorCode() {
		// TODO Auto-generated method stub
		return INTERNAL_SERVER_ERROR;
	}
}
