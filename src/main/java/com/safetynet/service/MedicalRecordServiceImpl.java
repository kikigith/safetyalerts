package com.safetynet.service;

import com.safetynet.exception.FirestationInvalidException;
import com.safetynet.exception.FirestationNotFoundException;
import com.safetynet.exception.MedicalRecordInvalidException;
import com.safetynet.exception.MedicalRecordNotFoundException;
import com.safetynet.model.Firestation;
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
    public MedicalRecord save(MedicalRecord medicalRecord) throws MedicalRecordInvalidException {
        logger.info("Saving medicalrecord lastname: "+medicalRecord.getLastName()+" firstname: "+medicalRecord.getFirstName());
        if(medicalRecord.getLastName() == null || medicalRecord.getLastName().isEmpty())
            throw new MedicalRecordInvalidException("Le champ prénom est obligatoire");
        if(medicalRecord.getFirstName() == null || medicalRecord.getFirstName().isEmpty())
            throw new MedicalRecordInvalidException("Le champ nom est obligatoire");
        return medicalRecordRepository.save(medicalRecord);
    }

    @Override
    public MedicalRecord update(MedicalRecord medicalRecord) throws MedicalRecordInvalidException, MedicalRecordNotFoundException {
        if(medicalRecord == null )
            throw new MedicalRecordInvalidException(" Vous devez spécifier le medical record à mettre à jour");
        if( medicalRecord.getLastName().isEmpty() || medicalRecord.getLastName().isBlank())
            throw new MedicalRecordInvalidException(" Vous devez spécifier le prénom du medical record à mettre à jour");
        if( medicalRecord.getFirstName().isEmpty() || medicalRecord.getFirstName().isBlank())
            throw new MedicalRecordInvalidException(" Vous devez spécifier le nom du medical record à mettre à jour");
        logger.info("Updating  MedicalRecord lastname: '" + medicalRecord.getLastName() + "' nom: '" + medicalRecord.getFirstName());
        MedicalRecord foundMedicalRecordToUpdate = medicalRecordRepository.findByLastnameAndFirstname(medicalRecord.getLastName(),medicalRecord.getFirstName());
        if(foundMedicalRecordToUpdate == null)
            throw new MedicalRecordNotFoundException("Le MedicalRecord de prénom: " + medicalRecord.getLastName()+ ", nom: " +medicalRecord.getFirstName()+ " n'existe pas");
        //foundMedicalRecordToUpdate.setLastName(medicalRecord.getLastName());
        //foundMedicalRecordToUpdate.setFirstName(medicalRecord.getFirstName());
        foundMedicalRecordToUpdate.setBirthdate(medicalRecord.getBirthdate());
        foundMedicalRecordToUpdate.setAllergies(medicalRecord.getAllergies());
        foundMedicalRecordToUpdate.setMedications(medicalRecord.getMedications());
        logger.info("MedicalRecord [" + medicalRecord+ "] updated successfully");
        return medicalRecordRepository.save(foundMedicalRecordToUpdate);
    }

    @Override
    public void delete(String lastname, String firstname) throws MedicalRecordNotFoundException {
        logger.info("Requête ==> Suppression medicalrecord prénom: "+lastname+" nom: "+firstname);
        MedicalRecord medicalRecord = medicalRecordRepository.findByLastnameAndFirstname(lastname,firstname);
        if(medicalRecord == null)
            throw new MedicalRecordNotFoundException("Le MedicalRecord prénom: " +lastname+ " nom: "+firstname+ " n'existe pas ");
        medicalRecordRepository.delete(medicalRecord);
        logger.info("medicalrecord prénom: "+lastname+" nom: "+firstname+" supprimé avec succès");
    }



    @Override
    public MedicalRecord findByLastnameAndFirstname(String lastname, String firstname) throws MedicalRecordNotFoundException {
        logger.info("Requête=>Recherche medicalrecord nom: "+lastname+" prénom: "+firstname);
        MedicalRecord foundMedicalRecord = medicalRecordRepository.findByLastnameAndFirstname(lastname,firstname);
        if(foundMedicalRecord == null){
            throw new MedicalRecordNotFoundException("Aucun medicalrecord  nom : "+lastname+" prénom: "+firstname+ "n'existe ");
        }
        logger.info("Réponse ==> medicalrecord : "+foundMedicalRecord.toString());
        return foundMedicalRecord;
    }
}
