package com.safetynet.repository;

import com.safetynet.model.Firestation;

import java.util.List;
import java.util.Optional;

public interface FirestationRepository {
    List<Firestation> findAll();
    Firestation save(Firestation firestation);
    void delete(Firestation firestation);
    Optional<Firestation> findById(int id);
    Optional<List<Firestation>> findByStation(int station);
    Optional<List<Firestation>> findByAddress(String address);
}
