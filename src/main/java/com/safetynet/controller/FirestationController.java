package com.safetynet.controller;

import com.safetynet.exception.FirestationInvalidException;
import com.safetynet.model.Firestation;
import com.safetynet.service.FirestationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class FirestationController {

    private final Logger logger = LoggerFactory.getLogger(FirestationController.class);

    @Autowired
    private FirestationService firestationService;


    /**
     * getFirestations - Get all firestations from the repository
     * @return - an iterable list
     */
    @GetMapping("/firestations")
    public List<Firestation> getFirestations(){
        return firestationService.findAll();
    }

    @PostMapping("/firestation")
    public ResponseEntity<Firestation> saveFirestation(@RequestBody Firestation firestation) throws
            FirestationInvalidException,Exception {
        Firestation persistedFirestation = firestationService.save(firestation);
        return ResponseEntity.created(URI.create(String.format("/firestation"))).body(persistedFirestation);
    }

    @GetMapping("/firestation")
    public ResponseEntity<Firestation> searchFirestation(){
        return null;
    }

    @PutMapping("/firestation")
    public ResponseEntity<Firestation> updateFirestation(@RequestParam Integer stationId,
                                                         @RequestBody Firestation firestation){
        try {
            Firestation frs = firestationService.findById(firestation.getStation());
            firestationService.save(frs);
            return ResponseEntity.ok().body(frs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @DeleteMapping("/firestation")
    public ResponseEntity<HttpStatus> deleteFirestation(@RequestParam Integer stationId){
        try {
            firestationService.delete(stationId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<HttpStatus>(HttpStatus.ACCEPTED);
    }

}
