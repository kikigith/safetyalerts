package com.safetynet.repository;

import com.safetynet.model.Firestation;
import com.safetynet.model.MedicalRecord;
import com.safetynet.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MedicalRecordRepositoryImpl implements MedicalRecordRepository{
    @Autowired
    DataSourceComponent dataSourceComponent;
    @Override
    public List<MedicalRecord> findAll() {
        return dataSourceComponent.getMedicalrecords();
    }

    @Override
    public MedicalRecord save(MedicalRecord medicalRecord) {
        List<MedicalRecord> medicalRecords = dataSourceComponent.getMedicalrecords();
        MedicalRecord aMedicalRecord = findByLastnameAndFirstname(medicalRecord.getLastName(),medicalRecord.getFirstName());
        if(aMedicalRecord != null ){//Update
            int index = medicalRecords.indexOf(aMedicalRecord);
            medicalRecords.remove(aMedicalRecord);
            medicalRecords.add(index, medicalRecord);
        }else{//Add new
            medicalRecords.add(medicalRecord);
        }
        return medicalRecord;
    }

    @Override
    public void delete(MedicalRecord medicalRecord) {
        List<MedicalRecord> medicalRecords = dataSourceComponent.getMedicalrecords();
        medicalRecords.remove(medicalRecord);

    }

    @Override
    public MedicalRecord findByLastnameAndFirstname(String lastname, String firstname) {
        for (MedicalRecord medicalRecord : dataSourceComponent.getMedicalrecords()) {
            if (medicalRecord.getLastName().equalsIgnoreCase(lastname) && medicalRecord.getFirstName().equalsIgnoreCase(firstname))
                return medicalRecord;
        }
        return null;
    }
}
