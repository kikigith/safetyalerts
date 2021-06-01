package com.safetynet.controller.integration;

import com.safetynet.controller.CommonController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CommonControllerIT {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    CommonController commonController;

    private String city;

    @BeforeEach
    public void initTest(){
        city = "Culver";
    }

    @Test
    public void given_a_station_number_should_return_persons_covered() throws Exception{
        mockMvc.perform(get("/firestation?stationNumber=2").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andDo(print()).andExpect(MockMvcResultMatchers.jsonPath("$.persons[0].firstname", is("Jonanathan")));
                //.andExpect(MockMvcResultMatchers.jsonPath("$.autresMembres[0].lastName", is("Zemicks")));
    }

    @Test
    public void given_an_address_should_return_children_covered() throws Exception{
        mockMvc.perform(get("/childAlert?address=892 Downing Ct").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andDo(print()).andExpect(MockMvcResultMatchers.jsonPath("$.enfants[0].firstname", is("Zach")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.autresMembres[0].lastName", is("Zemicks")));
    }

    @Test
    public void given_a_firestation_return_residents_phone() throws Exception{
        mockMvc.perform(get("/phoneAlert?firestation=3").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andDo(print()).andExpect(MockMvcResultMatchers.jsonPath("$[0]", is("841-874-6512")));
    }

    @Test
    public void given_a_city_should_return_emails() throws Exception{
        mockMvc.perform(get("/communityEmail?city="+city).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andDo(print()).andExpect(MockMvcResultMatchers.jsonPath("$[0]", is("jaboyd@email.com")));
    }

    @Test
    public void given_an_address_should_return_persons_covered() throws Exception{
        mockMvc.perform(get("/fire?address=892 Downing Ct").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andDo(print()).andExpect(MockMvcResultMatchers.jsonPath("$.personsCovered[0].firstname", is("Sophia")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.personsCovered[0].phone", is("841-874-7878")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.personsCovered[1].firstname", is("Warren")));
    }

    @Test
    public void given_firstname_and_lastname_should_return_medical_info() throws Exception{
        mockMvc.perform(get("/personInfo?firstName=Sophia&lastName=Zemicks").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andDo(print()).andExpect(MockMvcResultMatchers.jsonPath("$[0].antecedents.allergies[0]", is("peanut")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].antecedents.medications[0]", is("aznol:60mg")));
    }

    @Test
    public void given_a_list_of_stations_should_return_persons_covered_at_station_address() throws Exception{
        mockMvc.perform(get("/flood/stations?stations=2,3").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andDo(print()).andExpect(MockMvcResultMatchers.jsonPath("$[0].personsCovered[0].firstname", is("Jonanathan")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].personsCovered[2].firstname", is("Zach")));

    }

}
