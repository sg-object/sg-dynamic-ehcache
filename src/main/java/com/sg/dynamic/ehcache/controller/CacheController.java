package com.sg.dynamic.ehcache.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.sg.dynamic.ehcache.common.model.CacheVo;
import com.sg.dynamic.ehcache.service.CacheService;

@RestController
@RequestMapping("/cache")
public class CacheController {

	@Autowired
	private CacheService cacheService;

	@GetMapping("/{cacheName}")
	public Map<String, Object> getCacheData(@PathVariable("cacheName") String cacheName){
		return cacheService.getCacheData(cacheName);
	}
	
	@PostMapping
	public void createCache(@RequestParam("cacheName")String cacheName){
		cacheService.createCache(cacheName);
	}
	
	@DeleteMapping("/{cacheName}")
	public void removeCache(@PathVariable("cacheName") String cacheName){
		cacheService.removeCache(cacheName);
	}
	
	@PostMapping("/{cacheName}")
	public void addCacheData(@RequestBody CacheVo cacheVo){
		cacheService.addCacheData(cacheVo);
	}
	
	@DeleteMapping("/{cacheName}/{key}")
	public void removeCacheData(@PathVariable("cacheName") String cacheName, @PathVariable("key") String key){
		cacheService.removeCacheData(cacheName, key);
	}

	@PostMapping("/csv")
	public void createCacheByCSV(@RequestParam("cacheName")String cacheName, @RequestParam("csv")MultipartFile csv){
		cacheService.createCacheByCSV(cacheName, csv);
	}
	
	@PutMapping("/csv")
	public void initCacheByCSV(@RequestParam("cacheName")String cacheName, @RequestParam("csv")MultipartFile csv){
		cacheService.initCacheByCSV(cacheName, csv);
	}
}
