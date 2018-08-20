package com.sabadini.springcache.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Person implements BaseEntity {

    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "tenant")
    private String tenant;
    @Column(name = "name")
    private String name;

    protected Person() { }

    public Person(String tenant, String name) {
        this.id = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
        this.tenant = tenant;
        this.name = name;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getTenant() {
        return tenant;
    }

    public String getName() {
        return name;
    }
}

