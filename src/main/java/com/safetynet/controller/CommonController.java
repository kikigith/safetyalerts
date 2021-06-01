package com.safetynet.controller;

import com.safetynet.model.dto.ChildrenCoveredDTO;
import com.safetynet.model.dto.PersonMedicalDetailsDTO;
import com.safetynet.model.dto.PersonsCoveredAtAddress;
import com.safetynet.model.dto.PersonsCoveredByStation;
import com.safetynet.service.CommonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CommonController {
    private final Logger logger = LoggerFactory.getLogger(CommonController.class);

    @Autowired
    private CommonService commonService;

    /**
     * Endpoint: http://localhost:8080/firestation?stationNumber=<station_number>
     * getStationCoverage -
     * @param station
     * @return - une liste de personnes couvertes par la station
     */
    @GetMapping("/firestation")
    public ResponseEntity<PersonsCoveredByStation> getStationCoverage(@RequestParam("stationNumber") int station){
        logger.info("Request => personnes couvertes par la station {}", station);
        PersonsCoveredByStation personsCovered = commonService.produceStationCoverage(station);
        logger.info("Réponse => personnes couvertes {}", personsCovered);
        return new ResponseEntity<>(personsCovered,HttpStatus.OK);
    }

    /**
     * Endpoint: http://localhost:8080/childAlert?address=<address>
     * getChildrenCovered - Retrieve children covered at a given address
     * @param address - addresse couverte
     * @return - retourne la liste des enfants couverts à une addresse
     */
    @GetMapping("/childAlert")
    public ResponseEntity<ChildrenCoveredDTO> getChildrenCovered(@RequestParam("address") final String address){
        logger.info("Request => children covered at address {}", address);
        ChildrenCoveredDTO childrenCovered = commonService.produceChildrenAtAddress(address);
        logger.info("Réponse => enfants couverts {}", childrenCovered);
        return new ResponseEntity<>(childrenCovered, HttpStatus.OK);
    }

    /**
     * Endpoint: http://localhost:8080/phoneAlert?firestation=<firestation_number>
     * getResidentPhones
     * @param station
     * @return - retourne les numéros de téléphone des résidents couverts par une station
     */
    @GetMapping("/phoneAlert")
    public ResponseEntity<List<String>> getResidentPhones(@RequestParam("firestation") final int station){
        logger.info("Request => List Phone personnes couvertes par la station: {}, residents phone numbers", station);
        List<String> residentsPhoneNumbers = commonService.getResidentPhoneNumber(station);
        logger.info("Réponse => Liste phone {}", residentsPhoneNumbers);
        return new ResponseEntity<>(residentsPhoneNumbers,HttpStatus.OK);
    }

    /**
     * Endpoint: http://localhost:8080/communityEmail?city=<city>
     * @param city
     * @return - liste des adresses email de tous les habitants de la ville
     */
    @GetMapping("/communityEmail")
    public ResponseEntity<List<String>> getCommunityEmail(@RequestParam("city") String city){
        logger.info("Request => List mail communauté de la ville {}", city);
        List<String> communityEmail = commonService.getCommunityEmails(city);
        logger.info("Réponse => email {}", communityEmail);
        return new ResponseEntity<>(communityEmail,HttpStatus.OK);
    }

    /**
     * Endpoint: http://localhost:8080/fire?address=<address>
     * @param address
     * @return - liste des habitants vivants à l'adresse données
     */
    @GetMapping("/fire")
    public ResponseEntity<PersonsCoveredAtAddress> getPersonsCoveredAtAddress(@RequestParam("address") String address){
        logger.info("Request => personnes couvertes à l'addresse {}", address);
        PersonsCoveredAtAddress personsCoveredAtAddress = commonService.getAddressCoverage(address);
        logger.info("Réponse => personnes {}", personsCoveredAtAddress);
        return new ResponseEntity<>(personsCoveredAtAddress,HttpStatus.OK);
    }

    /**
     * Endpoint: http://localhost:8080/personInfo?firstName=<firstName>&lastName=<lastName>
     * @param lastName
     * @param firstName
     * @return
     */
    @GetMapping("/personInfo")
    public ResponseEntity<List<PersonMedicalDetailsDTO>> getPersonsMedicalDetails(@RequestParam("lastName") String lastName,
                                                                                  @RequestParam("firstName") String firstName){
        logger.info("Request => Recherche personnes avec nom : {}, et prénom : {}", lastName, firstName);
        List<PersonMedicalDetailsDTO> personMedicalDetailsDTOS = commonService.getPersonMedicalDetails(lastName,firstName);
        logger.info("Réponse => personnes : {}", personMedicalDetailsDTOS);
        return new ResponseEntity<>(personMedicalDetailsDTOS, HttpStatus.OK);
    }

    /**
     * Endpoint: http://localhost:8080/flood/stations?stations=<a list of station_numbers>
     * @param stations
     * @return
     */
    @GetMapping("/flood/stations")
    public ResponseEntity<List<PersonsCoveredAtAddress>> getAddressesCovered(@RequestParam("stations") List<String> stations){
        logger.info("Request => personnes couvertes par les station {}", stations);
        List<PersonsCoveredAtAddress> personsCoveredAtAddresses =commonService.getAddressesCoverage(stations);
        logger.info("Réponse => personnes : {}", personsCoveredAtAddresses);
        return new ResponseEntity<>(personsCoveredAtAddresses, HttpStatus.OK);
    }
}
