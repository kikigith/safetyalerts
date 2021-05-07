package com.safetynet.service;


import com.safetynet.exception.FirestationInvalidException;
import com.safetynet.exception.FirestationNotFoundException;
import com.safetynet.model.Firestation;
import com.safetynet.model.dto.PersonsCoveredByStation;

import java.util.List;

public interface FirestationService {
    List<Firestation> findAll();
    Firestation save(Firestation firestation) throws FirestationInvalidException;
    Firestation update(Firestation firestation) throws FirestationInvalidException, FirestationNotFoundException;
    void delete(int id) throws FirestationNotFoundException;
    Firestation findByStation(int id) throws  FirestationNotFoundException;
    Firestation findByAddress(String address) throws FirestationNotFoundException;
}
