package com.safetynet.service;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.safetynet.exception.PersonInvalidException;
import com.safetynet.exception.PersonNotFoundException;
import com.safetynet.model.Person;

import java.util.List;

public interface PersonService {
    public List<Person> findAll() throws  PersonNotFoundException, Exception;
    public Person savePerson(Person person) throws PersonInvalidException,Exception;
    public void deletePerson(String lastname, String firstname)
            throws  PersonNotFoundException, Exception;
    public Person findPersonByLastNameAndFirstName(String lastname, String firstname) throws Exception, PersonInvalidException, PersonNotFoundException;

}
