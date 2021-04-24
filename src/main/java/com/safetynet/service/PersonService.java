package com.safetynet.service;

import com.safetynet.exception.PersonInvalidException;
import com.safetynet.exception.PersonNotFoundException;
import com.safetynet.model.Person;

import java.util.List;

public interface PersonService {
    List<Person> findAll();
    Person save(Person person) throws PersonInvalidException,Exception;
    void delete(String lastname, String firstname) throws  PersonNotFoundException, Exception;
    Person findByLastNameAndFirstName(String lastname, String firstname) throws Exception, PersonInvalidException, PersonNotFoundException;

}
