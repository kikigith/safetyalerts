package com.safetynet.repository;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.safetynet.model.Person;

import java.util.List;

public interface PersonRepository {
    public List<Person> findAll() throws Exception;
    public Person savePerson(Person person) throws Exception;
    public void deletePerson(Person person)
            throws Exception;
    public Person findPersonByLastNameAndFirstName(String lastname, String firstname) throws JsonMappingException, Exception;

}
