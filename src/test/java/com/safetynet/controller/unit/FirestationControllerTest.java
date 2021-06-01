package com.safetynet.controller.unit;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebMvcTest(controllers = FirestationController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FirestationControllerTest extends AbstractControllerTest{
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FirestationService firestationService;

    private String address, city;
    private List<String> phones;
    Firestation station, station1, station2,
            invalidIdFirestation,invalidAddressFirestation, nonExistingFirestation;
    List<Firestation> stations;
    List<Firestation> stationsAtCotonou, invalidStations;
    Optional<List<Firestation>> invalidAddressStations;
    Person person1, person2, person3, person4;

    @BeforeEach
    public void initTest() {
        station = new Firestation();
        station.setId(1);
        station.setStation(1);
        station.setAddress("Rue 34 Cotonou");

        station1 = new Firestation();
        station1.setId(2);
        station1.setStation(2);
        station1.setAddress("Rue 68 Porto");

        station2 = new Firestation();
        station2.setId(3);
        station2.setStation(3);
        station2.setAddress("Rue 09 Parakou");

        invalidIdFirestation = new Firestation();
        invalidIdFirestation.setId(-1);
        invalidIdFirestation.setStation(-1);
        invalidIdFirestation.setAddress("54 rue bonheur");

        invalidAddressFirestation = new Firestation();
        invalidAddressFirestation.setId(3);
        invalidAddressFirestation.setStation(3);
        invalidAddressFirestation.setAddress("");
        invalidStations = new ArrayList<>();
        invalidStations.add(invalidAddressFirestation);
        invalidAddressStations=Optional.of(invalidStations);

        nonExistingFirestation = new Firestation();
        nonExistingFirestation.setId(4);
        nonExistingFirestation.setStation(4);
        nonExistingFirestation.setAddress("56 avenue bio guera");

        address = "25 rue Beauchamp";
        phones = new ArrayList<>();
        phones.add("24 132 45");

        stations = new ArrayList<>();
        stations.add(station);
        stations.add(station1);
        stations.add(station2);

        stationsAtCotonou = new ArrayList<>();
        stationsAtCotonou.add(station);
    }

    @Test
    public void getFirestations_should_return_all_firestations() throws Exception {
        mockMvc.perform(get("/stations")).andExpect(status().isOk());
    }

    @Test
    public void given_a_new_person_createFirestation_should_persist_the_firestation() throws Exception {
        String firestationJson = mapToJson(station);
        doReturn(station).when(firestationService).save(any());
        int status = mockMvc
                .perform(post("/station").contentType(MediaType.APPLICATION_JSON_VALUE).content(firestationJson))
                .andExpect(jsonPath("$.station", is(station.getStation()))).andReturn().getResponse().getStatus();
        assertEquals(201, status);
    }

    @Test
    public void given_a_invalid_station_id_createFirestation_should_return_httpstatus_bad_request() throws Exception{
        String firestationJson = mapToJson(invalidIdFirestation);
        int status = mockMvc
                .perform(post("/station").contentType(MediaType.APPLICATION_JSON_VALUE).content(firestationJson))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getStatus();
        assertEquals(400, status);
    }

    @Test
    public void given_a_invalid_address_createFirestation_should_return_httpstatus_bad_request() throws Exception{
        String firestationJson = mapToJson(invalidAddressFirestation);
        int status = mockMvc
                .perform(post("/station").contentType(MediaType.APPLICATION_JSON_VALUE).content(firestationJson))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getStatus();
        assertEquals(400, status);
    }

    @Test
    public void given_an_existing_firestation_update_should_modify_the_firestation() throws Exception {
        String uri = "/station";
        Firestation newStation=new Firestation();
        newStation.setStation(1);
        newStation.setAddress("76 rue Kerekou");
        String firestationJson = mapToJson(station);
        when(firestationService.update(station)).thenReturn(newStation);
        int status = mockMvc.perform(put(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(firestationJson))
                .andExpect(status().isAccepted())
                .andReturn().getResponse().getStatus();
        assertEquals(202, status);
    }

    @Test
    public void given_an_invalid_station_id_update_should_return_httpstatus_bad_request() throws Exception{
        String uri = "/station?stationId=-1";
        String firestationJson = mapToJson(invalidIdFirestation);
        when(firestationService.findById(anyInt())).thenReturn(invalidIdFirestation);
        int status = mockMvc
                .perform(put("/station").contentType(MediaType.APPLICATION_JSON_VALUE).content(firestationJson))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getStatus();
        assertEquals(400, status);
    }

    @Test
    public void given_an_invalid_address_update_should_return_httpstatus_bad_request() throws Exception{
        String uri = "/station?stationId=3";
        String firestationJson = mapToJson(invalidAddressFirestation);
        when(firestationService.findByAddress(anyString())).thenReturn(invalidStations);
        int status = mockMvc
                .perform(put("/station").contentType(MediaType.APPLICATION_JSON_VALUE).content(firestationJson))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getStatus();
        assertEquals(400, status);
    }

    @Test
    public void given_a_non_existing_firestation_update_should_return_httpstatus_not_found() throws Exception{
        String uri = "/station?stationId=4";
        String firestationJson = mapToJson(nonExistingFirestation);
        when(firestationService.findById(4)).thenReturn(null);
        int status = mockMvc
                .perform(put("/station").contentType(MediaType.APPLICATION_JSON_VALUE).content(firestationJson))
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getStatus();
        assertEquals(404, status);
    }


    @Test
    public void given_a_station_id_should_Delete_firestation() throws Exception {
        String uri = "/station?stationId=1";
        int status = mockMvc.perform(delete(uri)).andReturn().getResponse().getStatus();
        assertEquals(202, status);
    }

    @Test
    public void given_a_station_id_should_return_a_firestation() throws Exception {
        String uri = "/station?stationId=1";
        String firestationJson = mapToJson(station);
        when(firestationService.findById(1)).thenReturn(station);
        int status = mockMvc.perform(get(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(firestationJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.address",is("Rue 34 Cotonou")))
                .andReturn().getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    public void given_a_non_existing_firestation_id_search_should_return_httpstatus_not_found() throws Exception{
        String uri = "/station?stationId=4";
        String firestationJson = mapToJson(nonExistingFirestation);
        when(firestationService.findByStation(1)).thenReturn(null);
        int status = mockMvc.perform(get(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(firestationJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getStatus();
        assertEquals(404, status);
    }

    @Test
    public void given_an_address_should_return_a_firestation() throws Exception {
        String uri = "/stationAddress?address=Rue 34 Cotonou";
        String firestationJson = mapToJson(station);
        when(firestationService.findByAddress("Rue 34 Cotonou")).thenReturn(stationsAtCotonou);
        int status = mockMvc.perform(get(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(firestationJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$[0].station",is(1)))
                .andReturn().getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    public void given_a_non_existing_firestation_address_search_should_return_httpstatus_not_found() throws Exception{
        String uri = "/stationAddress?address=56 avenue bio guera";
        String firestationJson = mapToJson(nonExistingFirestation);
        when(firestationService.findByAddress("56 avenue bio guera")).thenReturn(null);
        int status = mockMvc.perform(get(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(firestationJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getStatus();
        assertEquals(404, status);
    }

}
