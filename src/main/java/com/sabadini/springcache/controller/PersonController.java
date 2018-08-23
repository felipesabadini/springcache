package com.sabadini.springcache.controller;

import com.sabadini.springcache.configuration.ThreadLocalTenant;
import com.sabadini.springcache.domain.Person;
import com.sabadini.springcache.dto.person.CreatePersonDto;
import com.sabadini.springcache.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/people")
public class PersonController {

    private PersonService service;

    @Autowired
    public PersonController(PersonService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreatePersonDto dto) {
        ThreadLocalTenant.TENANT_CURRENT.set(dto.tenant);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.create(new Person(dto.tenant, dto.name)));
    }

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(name = "tenant", defaultValue = "NO_TENANT") String tenant) {
        ThreadLocalTenant.TENANT_CURRENT.set(tenant);
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/v2")
    public ResponseEntity<?> getAll2(@RequestParam(name = "tenant", defaultValue = "NO_TENANT") String tenant,
                                     @RequestParam(name = "user", defaultValue = "NO_USER") String user) {
        ThreadLocalTenant.TENANT_CURRENT.set(tenant);
        ThreadLocalTenant.USER_CURRENT.set(user);
        return ResponseEntity.ok(service.getAll2());
    }
}
