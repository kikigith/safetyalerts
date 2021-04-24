package com.safetynet.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.model.AlertsData;
import com.safetynet.model.Firestation;
import com.safetynet.model.MedicalRecord;
import com.safetynet.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DataSourceComponentTest {

    @Mock
    private Resource jsonFile;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private DataSourceComponent dataSourceComponent;

    private AlertsData alertsData;

    @BeforeEach
    public void initTest(){

        Person person = new Person();
        person.setFirstName("mike");
        person.setLastName("fiver");
        person.setEmail("fmike@gmail");
        person.setCity("cotonou");
        person.setAddress("rue 30 cadjehoun");
        person.setPhone("98 9947272");
        person.setZip("0000");

        Person person1 = new Person();
        person1.setFirstName("fred");
        person1.setLastName("boulart");
        person1.setEmail("fboulart@gmail");
        person1.setCity("allada");
        person1.setAddress("rue 30 louverture");
        person1.setPhone("78 938828");
        person1.setZip("0000");

        List<Person> persons = new ArrayList<>();
        persons.add(person);
        persons.add(person1);

        List<Firestation> firestations = new ArrayList<>();
        Firestation firestation = new Firestation();
        firestation.setStation(1);
        firestation.setAddress("rue 30 louverture");
        firestations.add(firestation);

        List<MedicalRecord> medicalRecords = new ArrayList<>();
        Calendar calendar = new GregorianCalendar(2000, 11, 03);
        List<String> medications= new ArrayList<>();
        medications.add("citine:300mg");
        List<String> allergies= new ArrayList<>();
        allergies.add("phenotine");
        MedicalRecord medicalRecord = new MedicalRecord("mike","fiver",calendar.getTime(),medications, allergies);
        medicalRecords.add(medicalRecord);

        alertsData = new AlertsData(persons,firestations,medicalRecords);
    }


    @Test
    public void call_should_load_json_data() throws Exception{

        when(objectMapper.readValue(jsonFile.getFile(), AlertsData.class)).thenReturn(alertsData);
        dataSourceComponent.loadJsonData();
        assertThat(dataSourceComponent.getPersons()).hasSize(2);
    }
}
