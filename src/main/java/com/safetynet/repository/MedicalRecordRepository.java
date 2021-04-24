package com.safetynet.repository;

import com.safetynet.model.MedicalRecord;

import java.util.List;

public interface MedicalRecordRepository {
    List<MedicalRecord> findAll();
    MedicalRecord save(MedicalRecord medicalRecord);
    void delete(MedicalRecord medicalRecord);
    MedicalRecord findByLastnameAndFirstname(String lastname, String firstname);
}
