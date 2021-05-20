package com.safetynet.repository;

import com.safetynet.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonRepositoryTest {

    @Mock
    DataSourceComponent dataSourceComponent;
    @InjectMocks
    PersonRepositoryImpl personRepository;
    protected Person person, person1, person2;
    private List<Person> persons;

    @BeforeEach
    public void initTests() {
        person = new Person();
        person.setFirstName("mike");
        person.setLastName("fiver");
        person.setAddress("Rue 54 Parakou");
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

    }

    @Test
    public void findAll_should_return_all_persons() {
        when(dataSourceComponent.getPersons()).thenReturn(persons);
        List<Person> allPersons = personRepository.findAll();
        assertThat(allPersons).hasSize(3);
    }

   @Test
    public void given_a_new_person_save_should_persist_the_person() {
       Person newPerson = new Person();
       newPerson.setFirstName("Bill");
       newPerson.setLastName("Bill");
       newPerson.setAddress("Rue 70 Malanville");
       newPerson.setPhone("098 3584 88");
       newPerson.setCity("Malanville");
       newPerson.setZip("0000");
       newPerson.setEmail("bfill@gmail.com");
       when(dataSourceComponent.getPersons()).thenReturn(persons);
       Person savedPerson = personRepository.save(newPerson);
       assertThat(persons).hasSize(4);
       assertThat(savedPerson.getEmail()).isSameAs(newPerson.getEmail());

   }

   @Test
    public void given_an_existing_person_save_should_update_the_person() {
        Person newPerson = new Person();
        newPerson.setFirstName("Bill");
        newPerson.setLastName("Bauer");
        newPerson.setAddress("Rue 70 Malanville");
        newPerson.setPhone("098 3584 88");
        newPerson.setCity("Malanville");
        newPerson.setZip("0000");
        newPerson.setEmail("bfill@gmail.com");
        when(dataSourceComponent.getPersons()).thenReturn(persons);
        Person updatedPerson = personRepository.save(newPerson);
        assertThat(persons).hasSize(3);
        assertThat(updatedPerson.getLastName()).isSameAs(newPerson.getLastName());
        //todo : verifier les information qui ont mise à jour
       assertThat(updatedPerson.getAddress()).isNotEqualTo("Rue 34, Porto");
       assertThat(updatedPerson.getAddress()).isEqualTo("Rue 70 Malanville");
       assertThat(updatedPerson.getPhone()).isNotEqualTo(person1.getPhone());
       assertThat(updatedPerson.getPhone()).isEqualTo("098 3584 88");
    }

    @Test
    public void given_a_person_should_be_deleted_from_source() {
        when(dataSourceComponent.getPersons()).thenReturn(persons);
        assertThat(persons).hasSize(3);
        personRepository.delete(person);
        assertThat(persons).hasSize(2);
    }

    @Test
    public void given_lastname_firstname_should_return_a_person() {
        when(dataSourceComponent.getPersons()).thenReturn(persons);
        Person foundPerson = personRepository.findByLastNameAndFirstName("fiver","mike");
        assertThat(foundPerson.getLastName()).isSameAs(person2.getLastName());
        assertThat(foundPerson.getFirstName()).isSameAs(person2.getFirstName());
        assertThat(foundPerson.getEmail()).isSameAs(person2.getEmail());
        assertThat(foundPerson.getEmail()).isSameAs(person2.getEmail());
    }

    //todo: given_a_non_existing_firstname_lastname_should_return_null
    @Test
    public void given_a_non_existing_lastname_firstname_should_return_null() {
        when(dataSourceComponent.getPersons()).thenReturn(persons);
        Person foundPerson = personRepository.findByLastNameAndFirstName("marc","oliver");
        assertThat(foundPerson).isNull();
    }

    /**
     * given_an_address_should_return_resident_person - test getPersonsAtAddress
     */
    @Test
    public void given_an_address_should_return_resident_persons_at_the_address(){
        when(dataSourceComponent.getPersons()).thenReturn(persons);
        List<Person> personsAtAddress = personRepository.findByAddress("Rue 54 parakou");
        assertThat(personsAtAddress).hasSize(2);
        assertThat(personsAtAddress).contains(person).contains(person2);
        assertThat(personsAtAddress).doesNotContain(person1);
    }

    /**
     * given_an_address_should_return_resident_phone_numbers - test getPhonesPersonAtAddress
     */
    @Test
    public void given_an_address_should_return_resident_phone_numbers(){
        when(dataSourceComponent.getPersons()).thenReturn(persons);
        List<String> personsPhones = personRepository.findByAddressAndSelectPhone("Rue 54 parakou");
        assertThat(personsPhones).hasSize(2);
        assertThat(personsPhones).contains(person.getPhone()).contains(person2.getPhone());
        assertThat(personsPhones).doesNotContain(person1.getPhone());
    }
}
