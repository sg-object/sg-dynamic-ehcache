package com.sg.dynamic.ehcache.service;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sg.dynamic.ehcache.builder.CacheBuilder;
import com.sg.dynamic.ehcache.common.model.CacheVo;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

@Service
public class CacheService {

	@Autowired
	private CacheBuilder cacheBuilder;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public void createCache(String cacheName){
		cacheBuilder.createCacheAndAddName(cacheName);
	}
	
	public void removeCache(String cacheName){
		cacheBuilder.removeCache(cacheName);
	}
	
	public Map<String, Object> getCacheData(String cacheName){
		Ehcache cache = cacheBuilder.getEhcache(cacheName);
		if(cache == null || cache.getSize() < 1){
			return null;
		}else{
			Map<String, Object> result = new HashMap<>();
			cache.getAll(cache.getKeys()).forEach((key, value) -> {
				result.put(key.toString(), value);
			});
			return result;
		}
	}
	
	public void addCacheData(CacheVo cacheVo){
		Ehcache ehcache = cacheBuilder.getEhcache(cacheVo.getCacheName());
		if(ehcache != null){
			ehcache.put(new Element(cacheVo.getKey(), cacheVo.getValue()));
			logger.info("========= add cache data : {} - {} =========", cacheVo.getCacheName(), cacheVo.getKey());
		}else{
			throw new RuntimeException();
		}
	}
	
	public void removeCacheData(String cacheName, String key){
		Ehcache ehcache = cacheBuilder.getEhcache(cacheName);
		if(ehcache != null){
			ehcache.remove(key);
			logger.info("========= remove cache data : {} - {} =========", cacheName, key);
		}else{
			throw new RuntimeException();
		}
	}
}
