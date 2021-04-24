package com.safetynet.controller;

import com.safetynet.exception.MedicalRecordInvalidException;
import com.safetynet.exception.MedicalRecordNotFoundException;
import com.safetynet.model.Firestation;
import com.safetynet.model.MedicalRecord;
import com.safetynet.service.MedicalRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
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
    public List<MedicalRecord> getMedicalRecords(){
        return medicalRecordService.findAll();
    }

    /**
     * saveMedicalRecord - Create a new MedicalRecord in the repository
     *
     * @return - a new instance of MedicalRecord
     */
    @PostMapping("/medicalrecord")
    public ResponseEntity<MedicalRecord> saveMedicalRecord(@RequestParam("lastname") final String lastname,
                                                           @RequestParam("firstname") final String firstname,
                                                           @RequestBody MedicalRecord medicalRecord)
                                            throws MedicalRecordNotFoundException, Exception{
        MedicalRecord persistedMedicalRecord = null;
        persistedMedicalRecord = medicalRecordService.save(medicalRecord);

        return ResponseEntity
                .created(URI.create(String.format("/medicalRecord?lastname=" + lastname + "&firstname=" + firstname)))
                .body(persistedMedicalRecord);
    }

    /**
     *  searchMedicalRecord - Search a MedicalRecord in the repository by firstName & lastName
     * @param lastname
     * @param firstname
     * @return
     * @throws MedicalRecordInvalidException
     * @throws MedicalRecordNotFoundException
     * @throws Exception
     */
    @GetMapping("/medicalrecord")
    public ResponseEntity<MedicalRecord> searchMedicalRecord(@RequestParam("lastname") final String lastname,
                                                             @RequestParam("firstname") final String firstname)
                                                            throws MedicalRecordInvalidException, MedicalRecordNotFoundException,Exception {
        MedicalRecord foundMedicalRecord = null;
        foundMedicalRecord = medicalRecordService.findByLastnameAndFirstname(lastname, firstname);
        return new ResponseEntity<MedicalRecord>(foundMedicalRecord,HttpStatus.OK);
    }

    @PutMapping("/medicalrecord")
    public ResponseEntity<MedicalRecord> updateMedicalRecord(@RequestParam("lastname") final String lastname,
                                                             @RequestParam("firstname") final String firstname,
                                                             @RequestBody MedicalRecord medicalRecord)
            throws MedicalRecordInvalidException, MedicalRecordNotFoundException,Exception{

        ResponseEntity<MedicalRecord> response = null;
        MedicalRecord foundMedicalRecord = medicalRecordService.findByLastnameAndFirstname(lastname, firstname);

        medicalRecordService.save(medicalRecord);
        response = ResponseEntity.ok().body(foundMedicalRecord);

        return response;
    }

    @DeleteMapping("/medicalrecord")
    public ResponseEntity<MedicalRecord> deleteMedicalRecord(@RequestParam("lastname") final String lastname,
                                                             @RequestParam("firstname") final String firstname)
                                                            throws MedicalRecordNotFoundException,MedicalRecordInvalidException, Exception{
        ResponseEntity<MedicalRecord> response = null;
        medicalRecordService.delete(lastname, firstname);
        response = new ResponseEntity<MedicalRecord>(HttpStatus.ACCEPTED);

        return response;
    }
}
