package com.safetynet.repository;

import com.safetynet.model.MedicalRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MedicalRecordRepositoryTest {
    @Mock
    DataSourceComponent dataSourceComponent;
    @InjectMocks
    private MedicalRecordRepositoryImpl medicalRecordRepository;
    protected MedicalRecord medicalRecord, medicalRecord1, medicalRecord2;
    private List<MedicalRecord> medicalRecords;

    @BeforeEach
    public void initTests(){

        List<String> medications = new ArrayList<>();
        medications.add("zetiol:344mg");
        medications.add("uvitol:500mg");
        List<String> allergies = new ArrayList<>();
        allergies.add("pentol");
        LocalDate d = LocalDate.of(2000,11,3);
        medicalRecord = new MedicalRecord(1,"Paul","Tossou",d,medications,allergies);

        List<String> medications1 = new ArrayList<>();
        medications1.add("feneol:300mg");
        medications1.add("bartim:500mg");
        List<String> allergies1 = new ArrayList<>();
        allergies1.add("zentol");
        LocalDate d1 = LocalDate.of(1997,4,9);
        medicalRecord1 = new MedicalRecord(2,"Reine","Fanou",d1,medications1,allergies1);

        Calendar calendar2 = new GregorianCalendar(2003, 11, 19);
        List<String> medications2 = new ArrayList<>();
        medications2.add("outrenol:200mg");
        medications2.add("albiotol:500mg");
        List<String> allergies2 = new ArrayList<>();
        allergies2.add("pentenol");
        LocalDate d2 = LocalDate.of(2003,11,19);
        medicalRecord2 = new MedicalRecord(3,"Bignon","Kossou",d2,medications2,allergies2);

        medicalRecords = new ArrayList<>();
        medicalRecords.add(medicalRecord);
        medicalRecords.add(medicalRecord1);
        medicalRecords.add(medicalRecord2);
    }

    @Test
    public void findall_should_return_all_medicalrecords() throws Exception{
        when(dataSourceComponent.getMedicalrecords()).thenReturn(medicalRecords);
        List<MedicalRecord> allMedicalRecords = medicalRecordRepository.findAll();
        assertThat(allMedicalRecords).hasSize(3);
    }

    @Test
    public void given_a_medicalrecord_save_should_persist_the_medicalrecord() throws Exception{
        List<String> medications2 = new ArrayList<>();
        medications2.add("xetenol:200mg");
        medications2.add("biopatim:500mg");
        List<String> allergies2 = new ArrayList<>();
        allergies2.add("xitrine");
        allergies2.add("avamine");
        LocalDate d2 = LocalDate.of(2008,10,12);
        MedicalRecord newMedicalRecord = new MedicalRecord(1,"Florent","Idohou",d2,medications2,allergies2);
        when(dataSourceComponent.getMedicalrecords()).thenReturn(medicalRecords);
        MedicalRecord savedMedicalRecord = medicalRecordRepository.save(newMedicalRecord);
        assertThat(medicalRecords).hasSize(4);
        assertThat(medicalRecords.get(3).getFirstName()).isEqualTo("Idohou");
    }

    @Test
    public void given_an_existing_medicalrecord_save_should_update_the_medicalrecord() throws  Exception{

        List<String> medications1 = new ArrayList<>();
        medications1.add("feneol:300mg");
        medications1.add("bartim:500mg");
        List<String> allergies1 = new ArrayList<>();
        allergies1.add("carotène");
        LocalDate d = LocalDate.of(1997,4,9);
        MedicalRecord newMedicalRecord = new MedicalRecord(1,"Bignon","Kossou",d,medications1,allergies1);
        when(dataSourceComponent.getMedicalrecords()).thenReturn(medicalRecords);
        assertThat(medicalRecords.get(2).getAllergies().get(0)).isSameAs("pentenol");
        MedicalRecord updatedMedicalRecord = medicalRecordRepository.save(newMedicalRecord);
        assertThat(medicalRecords.get(2).getAllergies().get(0)).isSameAs("carotène");
    }

    @Test
    public void given_a_medicalrecord_should_be_delete_from_source() throws Exception{
        when(dataSourceComponent.getMedicalrecords()).thenReturn(medicalRecords);
        assertThat(medicalRecords).hasSize(3);
        assertThat(medicalRecord).isIn(medicalRecords);
        medicalRecordRepository.delete(medicalRecord);
        assertThat(medicalRecords).hasSize(2);
        assertThat(medicalRecord).isNotIn(medicalRecords);
    }

    @Test
    public void given_a_lastname_and_firstname_should_return_a_medicalrecord() throws Exception{
        when(dataSourceComponent.getMedicalrecords()).thenReturn(medicalRecords);
        MedicalRecord foundMedicalRecord = medicalRecordRepository.findByLastnameAndFirstname("Paul","Tossou");
        assertThat(foundMedicalRecord).isSameAs(medicalRecord);
    }
}
