package com.sabadini.springcache.repository;

import com.sabadini.springcache.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, String> {

    List<Person> findPersonByTenantEquals(String tenant);
}
