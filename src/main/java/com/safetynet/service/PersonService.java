package com.safetynet.service;

import com.safetynet.exception.PersonInvalidException;
import com.safetynet.exception.PersonNotFoundException;
import com.safetynet.model.Person;

import java.util.List;

public interface PersonService {
    List<Person> findAll();
    Person save(Person person) throws PersonInvalidException;
    Person update(Person person) throws PersonInvalidException, PersonNotFoundException;
    void delete(String lastname, String firstname);
    Person findByLastNameAndFirstName(String lastname, String firstname) throws PersonNotFoundException;
    List<Person> getPersonsAtAddress(String address);
    List<String> getPhonesPersonsAtAddress(String address);
}
