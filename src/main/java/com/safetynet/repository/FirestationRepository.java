package com.safetynet.repository;

import com.safetynet.model.Firestation;

import java.util.List;

public interface FirestationRepository {
    List<Firestation> findAll();
    Firestation save(Firestation firestation);
    void delete(Firestation firestation);
    Firestation findById(int id);
    Firestation findByAddress(String address);
}
