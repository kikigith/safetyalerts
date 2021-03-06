package com.safetynet.controller;

import com.safetynet.exception.FirestationInvalidException;
import com.safetynet.model.Firestation;
import com.safetynet.service.FirestationService;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
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
    @GetMapping("/stations")
    public ResponseEntity<List<Firestation>> getFirestations(){
        List<Firestation> firestations=new ArrayList<>();
        firestations = firestationService.findAll();
        return new ResponseEntity<>(firestations, HttpStatus.OK);
    }

    @PostMapping("/station")
    public ResponseEntity<Firestation> saveFirestation(@RequestBody Firestation firestation)  {
        logger.info("Request = @RequestBody = {}",firestation);
        if(firestation.getStation()<=0)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if(firestation.getAddress().isBlank() || firestation.getAddress().isEmpty())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Firestation persistedFirestation = firestationService.save(firestation);
        return ResponseEntity.created(URI.create(String.format("/firestation"))).body(persistedFirestation);
    }

    @GetMapping("/station")
    public ResponseEntity<Firestation> searchFirestationByStation(@RequestParam("stationId") final Integer station){
        logger.info("Request=> recheche station avec id: " +station);
        if(station <= 0 || !(station instanceof Integer)) {
            logger.error("la Requ??te est mal format??e");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Firestation foundFirestation = firestationService.findByStation(station);
        if(foundFirestation == null) {
            logger.error("La station : " +station+ " n'existe pas");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("R??ponse=> r??sultat recheche station avec id: " +station+ ", firestation:" +foundFirestation);
        return new ResponseEntity<>(foundFirestation, HttpStatus.OK);
    }

    @GetMapping("/stationAddress")
    public ResponseEntity<Firestation> searchFirestationByAddress(@RequestParam("address") final String address){
        logger.info("Request=> recheche station avec address: " +address);
        if(address==null || address.isBlank() || address.isEmpty()) {
            logger.error("la Requ??te est mal format??e");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Firestation foundFirestation = firestationService.findByAddress(address);
        if(foundFirestation == null) {
            logger.error("La station d'adresse: " +address+ " n'existe pas");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("R??ponse=> r??sultat recheche station avec addresse: " +address+ ", firestation:" +foundFirestation);
        return new ResponseEntity<>(foundFirestation, HttpStatus.OK);
    }

    @PutMapping("/station")
    public ResponseEntity<Firestation> updateFirestation(@RequestBody Firestation firestation){
        logger.info("Request = Mise ?? jour @RequestBody = {}", firestation);
        if(firestation.getStation()<=0)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if(firestation.getAddress() == null || firestation.getAddress().isEmpty() || firestation.getAddress().isBlank())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Firestation updatedFirestation = firestationService.update(firestation);
        if(updatedFirestation == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        logger.info("R??ponse = Firestation @ResponseBody = {} " +updatedFirestation+ " mise ?? jour avec succ??s ");
        return ResponseEntity.accepted().body(updatedFirestation);
    }

    @DeleteMapping("/station")
    public ResponseEntity<HttpStatus> deleteFirestation(@RequestParam Integer stationId){
        logger.info("Request Delete firestation with id: " +stationId);
        firestationService.delete(stationId);
        return new ResponseEntity<HttpStatus>(HttpStatus.ACCEPTED);
    }

}
