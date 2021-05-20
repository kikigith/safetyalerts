package com.safetynet.service;


import com.safetynet.exception.FirestationInvalidException;
import com.safetynet.exception.FirestationNotFoundException;
import com.safetynet.model.Firestation;
import com.safetynet.model.dto.PersonsCoveredByStation;

import java.util.List;
import java.util.Optional;

public interface FirestationService {
    List<Firestation> findAll();
    Firestation save(Firestation firestation) throws FirestationInvalidException;
    Firestation update(Firestation firestation) throws FirestationInvalidException, FirestationNotFoundException;
    void delete(int id) throws FirestationNotFoundException;
    Firestation findById(int id) throws FirestationNotFoundException;
    List<Firestation> findByStation(int station) throws  FirestationNotFoundException;
    List<Firestation> findByAddress(String address) throws FirestationNotFoundException;
}
