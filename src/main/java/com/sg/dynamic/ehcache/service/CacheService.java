package com.sg.dynamic.ehcache.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.sg.dynamic.ehcache.builder.CacheBuilder;
import com.sg.dynamic.ehcache.common.exception.InternalServerErrorException;
import com.sg.dynamic.ehcache.common.exception.NotFoundCacheDataException;
import com.sg.dynamic.ehcache.common.exception.NotFoundCacheException;
import com.sg.dynamic.ehcache.common.exception.UsedCacheNameException;
import com.sg.dynamic.ehcache.common.model.CacheVo;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

@Service
public class CacheService {

	@Autowired
	private CacheBuilder cacheBuilder;
	
	@Value("${ehcache.csv.path}")
	private String csvPath;
	
	private final String comma = ",";

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public void createCache(String cacheName){
		cacheBuilder.createCacheAndAddName(cacheName);
	}
	
	public void removeCache(String cacheName){
		cacheBuilder.removeCache(cacheName);
	}
	
	public Map<String, Object> getCacheData(String cacheName){
		Ehcache cache = cacheBuilder.getEhcache(cacheName);
		Map<String, Object> result = new HashMap<>();
		cache.getAll(cache.getKeys()).forEach((key, value) -> {
			result.put(key.toString(), value);
		});
		return result;
	}
	
	public void addCacheData(CacheVo cacheVo){
		Ehcache ehcache = cacheBuilder.getEhcache(cacheVo.getCacheName());
		ehcache.put(new Element(cacheVo.getKey(), cacheVo.getValue()));
		logger.info("========= add cache data : {} - {} =========", cacheVo.getCacheName(), cacheVo.getKey());
	}
	
	public void removeCacheData(String cacheName, String key){
		Ehcache ehcache = cacheBuilder.getEhcache(cacheName);
		if(ehcache.get(key) != null){
			ehcache.remove(key);
			logger.info("========= remove cache data : {} - {} =========", cacheName, key);	
		}else{
			throw new NotFoundCacheDataException();
		}
	}
	
	public void createCacheByCSV(String cacheName, MultipartFile csv){
		if(cacheBuilder.checkCacheName(cacheName)){
			throw new UsedCacheNameException();
		}
		cacheBuilder.createCacheAndAddName(cacheName);
		Ehcache cache = cacheBuilder.getEhcache(cacheName);
		putCacheDataByCSV(cache, csv);
	}
	
	public void initCacheByCSV(String cacheName, MultipartFile csv){
		if(!cacheBuilder.checkCacheName(cacheName)){
			throw new NotFoundCacheException();
		}
		Ehcache cache = cacheBuilder.getEhcache(cacheName);
		cache.removeAll();
		putCacheDataByCSV(cache, csv);
	}
	
	private void putCacheDataByCSV(Ehcache cache, MultipartFile csv){
		try {
			File file = getCSVFile(csv);
			BufferedReader br = Files.newBufferedReader(Paths.get(file.getAbsolutePath()));
			String line = "";
			while((line = br.readLine()) != null){
				String[] data = line.split(comma);
				// [0] : key , [1] : value
				cache.put(new Element(data[0].trim(), data[1].trim()));
			}
			file.delete();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// Roll Back Cache
			cacheBuilder.removeCache(cache.getName());
			throw new InternalServerErrorException();
		}
		logger.info("========= put cache data by CSV : {} =========", cache.getName());
	}
	
	private File getCSVFile(MultipartFile csv) throws IOException {
		File file = new File(csvPath + csv.getOriginalFilename());
	    file.createNewFile();
	    FileOutputStream fos = new FileOutputStream(file);
	    fos.write(csv.getBytes());
	    fos.close();
	    return file;
	}
}
