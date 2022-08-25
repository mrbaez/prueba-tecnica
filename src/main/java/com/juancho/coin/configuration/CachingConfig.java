package com.juancho.coin.configuration;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.cache.CacheBuilder;

@Configuration
@EnableCaching
public class CachingConfig {

   @Value("${heroes.cacheExpiration:1}")
   private Integer cacheExpiration;

   @Bean
   public CacheManager cacheManager() {
      ConcurrentMapCacheManager cacheManager = new ConcurrentMapCacheManager() {

         @Override
         protected Cache createConcurrentMapCache(final String name) {
            return new ConcurrentMapCache(name, CacheBuilder.newBuilder().expireAfterWrite(cacheExpiration, TimeUnit.MINUTES).build().asMap(), false);
         }
      };

      cacheManager.setCacheNames(Arrays.asList("userCache", "coinCache"));
      return cacheManager;
   }
}
