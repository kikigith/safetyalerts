package com.safetynet.repository;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.safetynet.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PersonRepositoryImpl implements PersonRepository{
    @Autowired
    DataSourceComponent dataSourceComponent;

    @Override
    public List<Person> findAll() throws Exception {
        return dataSourceComponent.getPersons();
    }

    @Override
    public Person savePerson(Person person) throws Exception {
        Person aPerson = null;
        List<Person> persons = findAll();
        if (persons.contains(person)) {// Update
            persons.get(persons.indexOf(person)).setFirstName(person.getFirstName());
            persons.get(persons.indexOf(person)).setLastName(person.getLastName());
            persons.get(persons.indexOf(person)).setAddress(person.getAddress());
            persons.get(persons.indexOf(person)).setEmail(person.getEmail());
            persons.get(persons.indexOf(person)).setPhone(person.getPhone());
            persons.get(persons.indexOf(person)).setCity(person.getCity());
            persons.get(persons.indexOf(person)).setZip(person.getZip());
            aPerson = persons.get(persons.indexOf(person));
        } else {// New person extend the list
            aPerson = new Person(person.getFirstName(), person.getLastName(), person.getAddress(), person.getCity(),
                    person.getZip(), person.getPhone(), person.getEmail());
            persons.add(person);
        }
        dataSourceComponent.getData().setPersons(persons);
        dataSourceComponent.serializeDataToFile(dataSourceComponent.getData());
        return aPerson;
    }

    @Override
    public void deletePerson(Person person) throws Exception {
        List<Person> persons = findAll();
        if(persons.contains(person)){
            persons.remove(person);
        }
        dataSourceComponent.getData().setPersons(persons);
        dataSourceComponent.serializeDataToFile(dataSourceComponent.getData());
    }

    @Override
    public Person findPersonByLastNameAndFirstName(String lastname, String firstname) throws JsonMappingException, Exception {
        Person foundPerson = new Person();
        List<Person> persons = findAll();
        for (Person person : persons) {
            if (person.getLastName().equalsIgnoreCase(lastname) && person.getFirstName().equalsIgnoreCase(firstname))
                foundPerson = person;
        }
        return foundPerson;

    }
}
