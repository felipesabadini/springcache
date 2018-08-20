package com.sabadini.springcache.domain;

import java.io.Serializable;

public interface BaseEntity extends Serializable {

    String getId();
    String getTenant();
}
