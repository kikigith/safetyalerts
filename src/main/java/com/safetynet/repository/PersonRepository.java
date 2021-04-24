package com.safetynet.repository;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.safetynet.model.Person;

import java.util.List;

public interface PersonRepository {
    List<Person> findAll();
    Person save(Person person);
    void delete(Person person);
    Person findByLastNameAndFirstName(String lastname, String firstname);
}
