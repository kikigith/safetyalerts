package com.safetynet.repository;

import com.safetynet.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PersonRepositoryImpl implements PersonRepository{
    @Autowired
    DataSourceComponent dataSourceComponent;

    @Override
    public List<Person> findAll() {
        return dataSourceComponent.getPersons();
    }

    @Override
    public Person save(Person person) {
        List<Person> persons = dataSourceComponent.getPersons();
        Person aPerson = findByLastNameAndFirstName(person.getLastName(), person.getFirstName());
        if (aPerson != null) {//Update
            int index = persons.indexOf(aPerson);
            persons.remove(aPerson);
            persons.add(index, person);
        } else {// New person then, add the person
            persons.add(person);
        }
        return person;
    }

    @Override
    public void delete(Person person) {
        List<Person> persons = dataSourceComponent.getPersons();
        persons.remove(person);
    }

    @Override
    public Person findByLastNameAndFirstName(String lastname, String firstname) {
        for (Person person : dataSourceComponent.getPersons()) {
            if (person.getLastName().equalsIgnoreCase(lastname) && person.getFirstName().equalsIgnoreCase(firstname))
                return person;
        }
        return null;
    }

    @Override
    public List<Person> findByAddress(String address){
        List<Person> persons = new ArrayList<>();
        dataSourceComponent.getPersons().forEach(p->{
            if(p.getAddress().equalsIgnoreCase(address)) {
                persons.add(p);
            }
        });
        return persons;
    }

    public List<String> findByAddressAndSelectPhone(String address){
        List<String> phones = new ArrayList<>();
        dataSourceComponent.getPersons().forEach(p->{
            if(p.getAddress().equalsIgnoreCase(address)) {
                phones.add(p.getPhone());
            }
        });
        return phones;
    }

    @Override
    public List<Person> findAllByLastNameAndFirstName(String lastname, String firstname) {
        List<Person> persons = new ArrayList<>();
        dataSourceComponent.getPersons().forEach(person -> {
            if(person.getLastName().equalsIgnoreCase(lastname) &&
                    person.getFirstName().equalsIgnoreCase(firstname)){
                persons.add(person);
            }
        });
        return persons;
    }
}
