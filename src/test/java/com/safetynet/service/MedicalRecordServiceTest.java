package com.safetynet.service;

import com.safetynet.exception.MedicalRecordInvalidException;
import com.safetynet.exception.MedicalRecordNotFoundException;
import com.safetynet.model.MedicalRecord;
import com.safetynet.repository.MedicalRecordRepository;
import com.safetynet.repository.MedicalRecordRepositoryImpl;
import org.junit.jupiter.api.Assertions;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class MedicalRecordServiceTest {
    @Mock
    private MedicalRecordRepositoryImpl medicalRecordRepository;
    @InjectMocks
    private MedicalRecordServiceImpl medicalRecordService;
    protected MedicalRecord medicalRecord, invalidFirstnameMedicalRecord, nonExistingMedicalRecord,
            invalidLastnameMedicalRecord, medicalRecord1, medicalRecord2, nullMedicalRecord;
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
        LocalDate d1 = LocalDate.of(1997,4,4);
        medicalRecord1 = new MedicalRecord(1,"Reine","Fanou",d1,medications1,allergies1);

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

        List<String> medications3 = new ArrayList<>();
        medications3.add("zetiol:344mg");
        medications3.add("uvitol:500mg");
        List<String> allergies3 = new ArrayList<>();
        allergies3.add("pentol");
        LocalDate d3 = LocalDate.of(2000,11,3);
        invalidFirstnameMedicalRecord = new MedicalRecord(4,"Paul","",d3,medications3,allergies3);

        List<String> medications4 = new ArrayList<>();
        medications4.add("zetiol:344mg");
        medications4.add("uvitol:500mg");
        List<String> allergies4 = new ArrayList<>();
        allergies4.add("pentol");
        LocalDate d4 = LocalDate.of(2000,11,3);
        invalidLastnameMedicalRecord = new MedicalRecord(5,"","Valentin",d4,medications4,allergies4);

        List<String> medications5 = new ArrayList<>();
        medications5.add("zetiol:344mg");
        medications5.add("uvitol:500mg");
        List<String> allergies5 = new ArrayList<>();
        allergies5.add("pentol");
        LocalDate d5 = LocalDate.of(1987,10,5);
        nonExistingMedicalRecord = new MedicalRecord(6,"Abou","Malick",d5,medications5,allergies5);
    }

    /**
     * given_a_valid_medicalrecord_save_should_persist_medicalrecord -  test methode save: cas norminal
     * @throws Exception
     */
    @Test
    public void given_a_valid_medicalrecord_save_should_persist_medicalrecord() throws Exception{
        when(medicalRecordRepository.save(any(MedicalRecord.class))).thenReturn(medicalRecord);
        MedicalRecord savedMedicalRecord = medicalRecordService.save(medicalRecord);
        verify(medicalRecordRepository, times(1)).save(any(MedicalRecord.class));
        assertThat(savedMedicalRecord).isNotNull();
    }


    /**
     * given_a_invalid_lastname_medicalrecord_save_should_raise_exception - save : cas exception
     * @throws Exception
     */
    @Test
    public void given_a_invalid_lastname_medicalrecord_save_should_raise_exception() throws Exception{
        Assertions.assertThrows(MedicalRecordInvalidException.class, () -> {
            medicalRecordService.save(invalidLastnameMedicalRecord);
        });
    }

    /**
     * given_a_invalid_firstname_medicalrecord_save_should_raise_exception - save : cas exception
     * @throws Exception
     */
    @Test
    public void given_a_invalid_firstname_medicalrecord_save_should_raise_exception() throws Exception{
        Assertions.assertThrows(MedicalRecordInvalidException.class, () -> {
            medicalRecordService.save(invalidFirstnameMedicalRecord);
        });
    }

    /**
     * given_a_null_medicalrecord_update_should_raise_exception - update: null medicalrecord
     * @throws Exception
     */
    @Test
    public void given_a_null_medicalrecord_update_should_raise_exception() throws Exception{
        Assertions.assertThrows(MedicalRecordInvalidException.class, () -> {
            medicalRecordService.update(nullMedicalRecord);
        });
    }

    /**
     * given_an_invalid_lastname_medicalrecord_update_should_raise_exception - update: invalid lastname
     * @throws Exception
     */
    @Test
    public void given_an_invalid_lastname_medicalrecord_update_should_raise_exception() throws Exception{
        Assertions.assertThrows(MedicalRecordInvalidException.class, () -> {
            medicalRecordService.update(invalidLastnameMedicalRecord);
        });
    }

    /**
     * given_an_invalid_firstname_medicalrecord_update_should_raise_exception - update : invalid firstname
     * @throws Exception
     */
    @Test
    public void given_an_invalid_firstname_medicalrecord_update_should_raise_exception() throws Exception{
        Assertions.assertThrows(MedicalRecordInvalidException.class, () -> {
            medicalRecordService.update(invalidFirstnameMedicalRecord);
        });
    }

    @Test
    public void given_a_non_existing_medicalrecord_update_should_raise_exception() throws Exception{
        when(medicalRecordRepository.findByLastnameAndFirstname("Abou","Malick")).thenReturn(null);
        Assertions.assertThrows(MedicalRecordNotFoundException.class, () -> {
            medicalRecordService.update(nonExistingMedicalRecord
            );
        });
    }

    /**
     * given_a_medicalrecord_update_should_save_changes - update : cas nominal
     * @throws Exception
     */
    @Test
    public void given_a_medicalrecord_update_should_save_changes() throws Exception{
        when(medicalRecordRepository.findByLastnameAndFirstname("Bignon","Kossou")).thenReturn(medicalRecord2);
        when(medicalRecordRepository.save(any())).thenReturn(medicalRecord2);
        MedicalRecord updatedMedicalRecord = medicalRecordService.update(medicalRecord2);
        verify(medicalRecordRepository).save(any(MedicalRecord.class));
        assertThat(updatedMedicalRecord).isNotNull();
        assertThat(updatedMedicalRecord.getAllergies()).isSameAs(medicalRecord2.getAllergies());
        assertThat(updatedMedicalRecord.getMedications()).isSameAs(medicalRecord2.getMedications());
        assertThat(updatedMedicalRecord.getBirthdate()).isSameAs(medicalRecord2.getBirthdate());

    }


    /**
     * find_should_return_all_medicalrecords - findall
     * @throws Exception
     */
    @Test
    public void find_should_return_all_medicalrecords() throws Exception{
        when(medicalRecordRepository.findAll()).thenReturn(medicalRecords);
        List<MedicalRecord> allMedicalRecords = medicalRecordService.findAll();
        verify(medicalRecordRepository, times(1)).findAll();
        assertThat(allMedicalRecords).hasSize(3);
    }

    /**
     * given_lastname_and_firstname_delete_should_remove_medical_record - delete
     * @throws Exception
     */
    @Test
    public void given_lastname_and_firstname_delete_should_remove_medical_record() throws Exception{
        //when(medicalRecordRepository.findAll()).thenReturn(medicalRecords);
        when(medicalRecordRepository.findByLastnameAndFirstname("Paul","Tossou")).thenReturn(medicalRecord);
        medicalRecordService.delete("Paul","Tossou");
        verify(medicalRecordRepository, times(1)).delete(any());
        //assertThat(medicalRecords).hasSize(2);
    }

    /**
     * given_a_non_existing_medicalrecord_delete_should_raise_exception - delete : non existing medicalrecord
     * @throws Exception
     */
    @Test
    public void given_a_non_existing_medicalrecord_delete_should_raise_exception() throws Exception{
        when(medicalRecordRepository.findByLastnameAndFirstname("Abou","Malick")).thenReturn(null);
        Assertions.assertThrows(MedicalRecordNotFoundException.class, () -> {
            medicalRecordService.delete("Abou","Malick");
        });
    }

    @Test
    public void given_lastname_and_firstname_findbylastnameandfirstname_should_return_medical_record() throws Exception{
        when(medicalRecordRepository.findByLastnameAndFirstname("Paul","Tossou")).thenReturn(medicalRecord);
        MedicalRecord foundMedicalRecord = medicalRecordService.findByLastnameAndFirstname("Paul", "Tossou");
        assertThat(foundMedicalRecord.getFirstName()).isSameAs(medicalRecord.getFirstName());
    }

    /**
     * given_a_non_existing_medicalrecord_findbylastnameandfirstname_should_raise_exception - findbylastnameandfirstname : non existing medicalrecord
     * @throws Exception
     */
    @Test
    public void given_a_non_existing_medicalrecord_findbylastnameandfirstname_should_raise_exception() throws Exception{
        when(medicalRecordRepository.findByLastnameAndFirstname("Abou","Malick")).thenReturn(null);
        Assertions.assertThrows(MedicalRecordNotFoundException.class, () -> {
            medicalRecordService.findByLastnameAndFirstname("Abou","Malick");
        });
    }
}
