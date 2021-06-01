package com.safetynet.controller.integration;


import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.safetynet.controller.integration.AbstractITControllerTest.asJsonString;

import static org.hamcrest.CoreMatchers.is;
import com.safetynet.controller.FirestationController;
import com.safetynet.model.Firestation;
import com.safetynet.model.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class FirestationControllerIT {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    FirestationController firestationController;

    @Test
    public void find_all_should_return_all_firestation() throws Exception{
        mockMvc.perform(get("/stations").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andDo(print()).andExpect(MockMvcResultMatchers.jsonPath("$[0].address", is("29 15th St")));
    }

    @Test
    public void given_a_new_firestation_should_be_persisted_in_json_file() throws  Exception{
        Firestation firestation = new Firestation();
        firestation.setId(5);
        firestation.setStation(5);
        firestation.setAddress("70 Abomey-Calavi");
        mockMvc.perform(post("/station").content(asJsonString(firestation))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").exists()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void given_an_existing_firestation_should_be_modified() throws Exception{
        Firestation firestation3 = new Firestation();
        firestation3.setId(3);
        firestation3.setStation(3);
        firestation3.setAddress("1509 Culver St");//setAddress("70 Abomey-Calavi");

        mockMvc.perform(put("/station").content(asJsonString(firestation3))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isAccepted())
                .andDo(print()).andExpect(MockMvcResultMatchers.jsonPath("$.address").value("1509 Culver St"));
    }

    @Test
    public void given_an_existing_firestation_should_be_removed() throws Exception{
        Firestation firestation = new Firestation();
        firestation.setId(3);
        firestation.setStation(3);
        firestation.setAddress("1509 Culver St");
        mockMvc.perform(delete("/station?stationId=3").content(asJsonString(firestation))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }
}
