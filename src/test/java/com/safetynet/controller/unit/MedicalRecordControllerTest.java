package com.safetynet.controller.unit;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.http.MediaType;
import com.safetynet.controller.FirestationController;
import com.safetynet.model.MedicalRecord;
import com.safetynet.service.MedicalRecordService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@WebMvcTest(controllers = FirestationController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MedicalRecordControllerTest extends  AbstractControllerTest{
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MedicalRecordService medicalRecordService;

    @Test
    @Order(1)
    public void testGetMedicalRecords() throws Exception {
        mockMvc.perform(get("/medicalRecords")).andExpect(status().isOk());
    }

    @Test
    @Order(2)
    public void give_a_valid_lastname_and_firstname_createMedicalRecord_should_persist_medicalrecord() throws Exception {
        // Arrange
        List<String> allergies = new ArrayList<String>();
        allergies.add("spatonine");
        allergies.add("alacol");
        List<String> medications = new ArrayList<String>();
        medications.add("beotim:200mg");
        medications.add("flavoquine:400mg");
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        MedicalRecord mr = new MedicalRecord("Dupont","Aline",sdf.parse("25-02-2000"),medications,allergies);

        String medicalRecordJson = mapToJson(mr);

        doReturn(mr).when(medicalRecordService).save(any());

        // Act and Assert
        int status = mockMvc
                .perform(
                        post("/medicalRecord").contentType(MediaType.APPLICATION_JSON_VALUE).content(medicalRecordJson))
                .andExpect(jsonPath("$.firstName", is(mr.getFirstName()))).andReturn().getResponse().getStatus();
        assertEquals(201, status);
    }

    @Test
    public void testUpdateMedicalRecord() throws Exception {
        String uri = "/medicalRecord?firstname=Aline&lastname=Dupont";
        List<String> allergies = new ArrayList<String>();
        allergies.add("spatomine");
        allergies.add("beoziol");
        List<String> medications = new ArrayList<String>();
        medications.add("quinine:200mg");
        medications.add("chloroquine:400mg");
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        MedicalRecord mr = new MedicalRecord("Durand","Aline",sdf.parse("15-02-2001"),medications,allergies);


        String medicalRecordJson = mapToJson(mr);
        int status = mockMvc.perform(put(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(medicalRecordJson))
                .andReturn().getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    public void given_a_lastname_and_firstname_should_delete_medicalRecord() throws Exception {
        String uri = "/medicalRecord?lastname=Dupond&firstname=Aline";
        int status = mockMvc.perform(delete(uri)).andReturn().getResponse().getStatus();
        assertEquals(202, status);
    }
}
