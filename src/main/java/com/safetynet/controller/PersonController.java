package com.safetynet.controller;

import com.safetynet.exception.PersonInvalidException;
import com.safetynet.exception.PersonNotFoundException;
import com.safetynet.model.Person;
import com.safetynet.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class PersonController {

    private final Logger logger = LoggerFactory.getLogger(PersonController.class);
    @Autowired
    PersonService personService;

    /**
     * Read - Get all persons from the repository
     *
     * @return an iterable list of persons
     */
    @GetMapping("/persons")
    public List<Person> getPersons(){
        return personService.findAll();
    }

    /**
     * Create - Create a new person in the repository
     *
     * @return - a new instance of person
     */
    @PostMapping("/person")
    public ResponseEntity<Person> savePerson(@RequestParam("lastname") final String lastname,
                                               @RequestParam("firstname") final String firstname,
                                               @RequestBody Person person) throws PersonInvalidException, Exception{

        logger.info("Request = @RequestBody = {}", person.toString());
        Person persistedPerson = null;
        persistedPerson = personService.save(person);

        return ResponseEntity
                .created(URI.create(String.format("/person?lastname=" + lastname + "&firstname=" + firstname)))
                .body(persistedPerson);
    }


    /**
     * Search - Search a person in the repository by firstName & lastName
     *
     * @return - an instance of person if found
     */
    @GetMapping("/person")
    public ResponseEntity<Person> searchPerson(@RequestParam("lastname") final String lastname,
                                               @RequestParam("firstname") final String firstname)
                                                throws PersonInvalidException, PersonNotFoundException,Exception {

        Person foundPerson = null;
        foundPerson = personService.findByLastNameAndFirstName(lastname, firstname);

        return new ResponseEntity<Person>(foundPerson, HttpStatus.OK);
    }

    /**
     * UpdatePerson - Update an existing person in the repository
     *
     * @return - an instance of ResponseEntity<Person>
     */
    @PutMapping("/person")
    public ResponseEntity<Person> updatePerson(@RequestParam("lastname") final String lastname,
                                               @RequestParam("firstname") final String firstname,
                                               @RequestBody Person person) throws PersonNotFoundException, PersonInvalidException,Exception {
        logger.info("Request = @RequestBody = {}", person.toString());
        ResponseEntity<Person> response = null;
        Person pers = personService.findByLastNameAndFirstName(lastname, firstname);
        logger.info("Response = @ResponseBody = {}", pers.toString());
        personService.save(pers);
        response = ResponseEntity.ok().body(pers);

        return response;
    }

    /**
     * Delete - Delete a person from the repository
     *
     * @param lastname  - the lastname of the person to be deleted
     * @param firstname - the firstname of the person to be deleted
     *
     *
     */
    @DeleteMapping("/person")
    public ResponseEntity<Person> deletePerson(@RequestParam("lastname") final String lastname,
                                               @RequestParam("firstname") final String firstname)
            throws PersonInvalidException, PersonNotFoundException, Exception {
        ResponseEntity<Person> response = null;
        logger.info("Request Delete person firstname {}, lastname{}", firstname, lastname);
        personService.delete(lastname, firstname);
        response = new ResponseEntity<Person>(HttpStatus.ACCEPTED);

        return response;
    }


}
