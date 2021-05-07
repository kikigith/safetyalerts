package com.safetynet.service;

import com.safetynet.exception.FirestationInvalidException;
import com.safetynet.exception.FirestationNotFoundException;
import com.safetynet.model.Firestation;
import com.safetynet.model.dto.PersonsCoveredByStation;
import com.safetynet.repository.FirestationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FirestationServiceImpl implements FirestationService{
    private final Logger logger = LoggerFactory.getLogger(FirestationService.class);

    @Autowired
    FirestationRepository firestationRepository;

    @Override
    public List<Firestation> findAll() {
        logger.info("Loading firestations");
        return firestationRepository.findAll();
    }

    @Override
    public Firestation save(Firestation firestation) throws FirestationInvalidException{
        logger.info("Saving a firestation id: "+firestation.getStation() + " address: "+firestation.getAddress());
        if(firestation.getStation()<=0){
            throw new FirestationInvalidException(" L'ID de la station doit être un entier positif non nul");
        }
        if(firestation.getAddress() == null || firestation.getAddress().isEmpty() || firestation.getAddress().isBlank() ){
            throw new FirestationInvalidException(" L'addresse de la station doit être une chaine non vide");
        }
        return firestationRepository.save(firestation);
    }

    @Override
    public Firestation update(Firestation firestation) throws FirestationInvalidException, FirestationNotFoundException {
        if(firestation.getStation()<=0)
            throw new FirestationInvalidException(" L'ID de la station doit être un entier positif non nul");
        if( firestation.getAddress()==null || firestation.getAddress().isEmpty() || firestation.getAddress().isBlank())
            throw new FirestationInvalidException(" L'addresse de la station doit être une chaine non vide");
        logger.info("Updating  firestation id: '" + firestation.getStation() + "' address: '" + firestation.getAddress());
        Firestation foundFirestationToUpdate = firestationRepository.findById(firestation.getStation());
        if(foundFirestationToUpdate == null)
            throw new FirestationNotFoundException("La stationd ID: " +firestation.getStation()+ ", address: " +firestation.getAddress()+ " n'existe pas");
        foundFirestationToUpdate.setStation(firestation.getStation());
        foundFirestationToUpdate.setAddress(firestation.getAddress());
        logger.info("firestation [" + firestation+ "] updated successfully");
        return firestationRepository.save(foundFirestationToUpdate);
    }

    @Override
    public void delete(int id) throws FirestationNotFoundException {
        Firestation firestation = firestationRepository.findById(id);
        logger.info("Requête=> Suppression firestation id : "+id);
        if(firestation == null )
            throw new FirestationNotFoundException("La station d'ID: " +id+ " n'existe pas ");
        firestationRepository.delete(firestation);
        logger.info("Firestation  [id:" + id + " address:" + firestation.getAddress() + "] supprimée avec succès");
    }

    @Override
    public Firestation findByStation(int id) throws FirestationNotFoundException {
        logger.info("Requête=>Recherche Firestation id: "+id);
        Firestation foundFirestation = firestationRepository.findById(id);
        if(foundFirestation == null) {
            throw new FirestationNotFoundException("La station d'id : " +id+ " n'existe pas");
        }
        logger.info("Réponse=> firestation  "+foundFirestation);
        return foundFirestation;
    }

    @Override
    public Firestation findByAddress(String address) throws FirestationNotFoundException {
        logger.info("Requête=>Recherche Firestation d'addresse: "+address);
        Firestation foundFirestation = firestationRepository.findByAddress(address);
        if(foundFirestation == null) {
            throw new FirestationNotFoundException("La Firestaiton addresse : "+address+"n'existe pas");
        }
        logger.info("Réponse=> firestation  "+foundFirestation);
        return foundFirestation;
    }
}
