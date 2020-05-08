package com.sg.dynamic.ehcache.builder;

import java.io.File;
import java.util.Optional;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Component;
import net.sf.ehcache.Cache;
import net.sf.ehcache.DiskStorePathManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;

@Component
public class CacheBuilder {

	@Autowired
	private EhCacheCacheManager cacheManager;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Value("${ehcache.cacheNames.cacheName}")
	private String cacheNames;
	
	@Value("${ehcache.diskStore.suffix}")
	private String[] suffix;
	
	@PostConstruct
	private void initCache(){
		Ehcache cache = getEhcache(cacheNames);
		cache.getKeys().forEach(cacheName -> {
			logger.info("========= init cache : {} =========", cacheName);
			createCache(cacheName.toString());
		});
	}
	
	public void createCacheAndAddName(String cacheName){
		// Cache Name Add & Check
		checkNameCache(cacheName);
		addCacheName(cacheName);
		createCache(cacheName);
	}
	
	public void removeCache(String cacheName){
		checkNameCache(cacheName);
		Ehcache ehcache = getEhcache(cacheNames);
		if(ehcache.get(cacheName) != null){
			ehcache.remove(cacheName);
			cacheManager.getCache(cacheName).clear();
			cacheManager.getCacheManager().removeCache(cacheName);
			logger.info("========= remove cache : {} =========", cacheName);
			DiskStorePathManager diskStorePathManager = cacheManager.getCacheManager().getDiskStorePathManager();
			for(String value : suffix){
				File file = diskStorePathManager.getFile(cacheName, value);
				if(file.exists()){
					logger.info("========= remove cache file : {} =========", file.getName());
					file.delete();
				}
			}
		}else{
			logger.info("========= not found cache : {} =========", cacheName);
		}
	}
	
	public Ehcache getEhcache(String cacheName){
		return Optional.ofNullable(cacheManager.getCacheManager().getCache(cacheName)).orElseThrow(() -> new RuntimeException());
	}
	
	public boolean checkCacheName(String cacheName){
		Ehcache cache = getEhcache(cacheNames);
		return cache.get(cacheName) != null ? true : false;
	}
	
	private void createCache(String cacheName){
		CacheConfiguration cacheConfig = new CacheConfiguration();
		cacheConfig.eternal(true);
		cacheConfig.maxEntriesLocalHeap(10000);
		cacheConfig.name(cacheName);
		
		//conf.persistence(new PersistenceConfiguration().strategy(Strategy.LOCALRESTARTABLE).synchronousWrites(true));
		cacheConfig.setDiskPersistent(true);
		cacheConfig.overflowToDisk(true);

		cacheManager.getCacheManager().addCache(new Cache(cacheConfig));
		logger.info("========= create cache : {} =========", cacheName);
	}
	
	private void addCacheName(String cacheName){
		Ehcache ehcache = getEhcache(cacheNames);
		if(ehcache.get(cacheName) == null){
			ehcache.put(new Element(cacheName, cacheName));			
		}else{
			throw new RuntimeException();
		}
	}
	
	private void checkNameCache(String cacheName){
		if(cacheNames.equals(cacheName)){
			throw new RuntimeException();
		}
	}
}
