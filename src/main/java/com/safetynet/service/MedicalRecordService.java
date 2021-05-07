package com.safetynet.service;

import com.safetynet.exception.MedicalRecordInvalidException;
import com.safetynet.exception.MedicalRecordNotFoundException;
import com.safetynet.model.MedicalRecord;

import java.util.List;

public interface MedicalRecordService {
    List<MedicalRecord> findAll();
    MedicalRecord save(MedicalRecord medicalRecord) throws MedicalRecordInvalidException;
    void delete(String lastname, String firstname) throws MedicalRecordNotFoundException;
    MedicalRecord update(MedicalRecord medicalRecord) throws MedicalRecordInvalidException, MedicalRecordNotFoundException;
    MedicalRecord findByLastnameAndFirstname(String lastname, String firstname) throws MedicalRecordNotFoundException;
}
