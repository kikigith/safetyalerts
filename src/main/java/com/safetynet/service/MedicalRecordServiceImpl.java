package com.safetynet.service;

import com.safetynet.exception.MedicalRecordInvalidException;
import com.safetynet.exception.MedicalRecordNotFoundException;
import com.safetynet.model.MedicalRecord;
import com.safetynet.repository.MedicalRecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordServiceImpl implements MedicalRecordService{
    private final Logger logger = LoggerFactory.getLogger(MedicalRecordService.class);
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Override
    public List<MedicalRecord> findAll() {
        logger.info("Loading medicalrecords");
        return medicalRecordRepository.findAll();
    }

    @Override
    public MedicalRecord save(MedicalRecord medicalRecord) throws MedicalRecordInvalidException, Exception {
        logger.info("Saving medicalrecord lastname: "+medicalRecord.getLastName()+" firstname: "+medicalRecord.getFirstName());
        if(medicalRecord.getLastName() == null || medicalRecord.getLastName().isEmpty())
            throw new MedicalRecordInvalidException("Le champ prénom est obligatoire");
        if(medicalRecord.getFirstName() == null || medicalRecord.getFirstName().isEmpty())
            throw new MedicalRecordInvalidException("Le champ nom est obligatoire");
        return medicalRecordRepository.save(medicalRecord);
    }

    @Override
    public void delete(String lastname, String firstname) throws Exception {
        logger.info("Requête ==> Suppression medicalrecord prénom: "+lastname+" nom: "+firstname);
        MedicalRecord medicalRecord = medicalRecordRepository.findByLastnameAndFirstname(lastname,firstname);
        if(medicalRecord == null) return;
        medicalRecordRepository.delete(medicalRecord);
        logger.info("medicalrecord prénom: "+lastname+" nom: "+firstname+" supprimé avec succès");
    }

    @Override
    public MedicalRecord findByLastnameAndFirstname(String lastname, String firstname) throws MedicalRecordNotFoundException, Exception {
        logger.info("Requête=>Recherche medicalrecord nom: "+lastname+" prénom: "+firstname);
        MedicalRecord foundMedicalRecord = medicalRecordRepository.findByLastnameAndFirstname(lastname,firstname);
        if(foundMedicalRecord == null){
            throw new MedicalRecordNotFoundException("Aucun medicalrecord  nom : "+lastname+" prénom: "+firstname+ "n'existe ");
        }
        logger.info("Réponse ==> medicalrecord : "+foundMedicalRecord.toString());
        return foundMedicalRecord;
    }
}
