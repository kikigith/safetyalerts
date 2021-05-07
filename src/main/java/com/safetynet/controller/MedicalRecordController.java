package com.safetynet.controller;

import com.safetynet.model.MedicalRecord;
import com.safetynet.service.MedicalRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class MedicalRecordController {
    private final Logger logger = LoggerFactory.getLogger(MedicalRecordController.class);

    @Autowired
    private MedicalRecordService medicalRecordService;

    /**
     * getMedicalRecords - Get all medicalrecords from the repository
     * @return - an iterable list
     */
    @GetMapping("/medicalrecords")
    public ResponseEntity<List<MedicalRecord>> getMedicalRecords(){
        logger.info("Requête => afficher tous les medicalrecord");
        List<MedicalRecord> medicalRecords;
        medicalRecords = medicalRecordService.findAll();
        logger.info("Réponse => les medicalrecord" +medicalRecords);
        return new ResponseEntity<>(medicalRecords,HttpStatus.OK);
    }

    /**
     * saveMedicalRecord - Create a new MedicalRecord in the repository
     *
     * @return - a new instance of MedicalRecord
     */
    @PostMapping("/medicalrecord")
    public ResponseEntity<MedicalRecord> saveMedicalRecord(@RequestBody MedicalRecord medicalRecord){
        logger.info("Requête => @RequestBody = {}", medicalRecord);
        if(medicalRecord.getLastName().isEmpty() || medicalRecord.getLastName().isBlank()
           || medicalRecord.getFirstName().isEmpty() || medicalRecord.getFirstName().isBlank())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        MedicalRecord persistedMedicalRecord = medicalRecordService.save(medicalRecord);

        return ResponseEntity
                .created(URI.create(String.format("/medicalrecord?lastname=" + medicalRecord.getLastName() + "&firstname=" + medicalRecord.getFirstName())))
                .body(persistedMedicalRecord);
    }

    /**
     * updateMedicalRecord  - update an existing medicalrecord
     * @return
     */
    @PutMapping("/medicalrecord")
    public ResponseEntity<MedicalRecord> updateMedicalRecord(@RequestBody MedicalRecord medicalRecord){
        logger.info("Request = @RequestBody = {}", medicalRecord);
        if(medicalRecord.getLastName().isEmpty() || medicalRecord.getLastName().isBlank() ||
                medicalRecord.getFirstName().isEmpty() || medicalRecord.getFirstName().isBlank())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        MedicalRecord updatedMedicalRecord = medicalRecordService.update(medicalRecord);
        if(updatedMedicalRecord == null )
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        logger.error("Réponse = @ResponseBody = {} " +updatedMedicalRecord);

        return ResponseEntity.accepted().body(updatedMedicalRecord);
    }

    /**
     *  searchMedicalRecord - Search a MedicalRecord in the repository by firstName & lastName
     * @param lastname
     * @param firstname
     * @return
     * @throws Exception
     */
    @GetMapping("/medicalrecord")
    public ResponseEntity<MedicalRecord> searchMedicalRecord(@RequestParam("lastname") final String lastname,
                                                             @RequestParam("firstname") final String firstname) {
        logger.info("Request = recherche medical record avec prénom : " + lastname+ ", nom: "+ firstname);
        if(lastname.isEmpty() || lastname.isBlank() ||
                firstname.isEmpty() || firstname.isBlank() )
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        MedicalRecord foundMedicalRecord = medicalRecordService.findByLastnameAndFirstname(lastname, firstname);
        if(foundMedicalRecord == null )
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        logger.error("Réponse MedicalRecord : " +foundMedicalRecord);
        return new ResponseEntity<MedicalRecord>(foundMedicalRecord,HttpStatus.OK);
    }

    /**
     * Delete - Delete a medicalrecord
     * @param lastname
     * @param firstname
     * @return
     */
    @DeleteMapping("/medicalrecord")
    public ResponseEntity<MedicalRecord> deleteMedicalRecord(@RequestParam("lastname") final String lastname,
                                                             @RequestParam("firstname") final String firstname) {
        logger.info("Request Delete medicalrecord firstname {}, lastname{}", firstname, lastname);
        ResponseEntity<MedicalRecord> response = null;
        medicalRecordService.delete(lastname, firstname);
        return new ResponseEntity<MedicalRecord>(HttpStatus.ACCEPTED);
    }
}
