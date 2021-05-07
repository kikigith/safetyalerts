package com.safetynet.controller.integration;

import static com.safetynet.controller.integration.AbstractITControllerTest.asJsonString;
import static org.hamcrest.CoreMatchers.is;

import com.safetynet.controller.PersonController;
import com.safetynet.model.Person;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PersonController personController;

    @Test
    @Order(1)
    public void findAll_should_return_all_person_from_json_source() throws Exception {

        mockMvc.perform(get("/persons").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andDo(print()).andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName", is("John")));
    }

    @Test
    @Order(2)
    public void given_a_new_person_should_be_persisted_in_json_file() throws Exception {
        Person pers = new Person();
        pers.setLastName("Benett");
        pers.setFirstName("Trump");
        pers.setEmail("btrump@trump.world");
        pers.setCity("Parakou");
        pers.setPhone("00495929949");
        pers.setZip("000000");

        mockMvc.perform(post("/person").content(asJsonString(pers))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").exists()).andDo(MockMvcResultHandlers.print());
//				.andExpect(MockMvcResultMatchers.jsonPath("$.id"), is("4L"));

    }

    @Test
    @Order(3)
    public void given_an_existing_person_should_be_modified_in_json_file() throws Exception {
        Person pers = new Person();
        pers.setLastName("Benett");
        pers.setFirstName("Trump");
        pers.setEmail("btrump@trump.world");
        pers.setCity("Cotonou");
        pers.setPhone("093939292");
        pers.setZip("000000");
        mockMvc.perform(put("/person").content(asJsonString(pers))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andDo(print()).andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Trump"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.city").value("Cotonou"));
    }

    @Test
    @Order(4)
    public void given_an_existing_person_should_be_removed() throws Exception {
        Person pers = new Person();
        pers.setLastName("Benett");
        pers.setFirstName("Trump");
        mockMvc.perform(delete("/person?lastname=Benett&firstname=Trump").content(asJsonString(pers))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }

}
