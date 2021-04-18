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

import java.util.List;

public class PersonServiceImpl implements  PersonService{
    private final Logger logger = LoggerFactory.getLogger(PersonService.class);

    @Autowired
    private PersonRepository personRepository;

    @Override
    public List<Person> findAll() throws Exception {
        logger.info("Loading persons ");
        return personRepository.findAll();
    }

    @Override
    public Person savePerson(Person person) throws PersonInvalidException,Exception {
        logger.info("Saving  person nom: '" + person.getFirstName() + "' prénom: '" + person.getLastName());
        if (person.getFirstName().isEmpty() || person.getLastName().isEmpty() || person.getEmail().isEmpty()) {
            throw new PersonInvalidException("Le champ nom/prénom/mail ne peut être vide");
        }
        return personRepository.savePerson(person);
    }

    @Override
    public void deletePerson(String lastname, String firstname) throws PersonNotFoundException, Exception {
        Person person = null;
        try {
            person = findPersonByLastNameAndFirstName(lastname, firstname);
        } catch (PersonInvalidException e) {
            logger.error(e.getMessage());
        }
        logger.info("Requête=> Suppression personne nom : "+lastname+" prénom: "+firstname);
        if(person == null){
            throw new PersonNotFoundException("La personne nom: " + person.getFirstName() + " prénom: " + person.getLastName()+" n'existe pas");
        }
        personRepository.deletePerson(person);
        logger.info("Personne  [nom:" + firstname + " prénom:" + lastname + "] supprimée avec succès");
    }

    @Override
    public Person findPersonByLastNameAndFirstName(String lastname, String firstname) throws  PersonInvalidException, PersonNotFoundException,Exception {
        Person foundPerson = null;
        if (lastname.isEmpty() || firstname.isEmpty() ) {
            throw new PersonInvalidException("Le champ nom/prénom/mail ne peut être vide");
        }
        logger.info("Requête=>Recherche personne nom: "+lastname+" prénom: "+firstname);
        foundPerson = personRepository.findPersonByLastNameAndFirstName(lastname, firstname);
        if(foundPerson == null ){
            throw new PersonNotFoundException("La personne nom : "+lastname+" prénom: "+firstname+ "n'existe pas");
        }
        logger.info("Réponse=> personne  "+foundPerson.toString());

        return foundPerson;
    }
}
