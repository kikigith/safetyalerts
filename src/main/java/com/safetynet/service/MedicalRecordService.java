package com.safetynet.service;

import com.safetynet.exception.MedicalRecordInvalidException;
import com.safetynet.exception.MedicalRecordNotFoundException;
import com.safetynet.model.MedicalRecord;

import java.util.List;

public interface MedicalRecordService {
    List<MedicalRecord> findAll();
    MedicalRecord save(MedicalRecord medicalRecord) throws MedicalRecordInvalidException, Exception;
    void delete(String lastname, String firstname) throws MedicalRecordNotFoundException, Exception;
    MedicalRecord findByLastnameAndFirstname(String lastname, String firstname) throws MedicalRecordNotFoundException, Exception;
}
