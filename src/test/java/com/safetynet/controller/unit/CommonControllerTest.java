package com.safetynet.controller.unit;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import com.safetynet.controller.CommonController;
import com.safetynet.model.Firestation;
import com.safetynet.model.dto.PersonInfoDTO;
import com.safetynet.model.dto.PersonsCoveredByStation;
import com.safetynet.service.CommonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = CommonController.class)
public class CommonControllerTest extends AbstractControllerTest{

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommonService commonService;

    private Firestation station;
    private PersonInfoDTO person1, person2, person3;
    private List<PersonInfoDTO> personsAtRueDako;
    private PersonsCoveredByStation personsCoveredByStation;

    @BeforeEach
    void initTest(){
        station = new Firestation();
        station.setId(1);
        station.setStation(1);
        station.setAddress("28 rue dako");

        person1 = new PersonInfoDTO();
        person1.setLastname("Marc");
        person1.setFirstname("Dossou");
        person1.setPhone("08 293 929");
        person1.setAddresse("28 rue dako");

        person2 = new PersonInfoDTO();
        person2.setLastname("René");
        person2.setFirstname("Coffi");
        person2.setPhone("57 83 83 12");
        person2.setAddresse("28 rue dako");

        person3 = new PersonInfoDTO();
        person3.setLastname("Parfait");
        person3.setFirstname("Manon");
        person3.setPhone("27 83 78 12");
        person3.setAddresse("02 rue Mohamed");

        personsAtRueDako = new ArrayList<>();
        personsAtRueDako.add(person1);
        personsAtRueDako.add(person2);



    }

    @Test
    public void given_a_station_number_should_return_persons_covered()throws Exception {
        String uri = "/firestation?stationNumber=1";
        int status =  mockMvc.perform(get(uri).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn().getResponse().getStatus();
        assertEquals(200,status);
    }

    @Test
    public void given_an_address_should_return_children_covered() throws Exception {
        String uri = "/childAlert?address=28 rue dako";
        int status =  mockMvc.perform(get(uri).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn().getResponse().getStatus();
        assertEquals(200,status);
    }

    @Test
    public void given_a_station_number_should_return_resident_phones() throws Exception {
        String uri = "/phoneAlert?firestation=1";
        int status =  mockMvc.perform(get(uri).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn().getResponse().getStatus();
        assertEquals(200,status);
    }

    @Test
    public  void given_a_city_should_return_community_email() throws Exception {
        String uri = "/communityEmail?city=Parakou";
        int status =  mockMvc.perform(get(uri).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn().getResponse().getStatus();
        assertEquals(200,status);
    }

    @Test
    public void given_an_address_should_return_persons_covered_at_the_address() throws Exception {
        String uri = "/fire?address=28 rue dako";
        int status =  mockMvc.perform(get(uri).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn().getResponse().getStatus();
        assertEquals(200,status);
    }


    @Test
    public void given_a_lastname_and_firstname_should_return_person_medical_details() throws Exception {
        String uri = "/personInfo?firstName=Dossou&lastName=Marc";
        int status =  mockMvc.perform(get(uri).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn().getResponse().getStatus();
        assertEquals(200,status);
    }


    @Test
    public void given_a_list_of_station_should_return_addresses_coverage() throws Exception {
        String uri = "/flood/stations";
        int status =  mockMvc.perform(get(uri).param("stations","1,2").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn().getResponse().getStatus();
        assertEquals(200,status);
    }


}
