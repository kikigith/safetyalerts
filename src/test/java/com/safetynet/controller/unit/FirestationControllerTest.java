package com.safetynet.controller.unit;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.safetynet.controller.FirestationController;
import com.safetynet.model.Firestation;
import com.safetynet.model.Person;
import com.safetynet.service.FirestationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(controllers = FirestationController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FirestationControllerTest extends AbstractControllerTest{
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FirestationService firestationService;

    private String address, city;
    private List<String> phones;
    Firestation station1, station2;
    Person person1, person2, person3, person4;

    @BeforeEach
    public void initTest() {
        address = "25 rue Beauchamp";
        phones = new ArrayList<>();
        phones.add("24 132 45");
    }

    @Test
    public void getFirestations_should_return_all_firestations() throws Exception {
        mockMvc.perform(get("/firestations")).andExpect(status().isOk());
    }

    @Test
    public void given_a_new_person_createFirestation_should_persist_the_firestation() throws Exception {
        Firestation frs = new Firestation();
        frs.setStation(1);
        frs.setAddress("Rue 34 Cotonou");

        String firestationJson = mapToJson(frs);

        doReturn(frs).when(firestationService).save(any());

        int status = mockMvc
                .perform(post("/firestation").contentType(MediaType.APPLICATION_JSON_VALUE).content(firestationJson))
                .andExpect(jsonPath("$.station", is(frs.getStation()))).andReturn().getResponse().getStatus();
        assertEquals(201, status);
    }

    @Test
    public void given_an_existing_firestation_save_should_update_the_firestation() throws Exception {
        String uri = "/firestation?stationId=1";
        Firestation frs = new Firestation();
        frs.setStation(1);
        frs.setAddress("Rue 35 Cotonou");

        String firestationJson = mapToJson(frs);
        int status = mockMvc.perform(put(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(firestationJson))
                .andReturn().getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    public void given_a_station_id_should_Delete_firestation() throws Exception {
        String uri = "/firestation?stationId=1";
        int status = mockMvc.perform(delete(uri)).andReturn().getResponse().getStatus();
        assertEquals(202, status);
    }

}
