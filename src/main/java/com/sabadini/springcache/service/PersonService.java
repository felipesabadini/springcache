package com.sabadini.springcache.service;

import com.sabadini.springcache.configuration.CacheConfiguration;
import com.sabadini.springcache.configuration.ThreadLocalTenant;
import com.sabadini.springcache.domain.Person;
import com.sabadini.springcache.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PersonService {

    private final PersonRepository repository;
    private final Logger log = LoggerFactory.getLogger(PersonService.class);

    @Autowired
    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

//    @CacheEvict(cacheNames = CacheConfiguration.CACHE_PERSON)
    public Person create(Person person) {
        return this.repository.save(person);
    }

    @Cacheable(cacheNames = CacheConfiguration.CACHE_PERSON)
    public List<Person> getAll() {
        final String tenant = ThreadLocalTenant.TENANT_CURRENT.get();
        log.info(String.format("Seeking people with the tenant -> %s", tenant));
        return this.repository.findPersonByTenantEquals(tenant);
    }
}
