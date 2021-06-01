package com.safetynet.service;

import com.safetynet.exception.FirestationNotFoundException;
import com.safetynet.model.Firestation;
import com.safetynet.model.MedicalRecord;
import com.safetynet.model.Person;
import com.safetynet.model.dto.*;
import com.safetynet.repository.*;
import com.safetynet.utils.Utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommonServiceTest {


    @Mock
    PersonRepositoryImpl personRepository;
    @Mock
    FirestationRepositoryImpl firestationRepository;
    @Mock
    MedicalRecordRepositoryImpl medicalRecordRepository;

    @InjectMocks
    CommonServiceImpl commonService;


    //private MockedStatic<Utils> utilsMock = Mockito.mockStatic(Utils.class);
    private Person person, person1, person2, person3, person4, person5, person6, person7;
    private PersonInfoDTO personInfoDTO, personInfoDTO1, personInfoDTO2, personInfoDTO3, personInfoDTO4;
    private List<Person> persons,personAtRueDako, personsStation1, personsStation2;
    private List<PersonInfoDTO> personsInfoDTO;
    private Firestation station,station1, station2;
    private Optional<Firestation> nonExistingFirestation;
    private List<Firestation> stations;
    private MedicalRecord medicalRecordPerson, medicalRecordPerson1,
            medicalRecordPerson2, medicalRecordPerson3,medicalRecordPerson4,medicalRecordPerson5,
            medicalRecordPerson6,medicalRecordPerson7;
    private PersonsCoveredByStation personsCoveredByStation;

    @BeforeEach
    public void initTests() throws  Exception{
        person = new Person();
        person.setFirstName("Marc");
        person.setLastName("Dossou");
        person.setEmail("mdossou@yahoo.fr");
        person.setCity("Porto-Novo");
        person.setPhone("21340978");
        person.setZip("03-000");
        person.setAddress("45 rue obama");

        person1 = new Person();
        person1.setFirstName("Fiacre");
        person1.setLastName("Djigla");
        person1.setEmail("fdjigla@gmail.com");
        person1.setCity("Allada");
        person1.setPhone("97682123");
        person1.setZip("06-001");
        person1.setAddress("28 rue dako");

        person2 = new Person();
        person2.setFirstName("Radji");
        person2.setLastName("Inoussa");
        person2.setEmail("rinoussa@facebook.com");
        person2.setCity("Parakou");
        person2.setPhone("90087634");
        person2.setZip("02-010");
        person2.setAddress("28 rue dako");

        person3 = new Person();
        person3.setFirstName("Fifa");
        person3.setLastName("Idohou");
        person3.setEmail("fidohou@yahoo.fr");
        person3.setCity("Porto-Novo");
        person3.setPhone("21340978");
        person3.setZip("03-000");
        person3.setAddress("28 rue dako");

        person4 = new Person();
        person4.setFirstName("René");
        person4.setLastName("Lokossou");
        person4.setEmail("rlokossou@yahoo.fr");
        person4.setCity("Porto-Novo");
        person4.setPhone("21340978");
        person4.setZip("03-000");
        person4.setAddress("28 rue dako");

        person5 = new Person();
        person5.setFirstName("Ruffine");
        person5.setLastName("Laborde");
        person5.setEmail("rlaborde@yahoo.fr");
        person5.setCity("Sèmè");
        person5.setPhone("23890978");
        person5.setZip("03-000");
        person5.setAddress("Avenue Bob Marley");

        person6 = new Person();
        person6.setFirstName("Josué");
        person6.setLastName("Aifa");
        person6.setEmail("jaifa@hotmail.fr");
        person6.setCity("Ekpè");
        person6.setPhone("238472663");
        person6.setZip("03-000");
        person6.setAddress("Avenue Bob Marley");

        person7 = new Person();
        person7.setFirstName("Josué");
        person7.setLastName("Aifa");
        person7.setEmail("jaifa@hotmail.fr");
        person7.setCity("Ekpè");
        person7.setPhone("238472663");
        person7.setZip("03-000");
        person7.setAddress("45 rue des oliviers");

        station = new Firestation();
        station.setStation(1);
        station.setAddress("28 rue dako");

        station1 = new Firestation();
        station1.setStation(2);
        station1.setAddress("Avenue Bob Marley");

        station2 = new Firestation();
        station2.setStation(3);
        station2.setAddress("45 rue des oliviers");

        stations = new ArrayList<>();
        stations.add(station);
        stations.add(station1);
        stations.add(station2);

        medicalRecordPerson = new MedicalRecord();
        List<String> allergies = new ArrayList<>();
        allergies.add("phénotine");
        allergies.add("corol");
        List<String> medications = new ArrayList<>();
        medications.add("rubezol:200mg");
        medications.add("panedol:400mg");
        medicalRecordPerson.setFirstName("Marc");
        medicalRecordPerson.setLastName("Dossou");
        LocalDate d = LocalDate.of(2010,3,3);
        medicalRecordPerson.setBirthdate(d);
        medicalRecordPerson.setMedications(medications);
        medicalRecordPerson.setAllergies(allergies);

        medicalRecordPerson1 = new MedicalRecord();
        List<String> allergies1 = new ArrayList<>();
        allergies1.add("azétine");
        allergies1.add("coarol");
        List<String> medications1 = new ArrayList<>();
        medications1.add("rubezol:200mg");
        medications1.add("panedol:400mg");
        medicalRecordPerson1.setFirstName("Fiacre");
        medicalRecordPerson1.setLastName("Djigla");
        LocalDate d1 = LocalDate.of(2000,05,02);
        medicalRecordPerson1.setBirthdate(d1);
        medicalRecordPerson1.setMedications(medications1);
        medicalRecordPerson1.setAllergies(allergies1);

        medicalRecordPerson2 = new MedicalRecord();
        List<String> allergies2 = new ArrayList<>();
        allergies2.add("azétine");
        allergies2.add("coarol");
        List<String> medications2 = new ArrayList<>();
        medications2.add("rubezol:200mg");
        medications2.add("panedol:400mg");
        medicalRecordPerson2.setFirstName("Radji");
        medicalRecordPerson2.setLastName("Inoussa");
        LocalDate d2 = LocalDate.of(1998, 3, 6);
        medicalRecordPerson2.setBirthdate(d2);
        medicalRecordPerson2.setAllergies(allergies2);
        medicalRecordPerson2.setMedications(medications2);

        medicalRecordPerson3 = new MedicalRecord();
        List<String> allergies3 = new ArrayList<>();
        allergies3.add("azétine");
        allergies3.add("coarol");
        List<String> medications3 = new ArrayList<>();
        medications3.add("rubezol:200mg");
        medications3.add("panedol:400mg");
        medicalRecordPerson3.setFirstName("Fifa");
        medicalRecordPerson3.setLastName("Idohou");
        LocalDate d3 = LocalDate.of(2011,4,12);
        medicalRecordPerson3.setBirthdate(d3);
        medicalRecordPerson3.setMedications(medications);
        medicalRecordPerson3.setAllergies(allergies3);

        medicalRecordPerson4 = new MedicalRecord();
        List<String> allergies4 = new ArrayList<>();
        allergies4.add("azétine");
        allergies4.add("coarol");
        List<String> medications4 = new ArrayList<>();
        medications4.add("rubezol:200mg");
        medications4.add("panedol:400mg");
        medicalRecordPerson4.setFirstName("René");
        medicalRecordPerson4.setLastName("Lokossou");
        LocalDate d4 = LocalDate.of(2013,5,22);
        medicalRecordPerson4.setBirthdate(d4);
        medicalRecordPerson4.setAllergies(allergies4);
        medicalRecordPerson4.setMedications(medications4);

        medicalRecordPerson5 = new MedicalRecord();
        List<String> allergies5 = new ArrayList<>();
        allergies5.add("azétine");
        allergies5.add("coarol");
        List<String> medications5 = new ArrayList<>();
        medications5.add("rubezol:200mg");
        medications5.add("panedol:400mg");
        medicalRecordPerson5.setFirstName("Ruffine");
        medicalRecordPerson5.setLastName("Laborde");
        LocalDate d5 = LocalDate.of(2013,5,22);
        medicalRecordPerson5.setBirthdate(d5);
        medicalRecordPerson5.setAllergies(allergies5);
        medicalRecordPerson5.setMedications(medications5);

        medicalRecordPerson6 = new MedicalRecord();
        List<String> allergies6 = new ArrayList<>();
        allergies6.add("azétine");
        allergies6.add("coarol");
        List<String> medications6 = new ArrayList<>();
        medications6.add("rubezol:200mg");
        medications6.add("panedol:400mg");
        medicalRecordPerson6.setFirstName("Josué");
        medicalRecordPerson6.setLastName("Aifa");
        LocalDate d6 = LocalDate.of(2013,5,22);
        medicalRecordPerson6.setBirthdate(d6);
        medicalRecordPerson6.setAllergies(allergies6);
        medicalRecordPerson6.setMedications(medications6);

        medicalRecordPerson7 = new MedicalRecord();
        List<String> allergies7 = new ArrayList<>();
        allergies7.add("azétine");
        allergies7.add("coarol");
        List<String> medications7 = new ArrayList<>();
        medications7.add("rubezol:200mg");
        medications7.add("panedol:400mg");
        medicalRecordPerson7.setFirstName("Josué");
        medicalRecordPerson7.setLastName("Aifa");
        LocalDate d7 = LocalDate.of(2013,5,22);
        medicalRecordPerson7.setBirthdate(d7);
        medicalRecordPerson7.setAllergies(allergies7);
        medicalRecordPerson7.setMedications(medications7);

        persons = new ArrayList<>();
        persons.add(person1);
        persons.add(person2);
        persons.add(person3);
        persons.add(person4);

        personsStation1 = new ArrayList<>();
        personsStation1.add(person5);
        personsStation1.add(person6);

        personsStation2 = new ArrayList<>();
        personsStation2.add(person7);



        personInfoDTO = new PersonInfoDTO();
        personInfoDTO.setFirstname(person.getFirstName());
        personInfoDTO.setLastname(person.getLastName());
        personInfoDTO.setPhone(person.getPhone());
        personInfoDTO.setAddresse(person.getAddress());

        personInfoDTO1 = new PersonInfoDTO();
        personInfoDTO1.setFirstname(person1.getFirstName());
        personInfoDTO1.setLastname(person1.getLastName());
        personInfoDTO1.setPhone(person1.getPhone());
        personInfoDTO1.setAddresse(person1.getAddress());

        personInfoDTO2 = new PersonInfoDTO();
        personInfoDTO2.setFirstname(person2.getFirstName());
        personInfoDTO2.setLastname(person2.getLastName());
        personInfoDTO2.setPhone(person2.getPhone());
        personInfoDTO2.setAddresse(person2.getAddress());

        personInfoDTO3 = new PersonInfoDTO();
        personInfoDTO3.setFirstname(person3.getFirstName());
        personInfoDTO3.setLastname(person3.getLastName());
        personInfoDTO3.setPhone(person3.getPhone());
        personInfoDTO3.setAddresse(person3.getAddress());

        personInfoDTO4 = new PersonInfoDTO();
        personInfoDTO4.setFirstname(person4.getFirstName());
        personInfoDTO4.setLastname(person4.getLastName());
        personInfoDTO4.setPhone(person4.getPhone());
        personInfoDTO4.setAddresse(person4.getAddress());

        personsInfoDTO = new ArrayList<>();
        personsInfoDTO.add(personInfoDTO);
        personsInfoDTO.add(personInfoDTO1);
        personsInfoDTO.add(personInfoDTO2);
        personsInfoDTO.add(personInfoDTO3);
        personsInfoDTO.add(personInfoDTO4);

        personsCoveredByStation = new PersonsCoveredByStation();
        personsCoveredByStation.setStationId(1);
        personsCoveredByStation.setPersons(personsInfoDTO);
        personsCoveredByStation.setNombreAdults(2);
        personsCoveredByStation.setNombreEnfants(2);

        nonExistingFirestation = Optional.empty();

        personAtRueDako = new ArrayList<>();
        personAtRueDako.add(person1);
        personAtRueDako.add(person2);
        personAtRueDako.add(person3);
        personAtRueDako.add(person4);



        //utilsMock.when(()->Utils.calculateAge(medicalRecordPerson1.getBirthdate())).thenReturn(21);
        //when(personRepository.findByAddress("28 rue dako")).thenReturn(persons);





    }

    @Test
    public void given_a_firestation_id_should_return_persons_covered(){
        when(firestationRepository.findById(1)).thenReturn(Optional.of(station));
        when(firestationRepository.findByStation(1)).thenReturn(Optional.of(stations));
        //when(firestationRepository.findByAddress("28 rue dako")).thenReturn(Optional.of(stations));
        when(personRepository.findByAddress("28 rue dako")).thenReturn(personAtRueDako);
        when(medicalRecordRepository.findByLastnameAndFirstname(anyString(),anyString())).thenReturn(medicalRecordPerson,
                medicalRecordPerson1, medicalRecordPerson2, medicalRecordPerson3, medicalRecordPerson4);
        //doReturn(medicalRecordPerson).when(medicalRecordRepository.findByLastnameAndFirstname("Dossou","Marc"));
        //utilsMock.when(()->Utils.calculateAge(medicalRecordPerson.getBirthdate())).thenReturn(12);
        //doReturn(medicalRecordPerson1).when(medicalRecordRepository.findByLastnameAndFirstname("Djigla","Fiacre"));
        //doReturn(medicalRecordPerson2).when(medicalRecordRepository.findByLastnameAndFirstname("Inoussa","Radji"));
        //utilsMock.when(()->Utils.calculateAge(medicalRecordPerson2.getBirthdate())).thenReturn(23);
        //doReturn(medicalRecordPerson3).when(medicalRecordRepository.findByLastnameAndFirstname("Idohou","Fifa"));
        //utilsMock.when(()->Utils.calculateAge(medicalRecordPerson3.getBirthdate())).thenReturn(10);
        //doReturn(medicalRecordPerson4).when(medicalRecordRepository.findByLastnameAndFirstname("Lokossou","René"));
        //utilsMock.when(()->Utils.calculateAge(medicalRecordPerson4.getBirthdate())).thenReturn(10);

        PersonsCoveredByStation personsCovered = commonService.produceStationCoverage(1);
        //assertThat(personsCovered.getNombreAdults()).isEqualTo(2);
        //assertThat(personsCovered.getNombreEnfants()).isEqualTo(2);
        assertThat(personsCovered.getPersons()).hasSize(4);

    }

    @Test
    public void given_a_non_existing_firestation_id_should_raise_exception(){
        when(firestationRepository.findById(1)).thenReturn(nonExistingFirestation);
        Assertions.assertThrows(FirestationNotFoundException.class, () -> commonService.produceStationCoverage(1));
    }

    @Test
    public void given_an_address_should_return_children_covered_at_the_address(){
        when(firestationRepository.findByAddress("28 rue dako")).thenReturn(Optional.of(stations));
        when(personRepository.findByAddress("28 rue dako")).thenReturn(personAtRueDako);
        when(medicalRecordRepository.findByLastnameAndFirstname(anyString(),anyString())).thenReturn(medicalRecordPerson,
                medicalRecordPerson1, medicalRecordPerson2, medicalRecordPerson3, medicalRecordPerson4);
        //willReturn(medicalRecordPerson4).given(medicalRecordRepository.findByLastnameAndFirstname("Lokossou","René"));
        //utilsMock.when(()->Utils.calculateAge(medicalRecordPerson4.getBirthdate())).thenReturn(10);
        ChildrenCoveredDTO childrenCovered = commonService.produceChildrenAtAddress("28 rue dako");
        assertThat(childrenCovered.getEnfants()).hasSize(2);
        assertThat(childrenCovered.getAutresMembres()).hasSize(2);
        assertTrue(childrenCovered.getEnfants().get(0).getFirstname().equalsIgnoreCase("Fiacre"));
        assertTrue(childrenCovered.getEnfants().get(1).getFirstname().equalsIgnoreCase("René"));
    }

    @Test
    public void given_a_non_existing_station_address_should_raise_exception(){
        when(firestationRepository.findByAddress("28 rue dako")).thenReturn(Optional.empty());
        Assertions.assertThrows(FirestationNotFoundException.class, () -> commonService.produceChildrenAtAddress("28 rue dako"));
    }

    @Test
    public void given_a_station_id_should_return_phone_numbers(){
        List<String> phones = new ArrayList<>();
        phones.add(person.getPhone());
        phones.add(person1.getPhone());
        phones.add(person2.getPhone());
        phones.add(person3.getPhone());
        phones.add(person.getPhone());
        phones.add(person4.getPhone());
        when(firestationRepository.findById(1)).thenReturn(Optional.of(station));
        when(personRepository.findByAddressAndSelectPhone("28 rue dako")).thenReturn(phones);
        List<String> phonesPersons = commonService.getResidentPhoneNumber(1);
        assertThat(phonesPersons.size()).isSameAs(phones.size());
        assertThat(phonesPersons).contains(person.getPhone());
    }

    public void given_a_non_station_id_should_return_raise_exception(){
        when(firestationRepository.findByStation(1)).thenReturn(null);
        Assertions.assertThrows(FirestationNotFoundException.class, () -> commonService.getResidentPhoneNumber(1));
    }

    @Test
    public void given_a_city_should_return_community_emails(){
        when(personRepository.findAll()).thenReturn(persons);
        List<String> portoCommunity = commonService.getCommunityEmails("Porto-Novo");
        assertThat(portoCommunity).contains("fidohou@yahoo.fr","rlokossou@yahoo.fr");
    }

    @Test
    public void given_a_lastname_and_firstname_should_return_person_medical_details(){
        when(personRepository.findAllByLastNameAndFirstName(anyString(),anyString())).thenReturn(persons);
        when(medicalRecordRepository.findByLastnameAndFirstname(anyString(),anyString())).thenReturn(medicalRecordPerson,
                medicalRecordPerson1, medicalRecordPerson2, medicalRecordPerson3, medicalRecordPerson4);
        List<PersonMedicalDetailsDTO> personMedicalDetailsDTOS = commonService.getPersonMedicalDetails("Dossou","Marc");
        assertThat(personMedicalDetailsDTOS).hasSize(4);
        assertThat(personMedicalDetailsDTOS.get(0).getFirstname().equalsIgnoreCase("Marc"));
        assertThat(personMedicalDetailsDTOS.get(0).getLastname().equalsIgnoreCase("Dossou"));

    }

    @Test
    public void given_a_list_of_stations_should_return_persons_covered(){
        List<String> lesStations = new ArrayList<>();
        lesStations.add("2");
        lesStations.add("3");
        Optional<List<Firestation>> firestations = Optional.of(stations);
        when(firestationRepository.findByStation(anyInt())).thenReturn(firestations);
        when(personRepository.findByAddress(anyString())).thenReturn(personsStation1, personsStation2);
        when(medicalRecordRepository.findByLastnameAndFirstname(anyString(),anyString())).thenReturn(medicalRecordPerson5,medicalRecordPerson6,medicalRecordPerson7);
        List<PersonsCoveredAtAddress>  personsCoveredAtAddresses = commonService.getAddressesCoverage(lesStations);
        assertThat(personsCoveredAtAddresses).hasSize(6);
    }

    @Test
    public void given_an_address_should_return_persons_covered(){
        when(personRepository.findByAddress("28 rue dako")).thenReturn(personAtRueDako);
        when(medicalRecordRepository.findByLastnameAndFirstname(anyString(),anyString())).thenReturn(medicalRecordPerson,
                medicalRecordPerson1, medicalRecordPerson2, medicalRecordPerson3, medicalRecordPerson4);

        PersonsCoveredAtAddress personsCovered = commonService.getAddressCoverage("28 rue dako");
        assertThat(personsCovered.getPersonsCovered()).hasSize(4);

    }


}
