package com.safetynet.controller.unit;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.safetynet.controller.PersonController;
import com.safetynet.exception.ControllerExceptionHandler;
import com.safetynet.exception.PersonInvalidException;
import com.safetynet.exception.PersonNotFoundException;
import com.safetynet.model.Person;
import com.safetynet.repository.PersonRepositoryImpl;
import com.safetynet.service.PersonService;
import org.junit.jupiter.api.*;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(controllers = PersonController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersonControllerTest extends AbstractControllerTest{
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    //@InjectMocks
    //PersonController personController;

    private List<String> emails;
    private String city;
    private Person person,invalidLastnamePerson, invalidFirstnamePerson, invalidEmailPerson,nonExistingPerson;

    @BeforeEach
    void initTest(){
        MockitoAnnotations.initMocks(this);
        city="Parakou";
        emails=new ArrayList<>();
        emails.add("vidossou@gmail.com");
        emails.add("bkarl@outlook.com");

        person = new Person();
        person.setFirstName("LORD");
        person.setLastName("Franklin");
        person.setEmail("flord@test.com");
        person.setPhone("0203003");
        person.setZip("8484848");
        person.setAddress("43 Bruyère");
        person.setCity("Albatros");

        invalidFirstnamePerson = new Person();
        invalidFirstnamePerson.setLastName("Martin");
        invalidFirstnamePerson.setFirstName("");
        invalidFirstnamePerson.setEmail("bmartin@gmail.com");
        invalidFirstnamePerson.setAddress("25, Avenue RIO");
        invalidFirstnamePerson.setCity("Porto");
        invalidFirstnamePerson.setPhone("32002020");

        invalidLastnamePerson = new Person();
        invalidLastnamePerson.setLastName("");
        invalidLastnamePerson.setFirstName("Bruce");
        invalidLastnamePerson.setEmail("bmartin@gmail.com");
        invalidLastnamePerson.setAddress("25, Avenue RIO");
        invalidLastnamePerson.setCity("Porto");
        invalidLastnamePerson.setPhone("32002020");

        invalidEmailPerson = new Person();
        invalidEmailPerson.setLastName("");
        invalidEmailPerson.setFirstName("Bruce");
        invalidEmailPerson.setEmail("");
        invalidEmailPerson.setAddress("25, Avenue RIO");
        invalidEmailPerson.setCity("Porto");
        invalidEmailPerson.setPhone("32002020");

        nonExistingPerson = new Person();
        nonExistingPerson.setFirstName("LORD");
        nonExistingPerson.setLastName("Franklin");
        nonExistingPerson.setEmail("flord@test.com");

        /*mockMvc = MockMvcBuilders.standaloneSetup(personController)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();*/
    }

    @Test
    @Order(1)
    public void findall_should_return_all_persons() throws Exception {
        mockMvc.perform(get("/persons")).andExpect(status().isOk());
    }

    @Test
    @Order(2)
    public void given_a_valid_person_save_should_persist_person() throws Exception {
        String uri="/person";
        Person person = new Person();
        person.setLastName("Martin");
        person.setFirstName("Bruce");
        person.setEmail("bmartin@gmail.com");
        person.setAddress("25, Avenue RIO");
        person.setCity("Porto");
        person.setPhone("32002020");

        String personJson = mapToJson(person);
        doReturn(person).when(personService).save(any());
        int status = mockMvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(personJson))
                .andExpect(jsonPath("$.firstName", is(person.getFirstName())))
                .andReturn().getResponse().getStatus();
        assertEquals(201, status);
    }

    @Test
    public void given_an_invalid_lastname_save_should_return_httpstatus_bad_request() throws Exception{
        String uri="/person";
        String personJson = mapToJson(invalidLastnamePerson);
        int status = mockMvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(personJson))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getStatus();
        assertEquals(400, status);
    }

    @Test
    @Order(3)
    public void given_an_invalid_firstname_save_should_return_httpstatus_bad_request() throws Exception{
        String uri="/person";
        String personJson = mapToJson(invalidFirstnamePerson);
        int status = mockMvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(personJson))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getStatus();
        assertEquals(400, status);
    }

    @Test
    @Order(4)
    public void given_an_invalid_email_save_should_return_httpstatus_bad_request() throws Exception{
        String uri="/person";
        String personJson = mapToJson(invalidEmailPerson);
        int status = mockMvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(personJson))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getStatus();
        assertEquals(400, status);
    }

    @Test
    @Order(5)
    public void given_a_existing_person_save_should_update_the_person() throws Exception {
        String uri = "/person?lastname=Franklin&firstname=LORD";
        String personJson = mapToJson(person);

        when(personService.findByLastNameAndFirstName("Franklin", "LORD")).thenReturn(person);
        int status = mockMvc.perform(put(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(personJson))
                .andReturn().getResponse().getStatus();
        //assertEquals(200, status);
    }

    @Test
    @Order(6)
    public void given_an_invalid_firstname_update_should_return_httpstatus_bad_request() throws Exception{
        String uri = "/person?lastname=Franklin&firstname=LORD";
        String personJson = mapToJson(invalidFirstnamePerson);
        when(personService.findByLastNameAndFirstName("Franklin", "LORD")).thenReturn(invalidFirstnamePerson);
        int status = mockMvc.perform(put(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(personJson))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getStatus();
    }

    @Test
    @Order(7)
    public void given_an_invalid_lastname_update_should_return_httpstatus_bad_request() throws Exception{
        String uri = "/person?lastname=Franklin&firstname=LORD";
        String personJson = mapToJson(invalidLastnamePerson);
        when(personService.findByLastNameAndFirstName("Franklin", "LORD")).thenReturn(invalidLastnamePerson);
        int status = mockMvc.perform(put(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(personJson))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getStatus();
    }

    @Test
    @Order(8)
    public void given_an_invalid_email_update_should_return_httpstatus_bad_request() throws Exception{
        String uri = "/person?lastname=Franklin&firstname=LORD";
        String personJson = mapToJson(invalidEmailPerson);

        when(personService.findByLastNameAndFirstName("Franklin", "LORD")).thenReturn(invalidEmailPerson);
        int status = mockMvc.perform(put(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(personJson))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getStatus();
    }

    @Test
    public void given_a_valid_person_update_should_save_changes() throws Exception{
        String uri = "/person";
        Person newPerson=new Person();
        newPerson.setLastName("Abou");
        newPerson.setFirstName("Malick");
        newPerson.setEmail("amalick@test.com");
        String personJson = mapToJson(newPerson);
        when(personService.update(any())).thenReturn(newPerson);
        int status = mockMvc.perform(put(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(personJson))
                .andExpect(status().isAccepted())
                .andReturn().getResponse().getStatus();
        assertEquals(202, status);
    }
    @Test
    @Order(9)
    public void given_a_non_existing_person_update_should_return_httpstatus_not_found() throws Exception{
        String uri = "/person?lastname=Franklin&firstname=LORD";
        String personJson = mapToJson(person);

        when(personService.findByLastNameAndFirstName("Franklin", "LORD")).thenReturn(null);
        int status = mockMvc.perform(put(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(personJson))
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getStatus();
    }

    @Test
    @Order(10)
    public void given_lastname_and_firstname_should_return_a_Person() throws Exception {
        String uri = "/person?lastname=Franklin&firstname=LORD";

        String personJson = mapToJson(person);
        when(personService.findByLastNameAndFirstName("Franklin", "LORD")).thenReturn(person);
        int status = mockMvc.perform(get(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(personJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.firstName",is("LORD")))
                .andReturn().getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    @Order(11)
    public void given_invalidlastname_search_should_return_httpstatus_bad_request() throws Exception {
        String uri = "/person?lastname="+invalidLastnamePerson.getLastName()+"&firstname="+invalidLastnamePerson.getFirstName();

        String personJson = mapToJson(invalidLastnamePerson);
        when(personService.findByLastNameAndFirstName("Franklin", "LORD")).thenReturn(invalidLastnamePerson);
        int status = mockMvc.perform(get(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(personJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getStatus();
        assertEquals(400, status);
    }

    @Test
    @Order(12)
    public void given_invalidfirstname_search_should_return_httpstatus_bad_request() throws Exception {
        String uri = "/person?lastname="+invalidFirstnamePerson.getLastName()+"&firstname="+invalidFirstnamePerson.getFirstName();

        String personJson = mapToJson(invalidLastnamePerson);
        when(personService.findByLastNameAndFirstName("Franklin", "LORD")).thenReturn(invalidFirstnamePerson);
        int status = mockMvc.perform(get(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(personJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getStatus();
        assertEquals(400, status);
    }

    @Test
    @Order(13)
    public void given_non_existing_lastname_and_firstname_searchPerson_should_raise_return_httpstatus_not_found() throws Exception {
        String uri = "/person?lastname=Franklin&firstname=LORD";
        String personJson = mapToJson(person);
        when(personService.findByLastNameAndFirstName("Franklin", "LORD")).thenThrow(PersonNotFoundException.class);
        int status = mockMvc.perform(get(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(personJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getStatus();
        assertEquals(404, status);
    }

    @Test
    public void given_a_non_existing_person_search_should_return_httpstatus_not_found() throws Exception{
        String uri = "/person?lastname=Abou&firstname=Malick";
        String personJson = mapToJson(nonExistingPerson);
        when(personService.findByLastNameAndFirstName("Abou","Malick")).thenReturn(null);
        int status = mockMvc.perform(get(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(personJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getStatus();
        assertEquals(404, status);
    }
    @Test
    @Order(14)
    public void given_a_lastname_and_firstname_should_delete_person() throws Exception {
        String uri = "/person?lastname=mike&firstname=karl";
        Person person = new Person();
        person.setFirstName("karl");
        person.setLastName("mike");

        String personJson = mapToJson(person);
        when(personService.findByLastNameAndFirstName("mike", "karl")).thenReturn(person);
        int status = mockMvc.perform(delete(uri)).andReturn().getResponse().getStatus();
        assertEquals(202, status);
    }

    @Test
    @Order(15)
    public void given_a_person_with_empty_firstname_or_lastname_save_should_raise_InvalidPersonException()
            throws Exception {
        String uri = "/person?lastname=&firstname=";
        Person person = new Person();
        person.setLastName("");
        person.setLastName("");

        String personJson = mapToJson(person);
        mockMvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    /*@Test
    @Order(7)
    public void given_non_existing_person_delete_should_raise_PersonNotFoundException() throws Exception {
        String uri = "/person?lastname=mike&firstname=karl";
        Person person = new Person();
        person.setLastName("mike");
        person.setFirstName("karl");

        when(personService.findByLastNameAndFirstName("mike", "karl")).thenThrow(PersonNotFoundException.class);

        mockMvc.perform(delete(uri).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof PersonNotFoundException));
    }*/

    /*@Test
    @Order(8)
    public void when_firstname_or_lastname_is_empty_delete_should_raise_exception() throws Exception{
        String uri = "/person?lastname=mike&firstname=";
        Person person = new Person();
        person.setLastName("mike");
        person.setFirstName("karl");

        when(personService.findByLastNameAndFirstName("mike", "")).thenReturn(null);

        mockMvc.perform(delete(uri).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof PersonInvalidException));
    }*/


}
