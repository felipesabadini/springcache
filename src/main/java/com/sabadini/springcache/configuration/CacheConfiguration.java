package com.sabadini.springcache.configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Ticker;
import org.springframework.cache.CacheManager;

import org.springframework.cache.annotation.*;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * https://docs.spring.io/spring/docs/5.0.0.BUILD-SNAPSHOT/spring-framework-reference/html/cache.html
 *
 * https://mvnrepository.com/artifact/org.springframework/spring-context-support/5.0.8.RELEASE
 *
 * https://github.com/danielolszewski/blog/tree/master/spring-boot-ttl-cache
 * http://dolszewski.com/spring/multiple-ttl-caches-in-spring-boot/
 * https://medium.com/@d.lopez.j/configuring-multiple-ttl-caches-in-spring-boot-dinamically-75f4aa6809f3
 **/

@Configuration
@EnableCaching
public class CacheConfiguration extends CachingConfigurerSupport {

    public static final String CACHE_PERSON = "PEOPLE";

    @Bean
    public CacheManager cacheManager(Ticker ticker) {
        SimpleCacheManager scm = new SimpleCacheManager();
        scm.setCaches(Arrays.asList(buildCache(CACHE_PERSON, ticker, 1)));
        return scm;
    }

    private CaffeineCache buildCache(String name, Ticker ticker, int minutesToExpire) {
        return new CaffeineCache(name, Caffeine.newBuilder()
                .expireAfterWrite(minutesToExpire, TimeUnit.MINUTES)
                .ticker(ticker)
                .build());
    }

    @Bean
    public Ticker ticker() {
        return Ticker.systemTicker();
    }

    @Override
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {

                final Cacheable cacheable = method.getAnnotation(Cacheable.class);
                if(cacheable != null) {
                    if(cacheable.cacheNames().length > 0) {
                        return getCacheKey(cacheable.cacheNames());
                    }
                    return "NO_CACHE";
                }

                final CacheEvict cacheEvict = method.getAnnotation(CacheEvict.class);
                if(cacheEvict != null) {
                    if(cacheEvict.cacheNames().length > 0) {
                        return getCacheKey(cacheEvict.cacheNames());
                    }
                    return "NO_CACHE";
                }

                final CachePut cachePut = method.getAnnotation(CachePut.class);
                if(cachePut != null) {
                    if(cachePut.cacheNames().length > 0) {
                        return getCacheKey(cachePut.cacheNames());
                    }
                    return "NO_CACHE";
                }

                return "NO_CACHE";
            }
        };
    }

    private Object getCacheKey(String[] strings) {
        return String.format("%s:%s", strings[0], ThreadLocalTenant.TENANT_CURRENT.get());
    }
}
