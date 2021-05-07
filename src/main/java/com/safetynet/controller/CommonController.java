package com.safetynet.controller;

import com.safetynet.model.dto.ChildrenCoveredDTO;
import com.safetynet.model.dto.PersonsCoveredByStation;
import com.safetynet.service.CommonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CommonController {
    private final Logger logger = LoggerFactory.getLogger(CommonController.class);

    @Autowired
    private CommonService commonService;

    @GetMapping("/firestation")
    public ResponseEntity<PersonsCoveredByStation> getStationCoverage(@RequestParam("stationNumber") String station){
        return null;
    }

    /**
     * getChildrenCovered - Retrieve children covered at a given address
     * @param address
     * @return
     */
    @GetMapping("/childAlert")
    public ResponseEntity<ChildrenCoveredDTO> getChildrenCovered(@RequestParam("address") final String address){
        logger.info("Request => children covered at address {}", address);
        ChildrenCoveredDTO childrenCovered = commonService.produceChildrenAtAddress(address);
        return new ResponseEntity<>(childrenCovered, HttpStatus.OK);
    }

    @GetMapping("/phoneAlert")
    public ResponseEntity<List<String>> getResidentPhones(@RequestParam("firestation") final int station){
        logger.info("Request => List Phone personnes couvertes par la station: {}, residents phone numbers", station);
        List<String> residentsPhoneNumbers = commonService.getResidentPhoneNumber(station);
        return new ResponseEntity<>(residentsPhoneNumbers,HttpStatus.OK);
    }

    @GetMapping("/communityEmail")
    public ResponseEntity<List<String>> getResidentPhones(@RequestParam("city") String city){
        return null;
    }
}
