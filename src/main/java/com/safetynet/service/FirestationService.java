package com.safetynet.service;


import com.safetynet.exception.FirestationInvalidException;
import com.safetynet.exception.FirestationNotFoundException;
import com.safetynet.model.Firestation;

import java.util.List;

public interface FirestationService {
    List<Firestation> findAll();
    Firestation save(Firestation firestation) throws FirestationInvalidException, Exception;
    void delete(int id) throws FirestationNotFoundException, Exception;
    Firestation findById(int id) throws  FirestationNotFoundException,Exception;
    Firestation findByAddress(String address) throws FirestationNotFoundException, Exception;
}
