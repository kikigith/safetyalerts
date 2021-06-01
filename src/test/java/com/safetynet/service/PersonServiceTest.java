package com.safetynet.service;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyString;
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

    private Person person, person1, person2;
    private Person invalidFirstnamePerson;
    private Person invalidLastnamePerson;
    private Person invalidEmailPerson;
    private List<Person> persons, personsAtRue54;

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
        person2.setPhone("039 2484 76");
        person2.setCity("Parakou");
        person2.setZip("0000");
        person2.setEmail("fmike@gmail.com");

        persons = new ArrayList<>();
        persons.add(person);
        persons.add(person1);
        persons.add(person2);

        personsAtRue54 = new ArrayList<>();
        personsAtRue54.add(person);
        personsAtRue54.add(person2);

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
     *
     */
    @Test
    public void given_a_valid_person_should_save_the_person() {
        when(personRepository.save(any(Person.class))).thenReturn(person);
        Person savedPerson = personService.save(person);
        verify(personRepository).save(any(Person.class));
        assertThat(savedPerson).isNotNull();
    }

    /**
     * given_invalid_lastname_save_should_raise_exception - test methode savePerson: cas exception
     */
    @Test
    public void given_invalid_lastname_save_should_raise_exception() {
        Assertions.assertThrows(PersonInvalidException.class, () -> personService.save(invalidLastnamePerson));
    }

    /**
     * give_invalid_firstname_save_should_raise_exception - test methode savePerson: cas exception
     *
     */
    @Test
    public void give_invalid_firstname_save_should_raise_exception()  {
        Assertions.assertThrows(PersonInvalidException.class, () ->personService.save(invalidFirstnamePerson));
    }

    /**
     * give_invalid_email_save_should_raise_exception - test methode savePerson: cas exception
     *
     */
    @Test
    public void give_invalid_email_save_should_raise_exception()  {
        Assertions.assertThrows(PersonInvalidException.class, () -> personService.save(invalidEmailPerson));
    }

    /**
     * given_invalid_lastname_update_should_raise_exception - test methode savePerson: cas exception
     *
     */
    @Test
    public void given_invalid_lastname_update_should_raise_exception() {
        Assertions.assertThrows(PersonInvalidException.class, () -> personService.update(invalidLastnamePerson));
    }

    /**
     * give_invalid_firstname_update_should_raise_exception - test methode savePerson: cas exception
     *
     */
    @Test
    public void give_invalid_firstname_update_should_raise_exception()  {
        Assertions.assertThrows(PersonInvalidException.class, () ->personService.update(invalidFirstnamePerson));
    }

    /**
     * give_invalid_email_update_should_raise_exception - test methode savePerson: cas exception
     *
     */
    @Test
    public void give_invalid_email_update_should_raise_exception()  {
        Assertions.assertThrows(PersonInvalidException.class, () -> personService.update(invalidEmailPerson));
    }

    /**
     * given_a_non_existing_person_update_should_raise_exception - test update : exception PersonNotFoundException
     *
     */
    @Test
    public void given_a_non_existing_person_update_should_raise_exception() {
        when(personRepository.findByLastNameAndFirstName("fiver","mike")).thenReturn(null);
        Assertions.assertThrows(PersonNotFoundException.class, () -> personService.update(person));
    }

    @Test
    public void given_a_valid_person_update_should_persist_the_person() {
        Person newPerson = new Person();
        newPerson.setFirstName("Bill");
        newPerson.setLastName("Bauer");
        newPerson.setAddress("Rue 70 Malanville");
        newPerson.setPhone("098 3584 88");
        newPerson.setCity("Malanville");
        newPerson.setZip("0000");
        newPerson.setEmail("bfill@gmail.com");
        when(personRepository.findByLastNameAndFirstName(any(),any())).thenReturn(person);
        when(personRepository.save(any())).thenAnswer(a -> a.getArguments()[0]);

        Person updatedPerson = personService.update(newPerson);
        verify(personRepository).save(any(Person.class));
        assertThat(updatedPerson).isNotNull();
        assertThat(updatedPerson.getAddress()).isSameAs(newPerson.getAddress());
        assertThat(updatedPerson.getCity()).isSameAs("Malanville");
        assertThat(updatedPerson.getEmail()).isSameAs("bfill@gmail.com");
    }

    /**
     * find_should_return_all_persons - Test findAll
     *
     */
    @Test
    public void find_should_return_all_persons() {
        when(personRepository.findAll()).thenReturn(persons);
        List<Person> allPersons = personService.findAll();
        verify(personRepository).findAll();
        assertThat(allPersons).hasSize(3);
    }

    @Test
    public void given_lastname_and_firstname_person_should_be_deleted() {
        when(personRepository.findByLastNameAndFirstName(any(),any())).thenReturn(person);
        personService.delete(person.getLastName(), person.getLastName());
        verify(personRepository, times(1)).delete(any());
    }

    /**
     * given_lastname_and_firstname_should_return_person - Test findPersonByLastnameAndFirstname :cas nominal
     *
     */
    @Test
    public void given_lastname_and_firstname_should_return_person() {
        when(personRepository.findByLastNameAndFirstName("fiver", "mike")).thenReturn(person);
        Person foundPerson = personService.findByLastNameAndFirstName("fiver","mike");
        assertThat(foundPerson.getLastName()).isSameAs(person.getLastName());
    }

    /**
     * given_invalid_lastname_findbylastnameandfirstname_should_raise_PersonNotFoundException - Test findPersonByLastnameAndFirstname :cas exception PersonInvalidException
     *
     */
    @Test
    public void given_invalid_lastname_findbylastnameandfirstname_should_raise_PersonNotFoundException() {
        Assertions.assertThrows(PersonNotFoundException.class, () -> personService.findByLastNameAndFirstName("", "Cool"));
    }

    /**
     * given_invalid_firstname_findbylastnameandfirstname_should_raise_PersonInvalidException - Test findPersonByLastnameAndFirstname :cas exception PersonInvalidException
     *
     */
    @Test
    public void given_invalid_firstname_findbylastnameandfirstname_should_raise_PersonNotFoundException() {
        Assertions.assertThrows(PersonNotFoundException.class, () -> personService.findByLastNameAndFirstName("Mike", ""));
    }

    /**
     * given_lastname_and_firstname_should_return_person - Test findPersonByLastnameAndFirstname :cas exception PersonNotFoundException
     *
     */
    @Test
    public void given_invalid_name_should_raise_PersonNotFoundException() {
        when(personRepository.findByLastNameAndFirstName("Mike", "Cool")).thenReturn(null);
        Assertions.assertThrows(PersonNotFoundException.class, () -> personService.findByLastNameAndFirstName("Mike", "Cool"));
    }

    @Test
    public void given_an_address_should_return_persons_at_the_address() {
        when(personRepository.findByAddress("Rue 54 parakou")).thenReturn(personsAtRue54);
        List<Person> personsAtParakou = personService.getPersonsAtAddress("Rue 54 parakou");
        assertThat(personsAtParakou).hasSize(2);
        assertThat(personsAtParakou).contains(person);
        assertThat(personsAtParakou).contains(person2);
    }

    @Test
    public void given_an_address_should_return_phones_of_persons_at_the_address(){
        List<String> phoneAtAddress = new ArrayList<>();
        phoneAtAddress.add("928 8484 88");
        phoneAtAddress.add("039 2484 76");
        when(personRepository.findByAddressAndSelectPhone("Rue 54 parakou")).thenReturn(phoneAtAddress);
        List<String> personsPhones = personService.getPhonesPersonsAtAddress("Rue 54 parakou");
        assertThat(personsPhones).hasSize(2);
        assertThat(personsPhones).contains(person.getPhone());
        assertThat(personsPhones).contains(person2.getPhone());
    }

    @Test
    public void given_a_lastname_and_firstname_should_return_persons() {
        when(personRepository.findAllByLastNameAndFirstName(anyString(),anyString())).thenReturn(persons);
        List<Person> allPersons = personService.findAllByLastnameAndFirstname("mike","fiver");
        assertThat(allPersons).contains(person);
    }
}
