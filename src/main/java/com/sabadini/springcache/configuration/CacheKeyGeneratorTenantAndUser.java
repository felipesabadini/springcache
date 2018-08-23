package com.sabadini.springcache.configuration;

import com.sabadini.springcache.annotations.CacheableTenantAndUser;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

@Configuration
public class CacheKeyGeneratorTenantAndUser implements KeyGenerator {

    @Override
    public Object generate(Object target, Method method, Object... params) {
        final CacheableTenantAndUser cacheableTenantAndUser = method.getAnnotation(CacheableTenantAndUser.class);
        if(cacheableTenantAndUser != null) {
            if(cacheableTenantAndUser.cacheNames().length > 0) {
                return getCacheKey(cacheableTenantAndUser.cacheNames());
            }
            return "NO_CACHE";
        }

        return "NO_CACHE";
    }

    private Object getCacheKey(String[] strings) {
        return String.format("%s:%s:%s", strings[0], ThreadLocalTenant.TENANT_CURRENT.get(), ThreadLocalTenant.USER_CURRENT.get());
    }
}
