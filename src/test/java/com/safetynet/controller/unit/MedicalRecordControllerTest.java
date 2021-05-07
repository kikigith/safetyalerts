package com.safetynet.controller.unit;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.safetynet.controller.MedicalRecordController;
import org.junit.jupiter.api.*;
import org.springframework.http.MediaType;
import com.safetynet.model.MedicalRecord;
import com.safetynet.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@WebMvcTest(controllers = MedicalRecordController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MedicalRecordControllerTest extends  AbstractControllerTest{
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MedicalRecordService medicalRecordService;

    private MedicalRecord medicalRecord, invalidLastnameMedicalRecord,invalidFirstnameMedicalRecord,nonExistingMedicalRecord;

    @BeforeEach
    public void initTests() throws  Exception{
        List<String> allergies = new ArrayList<>();
        allergies.add("spatonine");
        allergies.add("alacol");
        List<String> medications = new ArrayList<>();
        medications.add("beotim:200mg");
        medications.add("flavoquine:400mg");
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        medicalRecord = new MedicalRecord("Dupont","Aline",sdf.parse("12-02-2001"),medications,allergies);

        List<String> allergies1 = new ArrayList<>();
        allergies1.add("spatonine");
        allergies1.add("alacol");
        List<String> medications1 = new ArrayList<>();
        medications1.add("beotim:200mg");
        medications1.add("flavoquine:400mg");
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
        invalidLastnameMedicalRecord = new MedicalRecord("","Pierre",sdf1.parse("21-02-2001"),medications1,allergies1);

        List<String> allergies2 = new ArrayList<>();
        allergies2.add("spatonine");
        allergies2.add("alacol");
        List<String> medications2 = new ArrayList<>();
        medications2.add("beotim:200mg");
        medications2.add("flavoquine:400mg");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");
        invalidFirstnameMedicalRecord = new MedicalRecord("Julie","",sdf2.parse("21-02-2001"),medications2,allergies2);

        List<String> allergies3 = new ArrayList<>();
        allergies3.add("spatonine");
        allergies3.add("alacol");
        List<String> medications3 = new ArrayList<>();
        medications3.add("beotim:200mg");
        medications3.add("flavoquine:400mg");
        SimpleDateFormat sdf3 = new SimpleDateFormat("dd-MM-yyyy");
        nonExistingMedicalRecord = new MedicalRecord("Julie","Pierre",sdf3.parse("21-02-2001"),medications3,allergies3);
    }

    @Test
    @Order(1)
    public void getMedicalRecords_should_return_all_medicalreocrds() throws Exception {
        mockMvc.perform(get("/medicalrecords")).andExpect(status().isOk());
    }

    @Test
    @Order(2)
    public void give_a_valid_lastname_and_firstname_createMedicalRecord_should_persist_medicalrecord() throws Exception {
        // Arrange
        String uri = "/medicalrecord";
        String medicalRecordJson = mapToJson(medicalRecord);
        doReturn(medicalRecord).when(medicalRecordService).save(any());

        // Act and Assert
        int status = mockMvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(medicalRecordJson))
                .andExpect(jsonPath("$.firstName", is(medicalRecord.getFirstName())))
                .andReturn().getResponse().getStatus();
        assertEquals(201, status);
    }

    @Test
    public void given_an_invalid_lastname_save_should_return_httpstatus_bad_request() throws Exception{
        String uri="/medicalrecord";
        String invalidLastnameMedicalRecordJson = mapToJson(invalidLastnameMedicalRecord);
        int status = mockMvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(invalidLastnameMedicalRecordJson))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getStatus();
        assertEquals(400, status);
    }

    @Test
    public void given_an_invalid_firstname_save_should_return_httpstatus_bad_request() throws Exception{
        String uri="/medicalrecord";
        String invalidFirstnameMedicalRecordJson = mapToJson(invalidFirstnameMedicalRecord);
        int status = mockMvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(invalidFirstnameMedicalRecordJson))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getStatus();
        assertEquals(400, status);
    }



    @Test
    public void given_a_medicalrecord_update_should_modify_the_medicalrecord() throws Exception {
        String uri = "/medicalrecord";
        List<String> allergies = new ArrayList<>();
        allergies.add("spatomine");
        allergies.add("beoziol");
        List<String> medications = new ArrayList<>();
        medications.add("quinine:200mg");
        medications.add("chloroquine:400mg");
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        MedicalRecord newMedicalRecord = new MedicalRecord("Dupont","Aline",sdf.parse("15-02-2001"),medications,allergies);


        String medicalRecordJson = mapToJson(newMedicalRecord);
        when(medicalRecordService.findByLastnameAndFirstname("Dupont","Aline")).thenReturn(medicalRecord);
        when(medicalRecordService.update(medicalRecord)).thenReturn(newMedicalRecord);
        int status = mockMvc.perform(put(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(medicalRecordJson))
                .andReturn().getResponse().getStatus();
        assertEquals(202, status);
    }

    @Test
    public void given_invalidlastname_medicalrecord_update_should_return_httpstatus_bad_request() throws Exception {
        String uri = "/medicalrecord?lastname="+invalidLastnameMedicalRecord.getLastName()+"&firstname="+invalidLastnameMedicalRecord.getFirstName();

        String invalidLastnameMedicalRecordJson = mapToJson(invalidLastnameMedicalRecord);
        when(medicalRecordService.findByLastnameAndFirstname(any(), any())).thenReturn(invalidLastnameMedicalRecord);
        int status = mockMvc.perform(put(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(invalidLastnameMedicalRecordJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getStatus();
        assertEquals(400, status);
    }

    @Test
    @Order(12)
    public void given_invalidfirstname_update_should_return_httpstatus_bad_request() throws Exception {
        String uri = "/medicalrecord?lastname="+invalidFirstnameMedicalRecord.getLastName()+"&firstname="+invalidFirstnameMedicalRecord.getFirstName();

        String invalidFirstnameMedicalRecordJson = mapToJson(invalidFirstnameMedicalRecord);
        when(medicalRecordService.findByLastnameAndFirstname(any(),any())).thenReturn(invalidFirstnameMedicalRecord);
        int status = mockMvc.perform(put(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(invalidFirstnameMedicalRecordJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getStatus();
        assertEquals(400, status);
    }

    @Test
    public void given_a_non_existing_medicalrecord_update_should_return_httpstatus_not_found() throws Exception{
        String uri = "/medicalrecord?lastname=Julie&firstname=Pierre";
        String nonExistingMedicalRecordJson = mapToJson(nonExistingMedicalRecord);
        when(medicalRecordService.findByLastnameAndFirstname("Julie","Pierre")).thenReturn(null);
        int status = mockMvc.perform(put(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(nonExistingMedicalRecordJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getStatus();
        assertEquals(404, status);
    }

    @Test
    public void given_lastname_and_firstname_search_should_return_a_medical_record() throws Exception {
        String uri = "/medicalrecord?lastname=Dupont&firstname=Aline";

        String medicalRecordJson = mapToJson(medicalRecord);
        when(medicalRecordService.findByLastnameAndFirstname("Dupont", "Aline")).thenReturn(medicalRecord);
        int status = mockMvc.perform(get(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(medicalRecordJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.firstName",is("Aline")))
                .andReturn().getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    public void given_invalidlastname_medicalrecord_search_should_return_httpstatus_bad_request() throws Exception {
        String uri = "/medicalrecord?lastname="+invalidLastnameMedicalRecord.getLastName()+"&firstname="+invalidLastnameMedicalRecord.getFirstName();

        String invalidLastnameMedicalRecordJson = mapToJson(invalidLastnameMedicalRecord);
        when(medicalRecordService.findByLastnameAndFirstname(any(), any())).thenReturn(invalidLastnameMedicalRecord);
        int status = mockMvc.perform(get(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(invalidLastnameMedicalRecordJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getStatus();
        assertEquals(400, status);
    }

    @Test
    @Order(12)
    public void given_invalidfirstname_search_should_return_httpstatus_bad_request() throws Exception {
        String uri = "/medicalrecord?lastname="+invalidFirstnameMedicalRecord.getLastName()+"&firstname="+invalidFirstnameMedicalRecord.getFirstName();

        String invalidFirstnameMedicalRecordJson = mapToJson(invalidFirstnameMedicalRecord);
        when(medicalRecordService.findByLastnameAndFirstname(any(),any())).thenReturn(invalidFirstnameMedicalRecord);
        int status = mockMvc.perform(get(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(invalidFirstnameMedicalRecordJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getStatus();
        assertEquals(400, status);
    }

    @Test
    public void given_a_non_existing_medicalrecord_search_should_return_httpstatus_not_found() throws Exception{
        String uri = "/medicalrecord?lastname=Julie&firstname=Pierre";
        String nonExistingMedicalRecordJson = mapToJson(nonExistingMedicalRecord);
        when(medicalRecordService.findByLastnameAndFirstname("Julie","Pierre")).thenReturn(null);
        int status = mockMvc.perform(get(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(nonExistingMedicalRecordJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getStatus();
        assertEquals(404, status);
    }

    @Test
    public void given_a_lastname_and_firstname_should_delete_medicalRecord() throws Exception {
        String uri = "/medicalrecord?lastname=Dupond&firstname=Aline";
        int status = mockMvc.perform(delete(uri)).andReturn().getResponse().getStatus();
        assertEquals(202, status);
    }
}
