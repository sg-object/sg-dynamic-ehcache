package com.sg.dynamic.ehcache.common.exception;

import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.sg.dynamic.ehcache.common.model.ExceptionResponse;

@RestControllerAdvice
public class ExceptionControllerAdvice {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MessageSourceAccessor messageSourceAccessor;
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ExceptionResponse handleInternalServerErrorException(HttpServletResponse response, Exception e){
		logger.error("Internal Server Error Exception has caught.", e);
		return getExceptionResponse(AbstractException.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(NotFoundCacheException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ExceptionResponse handleNotFoundCacheException(HttpServletResponse response, NotFoundCacheException e){
		logger.error("Not Found Cache Exception has caught.", e);
		return getExceptionResponse(e.getErrorCode());
	}
	
	@ExceptionHandler(NotFoundCacheDataException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ExceptionResponse handleNotFoundCacheDataException(HttpServletResponse response, NotFoundCacheDataException e){
		logger.error("Not Found Cache Data Exception has caught.", e);
		return getExceptionResponse(e.getErrorCode());
	}
	
	@ExceptionHandler(UsedCacheNameException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ExceptionResponse handleUsedCacheNameException(HttpServletResponse response, UsedCacheNameException e){
		logger.error("Used Cache Name Exception has caught.", e);
		return getExceptionResponse(e.getErrorCode());
	}
	
	private ExceptionResponse getExceptionResponse(String errorCode){
		return new ExceptionResponse(errorCode, messageSourceAccessor.getMessage(errorCode));
	}
}
