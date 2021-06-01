package com.safetynet.controller.integration;


import com.safetynet.controller.MedicalRecordController;
import com.safetynet.model.MedicalRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.safetynet.controller.integration.AbstractITControllerTest.asJsonString;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MedicalRecordControllerIT {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    MedicalRecordController MedicalRecordController;

    private MedicalRecord newMedicalRecord, medicalRecordToUpdate;

    @BeforeEach
    public void initTest(){
        List<String> medications = new ArrayList<>();
        medications.add("zetiol:344mg");
        medications.add("uvitol:500mg");
        List<String> allergies = new ArrayList<>();
        allergies.add("pentol");
        LocalDate d = LocalDate.of(2000,11,3);
        newMedicalRecord = new MedicalRecord(1,"Paul","Tossou",d,medications,allergies);

        List<String> medications1 = new ArrayList<>();
        medications1.add("zetiol:344mg");
        medications1.add("uvitol:500mg");
        List<String> allergies1 = new ArrayList<>();
        allergies1.add("pentol");
        LocalDate d1 = LocalDate.of(2000,11,3);
        medicalRecordToUpdate = new MedicalRecord(2,"Boyd","Jacob",d1,medications1,allergies1);
    }

    @Test
    public void find_all_should_return_all_medicalrecords() throws Exception{
        mockMvc.perform(get("/medicalrecords").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andDo(print()).andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName", is("Boyd")));
    }

    @Test
    public void given_a_new_medicalrecord_should_be_persisted() throws Exception{

        mockMvc.perform(post("/medicalrecord").content(asJsonString(newMedicalRecord))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.medications").exists()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void given_an_existing_medicalrecord_should_be_modified() throws Exception{
        mockMvc.perform(put("/medicalrecord").content(asJsonString(medicalRecordToUpdate))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isAccepted())
                .andDo(print()).andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Jacob"));
    }

    @Test
    public void given_an_existing_medicalrecord_should_be_removed() throws Exception{
        mockMvc.perform(delete("/medicalrecord?lastname=Paul&firstname=Tossou").content(asJsonString(newMedicalRecord))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }
}
