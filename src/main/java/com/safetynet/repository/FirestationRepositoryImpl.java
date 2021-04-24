package com.safetynet.repository;

import com.safetynet.model.Firestation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

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
        Firestation aFirestation = findById(firestation.getStation());
        if(aFirestation != null ){//Update
            int index = firestations.indexOf(aFirestation);
            firestations.remove(aFirestation);
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
    public Firestation findById(int id) {
        for (Firestation firestation:dataSourceComponent.getFirestations()) {
            if(firestation.getStation()==id)
                return firestation;
        }
        return null;
    }

    @Override
    public Firestation findByAddress(String address) {
        for (Firestation firestation:dataSourceComponent.getFirestations()) {
            if(firestation.getAddress().equalsIgnoreCase(address))
                return firestation;
        }
        return null;
    }
}
