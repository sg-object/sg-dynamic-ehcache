package com.sg.dynamic.ehcache.common.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CacheVo {

	@ApiModelProperty(required = true)
	private String cacheName;
	
	private String key;
	
	private String value;
}
