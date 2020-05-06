package com.sg.dynamic.ehcache.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@EnableCaching
@Configuration
public class CacheConfig {

	@Bean
	public EhCacheManagerFactoryBean ehCacheManagerFactoryBean(){
		EhCacheManagerFactoryBean factoryBean = new EhCacheManagerFactoryBean();
		factoryBean.setConfigLocation(new ClassPathResource("config/ehcache.xml"));
		factoryBean.setShared(true);
		return factoryBean;
	}
	
	@Bean
	public EhCacheCacheManager ehCacheCacheManager(EhCacheManagerFactoryBean ehCacheManagerFactoryBean){
		EhCacheCacheManager cacheManager = new EhCacheCacheManager();
		cacheManager.setCacheManager(ehCacheManagerFactoryBean.getObject());
		return cacheManager;
	}
}
