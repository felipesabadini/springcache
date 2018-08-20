package com.sabadini.springcache.configuration;

public class ThreadLocalTenant {

    public static final ThreadLocal<String> TENANT_CURRENT = new ThreadLocal<>();
}
