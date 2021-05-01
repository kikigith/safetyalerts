package com.safetynet.controller;

import com.safetynet.model.Person;
import com.safetynet.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ResponseEntity<List<Person>> getPersons(){
        List<Person> persons = personService.findAll();
        return new ResponseEntity<>(persons, HttpStatus.OK);
    }

    /**
     * Create - Create a new person in the repository
     *
     * @return - a new instance of person
     */
    @PostMapping("/person")
    public ResponseEntity<Person> savePerson(@Valid @RequestBody Person person) {
        logger.info("Request = @RequestBody = {}", person);
        if(person.getLastName().isEmpty() || person.getLastName().isBlank() ||
           person.getFirstName().isEmpty() || person.getFirstName().isBlank() ||
           person.getEmail().isEmpty() || person.getEmail().isBlank())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Person persistedPerson = personService.save(person);

        logger.error("Réponse = @ResponseBody = {} ", persistedPerson);
        return ResponseEntity
                .created(URI.create(String.format("/person?lastname=" + person.getLastName() + "&firstname=" + person.getFirstName())))
                //.created(URI.create(String.format("/person")))
                .body(persistedPerson);
    }

    /**
     * UpdatePerson - Update an existing person in the repository
     *
     * @return - an instance of ResponseEntity<Person>
     */
    @PutMapping("/person")
    public ResponseEntity<Person> updatePerson(@Valid @RequestBody Person person){
        logger.info("Request = @RequestBody = {}", person);
        if(person.getLastName().isEmpty() || person.getLastName().isBlank() ||
                person.getFirstName().isEmpty() || person.getFirstName().isBlank() ||
                person.getEmail().isEmpty() || person.getEmail().isBlank())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Person updatedPerson = personService.update(person);
        if(updatedPerson == null )
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        logger.error("Réponse = @ResponseBody = {} ",updatedPerson);
        return ResponseEntity.accepted().body(updatedPerson);
    }

    /**
     * Search - Search a person in the repository by firstName & lastName
     *
     * @return - an instance of person if found
     */
    @GetMapping("/person")
    public ResponseEntity<Person> searchPerson(@RequestParam("lastname") final String lastname,
                                               @RequestParam("firstname") final String firstname) {
        logger.info("Request = recherche person avec prénom : " + lastname+ ", nom: "+ firstname);
        if(lastname.isEmpty() || lastname.isBlank() ||
                firstname.isEmpty() || firstname.isBlank() )
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Person foundPerson = personService.findByLastNameAndFirstName(lastname, firstname);
        if(foundPerson == null )
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        logger.info("Réponse personne " +foundPerson);
        return new ResponseEntity<>(foundPerson, HttpStatus.OK);
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
                                               @RequestParam("firstname") final String firstname) {
        logger.info("Request Delete person firstname {}, lastname{}", firstname, lastname);
        personService.delete(lastname, firstname);
        return  new ResponseEntity<>(HttpStatus.ACCEPTED);
    }




}
