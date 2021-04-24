package com.safetynet.service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.safetynet.exception.PersonInvalidException;
import com.safetynet.exception.PersonNotFoundException;
import com.safetynet.model.Person;
import com.safetynet.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl implements  PersonService{
    private final Logger logger = LoggerFactory.getLogger(PersonService.class);

    @Autowired
    private PersonRepository personRepository;

    @Override
    public List<Person> findAll()  {
        logger.info("Loading persons ");
        return personRepository.findAll();
    }

    @Override
    public Person save(Person person) throws PersonInvalidException {
        logger.info("Saving  person nom: '" + person.getFirstName() + "' prénom: '" + person.getLastName());
        if (person.getFirstName() == null || person.getFirstName().isEmpty())
            throw new PersonInvalidException("Le champ nom ne peut être vide");
        if(person.getLastName() == null || person.getLastName().isEmpty())
            throw new PersonInvalidException("Le champ prénom ne peut être vide");
        if(person.getEmail() == null || person.getEmail().isEmpty()) {
            throw new PersonInvalidException("Le champ mail ne peut être vide");
        }
        return personRepository.save(person);
    }

    @Override
    public void delete(String lastname, String firstname) throws PersonNotFoundException {
        Person person = personRepository.findByLastNameAndFirstName(lastname, firstname);
        logger.info("Requête=> Suppression personne nom : "+lastname+" prénom: "+firstname);
        if (person == null) return;
        personRepository.delete(person);
        logger.info("Personne  [nom:" + firstname + " prénom:" + lastname + "] supprimée avec succès");
    }

    @Override
    public Person findByLastNameAndFirstName(String lastname, String firstname) throws  PersonNotFoundException {
        logger.info("Requête=>Recherche personne nom: "+lastname+" prénom: "+firstname);
        Person foundPerson = personRepository.findByLastNameAndFirstName(lastname, firstname);
        if(foundPerson == null) {
            throw new PersonNotFoundException("La personne nom : "+lastname+" prénom: "+firstname+ "n'existe pas");
        }
        logger.info("Réponse=> personne  "+foundPerson.toString());
        return foundPerson;
    }
}
