package com.safetynet.repository;

import com.safetynet.model.Firestation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class FirestationRepositoryImpl implements FirestationRepository{
    @Autowired
    DataSourceComponent dataSourceComponent;

    @Override
    public List<Firestation> findAll() {
        return dataSourceComponent.getFirestations();
    }

    @Override
    public Firestation save(Firestation firestation) {
        List<Firestation> firestations = dataSourceComponent.getFirestations();
        Optional<Firestation> foundFirestation = findById(firestation.getId());
        if(foundFirestation.isPresent()){//Update
            int index = firestations.indexOf(foundFirestation.get());
            Firestation oldFirestation = firestations.get(index);
            firestations.remove(oldFirestation);
            firestations.add(index, firestation);
        }else{//Add new
            firestations.add(firestation);
        }
        return firestation;
    }

    @Override
    public void delete(Firestation firestation) {
        List<Firestation> firestations = dataSourceComponent.getFirestations();
        firestations.remove(firestation);
    }

    @Override
    public Optional<Firestation> findById(int id) {
        List<Firestation> firestations = dataSourceComponent.getFirestations();
        for (Firestation firestation:firestations) {
            if(firestation.getId() == id) {
                return Optional.of(firestation);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<Firestation>> findByStation(int station) {//simple liste en retour optional pas nécessaire
        List<Firestation> allFirestations = dataSourceComponent.getFirestations();
        List<Firestation> foundFirestations = new ArrayList<>();
        for (Firestation firestation:allFirestations) {
            if(firestation.getStation()==station)
                foundFirestations.add(firestation);
        }
        if(foundFirestations.isEmpty())
            return Optional.empty();
        return Optional.of(foundFirestations);
    }

    @Override
    public Optional<List<Firestation>> findByAddress(String address) {
        List<Firestation> allFirestations = dataSourceComponent.getFirestations();
        List<Firestation> foundFirestations = new ArrayList<>();
        for (Firestation firestation:allFirestations) {
            if(firestation.getAddress().equalsIgnoreCase(address))
                foundFirestations.add(firestation);
        }
        if(foundFirestations.isEmpty())
            return Optional.empty();
        return Optional.of(foundFirestations);
    }
}
