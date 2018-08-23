package com.sabadini.springcache.configuration;

import javafx.beans.property.adapter.JavaBeanObjectProperty;

public class ThreadLocalTenant {

    public static final ThreadLocal<String> TENANT_CURRENT = new ThreadLocal<>();
    public static final ThreadLocal<String> USER_CURRENT = new ThreadLocal<>();
}
