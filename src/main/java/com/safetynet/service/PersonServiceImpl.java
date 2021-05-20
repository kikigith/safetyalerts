package com.safetynet.service;

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
    public Person save(Person person)  {
        if (person.getFirstName() == null || person.getFirstName().isEmpty())
            throw new PersonInvalidException("Le champ nom ne peut être vide");
        if(person.getLastName() == null || person.getLastName().isEmpty())
            throw new PersonInvalidException("Le champ prénom ne peut être vide");
        if(person.getEmail() == null || person.getEmail().isEmpty()) {
            throw new PersonInvalidException("Le champ mail ne peut être vide");
        }
        logger.info("Saving  person nom: '" + person.getFirstName() + "' prénom: '" + person.getLastName());
        Person createdPerson = personRepository.save(person);
        logger.info("Création de la personne  prénom: '" +createdPerson.getFirstName()+ "' nom: '" +createdPerson.getLastName()+ "' effectuée avec succès");
        return createdPerson;
    }

    @Override
    public Person update(Person person) throws PersonInvalidException, PersonNotFoundException {
        if (person.getFirstName() == null || person.getFirstName().isEmpty())
            throw new PersonInvalidException("Le champ nom ne peut être vide");
        if(person.getLastName() == null || person.getLastName().isEmpty())
            throw new PersonInvalidException("Le champ prénom ne peut être vide");
        if(person.getEmail() == null || person.getEmail().isEmpty()) {
            throw new PersonInvalidException("Le champ mail ne peut être vide");
        }
        logger.info("Updating  person nom: '" + person.getFirstName() + "' prénom: '" + person.getLastName());
        Person personToUpdate = personRepository.findByLastNameAndFirstName(person.getLastName(), person.getFirstName());
        if(personToUpdate == null )
            throw new PersonNotFoundException("La personne prénom: " +person.getFirstName()+ ", nom: " +person.getLastName()+ " n'existe pas");
        personToUpdate.setEmail(person.getEmail());
        personToUpdate.setPhone(person.getPhone());
        personToUpdate.setZip(person.getZip());
        personToUpdate.setAddress(person.getAddress());
        personToUpdate.setCity(person.getCity());
        Person updatedPerson = personRepository.save(personToUpdate);
        logger.info("Mise à jour de la personne  prénom: '" +updatedPerson.getFirstName()+ "' nom: '" +updatedPerson.getLastName()+ "' effectuée avec succès");
        return updatedPerson;
    }

    @Override
    public void delete(String lastname, String firstname) {
        Person person = personRepository.findByLastNameAndFirstName(lastname, firstname);
        logger.info("Suppression personne nom : "+lastname+" prénom: "+firstname);
        //if (person == null) return ;
        personRepository.delete(person);
        logger.info("Personne  [nom:" + firstname + " prénom:" + lastname + "] supprimée avec succès");
    }

    @Override
    public Person findByLastNameAndFirstName(String lastname, String firstname) throws  PersonNotFoundException {
        logger.info("Recherche personne nom: "+lastname+" prénom: "+firstname);
        Person foundPerson = personRepository.findByLastNameAndFirstName(lastname, firstname);
        if(foundPerson == null) {
            throw new PersonNotFoundException("La personne nom : "+lastname+" prénom: "+firstname+ "n'existe pas");
        }
        logger.info("Résultat de la recherche personne : " + foundPerson);
        return foundPerson;
    }

    @Override
    public List<Person> getPersonsAtAddress(String address) {
        logger.info("Recherche des personnes résidant à l'adresse: "+address);
        List<Person> persons = personRepository.findByAddress(address);
        logger.info("Résultat de la recherche des personnes : " + persons);
        return persons;
    }

    @Override
    public List<String> getPhonesPersonsAtAddress(String address) {
        logger.info("Recherche des personnes résidant à l'adresse: "+address);
        List<String> personsPhones = personRepository.findByAddressAndSelectPhone(address);
        logger.info("Résultat des numéros des personnes : " + personsPhones);
        return personsPhones;
    }

    @Override
    public List<Person> findAllByLastnameAndFirstname(String lastname, String firstname) throws PersonNotFoundException {
        logger.info("Recherche des personnes avec nom {} et prénom {} ",lastname,firstname);
        List<Person> persons = personRepository.findAllByLastNameAndFirstName(lastname,firstname);
        logger.info("Résultat recherche des personnes : " + persons);
        return persons;
    }
}
