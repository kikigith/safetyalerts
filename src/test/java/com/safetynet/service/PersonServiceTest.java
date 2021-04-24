package com.safetynet.service;
import static org.mockito.Mockito.times;
import com.safetynet.exception.PersonInvalidException;
import com.safetynet.exception.PersonNotFoundException;
import com.safetynet.model.Person;
import com.safetynet.repository.PersonRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    private PersonRepositoryImpl personRepository;

    @InjectMocks
    private PersonServiceImpl personService;

    private Person person, invalidFirstnamePerson, invalidLastnamePerson, invalidEmailPerson, person1, person2;
    private List<Person> persons;

    @BeforeEach
    void initTest() {

        person = new Person();
        person.setFirstName("mike");
        person.setLastName("fiver");
        person.setAddress("Rue 54 parakou");
        person.setPhone("928 8484 88");
        person.setCity("Parakou");
        person.setZip("0000");
        person.setEmail("fmike@gmail.com");

        person1 = new Person();
        person1.setFirstName("Bill");
        person1.setLastName("Bauer");
        person1.setAddress("Rue 34, Porto");
        person1.setPhone("102 9848 89");
        person1.setCity("Porto");
        person1.setZip("0000");
        person1.setEmail("bbauer@gmail.com");

        person2 = new Person();
        person2.setFirstName("mike");
        person2.setLastName("fiver");
        person2.setAddress("Rue 54 parakou");
        person2.setPhone("928 8484 88");
        person2.setCity("Parakou");
        person2.setZip("0000");
        person2.setEmail("fmike@gmail.com");

        persons = new ArrayList<>();
        persons.add(person);
        persons.add(person1);
        persons.add(person2);

        invalidFirstnamePerson = new Person();
        invalidFirstnamePerson.setFirstName("");
        invalidFirstnamePerson.setLastName("Karl");
        invalidFirstnamePerson.setAddress("78 rue viera");
        invalidFirstnamePerson.setPhone("928 48482");
        invalidFirstnamePerson.setCity("Allada");
        invalidFirstnamePerson.setZip("0000");
        invalidFirstnamePerson.setEmail("jkarl@facebook.com");

        invalidLastnamePerson = new Person();
        invalidLastnamePerson.setLastName("");
        invalidLastnamePerson.setFirstName("Karl");
        invalidLastnamePerson.setAddress("78 rue viera");
        invalidLastnamePerson.setPhone("928 48482");
        invalidLastnamePerson.setCity("Allada");
        invalidLastnamePerson.setZip("0000");


        invalidEmailPerson = new Person();
        invalidEmailPerson.setLastName("Frantz");
        invalidEmailPerson.setFirstName("Karl");
        invalidEmailPerson.setAddress("78 rue viera");
        invalidEmailPerson.setEmail("");
        invalidEmailPerson.setPhone("928 48482");
        invalidEmailPerson.setCity("Allada");
        invalidEmailPerson.setZip("0000");



    }


    /**
     * given_a_valid_person_should_save_the_person - test methode savePerson: cas norminal
     * @throws Exception
     * @throws PersonInvalidException
     */
    @Test
    public void given_a_valid_person_should_save_the_person() throws Exception, PersonInvalidException {
        when(personRepository.save(any(Person.class))).thenReturn(person);
        Person savedPerson = personService.save(person);
        verify(personRepository).save(any(Person.class));
        assertThat(savedPerson).isNotNull();

    }

    /**
     * given_a_valid_person_should_save_the_person - test methode savePerson: cas exception
     * @throws Exception
     */
    @Test
    public void given_invalid_lastname_save_should_raise_exception() throws Exception {
        Assertions.assertThrows(PersonInvalidException.class, () -> {
            personService.save(invalidLastnamePerson);
        });
    }

    @Test
    public void give_invalid_firstname_save_should_raise_exception() throws Exception {
        Assertions.assertThrows(PersonInvalidException.class, () -> {
            personService.save(invalidFirstnamePerson);
        });
    }

    @Test
    public void give_invalid_lastname_save_should_raise_exception() throws Exception {
        Assertions.assertThrows(PersonInvalidException.class, () -> {
            personService.save(invalidLastnamePerson);
        });
    }

    /**
     * find_should_return_all_persons - Test findAll
     * @throws Exception
     */
    @Test
    public void find_should_return_all_persons() throws Exception {
        when(personRepository.findAll()).thenReturn(persons);
        List<Person> allPersons = personService.findAll();
        verify(personRepository).findAll();
        assertThat(allPersons).hasSize(3);
    }

    @Test
    public void given_lastname_and_firstname_person_should_be_deleted() throws Exception, PersonNotFoundException {
        when(personRepository.findByLastNameAndFirstName(any(),any())).thenReturn(person);
        personService.delete(person.getLastName(), person.getLastName());
        verify(personRepository, times(1)).delete(any());
    }

   /* @Test
    public void given_non_existing_person_delete_should_raise_PersonNotFoundException() throws Exception{
        when(personRepository.findByLastNameAndFirstName("Mike", "Cool")).thenReturn(null);
        Assertions.assertThrows(PersonNotFoundException.class, () -> {
            personService.delete("Mike", "Cool");
        });
    }*/

    /**
     * given_lastname_and_firstname_should_return_person - Test findPersonByLastnameAndFirstname :cas nominal
     * @throws Exception
     */
    @Test
    public void given_lastname_and_firstname_should_return_person() throws Exception, PersonInvalidException, PersonNotFoundException {
        when(personRepository.findByLastNameAndFirstName("fiver", "mike")).thenReturn(person);
        Person foundPerson = personService.findByLastNameAndFirstName("fiver","mike");
        assertThat(foundPerson.getLastName()).isSameAs(person.getLastName());
    }

    /**
     * given_invalid_lastname_findbylastnameandfirstname_should_raise_PersonNotFoundException - Test findPersonByLastnameAndFirstname :cas exception PersonInvalidException
     * @throws Exception
     */
    @Test
    public void given_invalid_lastname_findbylastnameandfirstname_should_raise_PersonNotFoundException() throws Exception{
        Assertions.assertThrows(PersonNotFoundException.class, () -> {
            personService.findByLastNameAndFirstName("", "Cool");
        });
    }

    /**
     * given_invalid_firstname_findbylastnameandfirstname_should_raise_PersonInvalidException - Test findPersonByLastnameAndFirstname :cas exception PersonInvalidException
     * @throws Exception
     */
    @Test
    public void given_invalid_firstname_findbylastnameandfirstname_should_raise_PersonNotFoundException() throws Exception{
        Assertions.assertThrows(PersonNotFoundException.class, () -> {
            personService.findByLastNameAndFirstName("Mike", "");
        });
    }

    /**
     * given_lastname_and_firstname_should_return_person - Test findPersonByLastnameAndFirstname :cas exception PersonNotFoundException
     * @throws Exception
     */
    @Test
    public void given_invalid_name_should_raise_PersonNotFoundException() throws Exception{
        when(personRepository.findByLastNameAndFirstName("Mike", "Cool")).thenReturn(null);
        Assertions.assertThrows(PersonNotFoundException.class, () -> {
            personService.findByLastNameAndFirstName("Mike", "Cool");
        });
    }

}
