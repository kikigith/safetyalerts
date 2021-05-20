package com.safetynet.service;

import com.safetynet.exception.FirestationInvalidException;
import com.safetynet.exception.FirestationNotFoundException;
import com.safetynet.model.Firestation;
import com.safetynet.repository.FirestationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        logger.info("Saving a firestation id: "+firestation.getId() + " address: "+firestation.getAddress());
        if(firestation.getId()<=0){
            throw new FirestationInvalidException(" L'ID de la station doit être un entier positif non nul");
        }
        if(firestation.getAddress() == null || firestation.getAddress().isEmpty() || firestation.getAddress().isBlank() ){
            throw new FirestationInvalidException(" L'addresse de la station doit être une chaine non vide");
        }
        return firestationRepository.save(firestation);
    }

    @Override
    public Firestation update(Firestation firestation) throws FirestationInvalidException, FirestationNotFoundException {
        if(firestation.getId()<=0)
            throw new FirestationInvalidException(" L'ID de la station doit être un entier positif non nul");
        if( firestation.getAddress()==null || firestation.getAddress().isEmpty() || firestation.getAddress().isBlank())
            throw new FirestationInvalidException(" L'addresse de la station doit être une chaine non vide");
        logger.info("Updating  firestation id: '" + firestation.getStation() + "' address: '" + firestation.getAddress());
        Optional<Firestation> foundFirestationToUpdate = firestationRepository.findById(firestation.getId());
        if(foundFirestationToUpdate.isEmpty())
            throw new FirestationNotFoundException("La stationd ID: " +firestation.getStation()+ ", address: " +firestation.getAddress()+ " n'existe pas");
        //foundFirestationToUpdate.setStation(firestation.getStation());
        foundFirestationToUpdate.get().setAddress(firestation.getAddress());
        logger.info("firestation [" + firestation+ "] updated successfully");
        return firestationRepository.save(foundFirestationToUpdate.get());
    }

    @Override
    public void delete(int id) throws FirestationNotFoundException {
        Optional<Firestation> firestation = firestationRepository.findById(id);
        logger.info("Requête=> Suppression firestation id : "+id);
        if(firestation.isEmpty() )
            throw new FirestationNotFoundException("La station d'ID: " +id+ " n'existe pas ");
        firestationRepository.delete(firestation.get());
        logger.info("Firestation  [id:" + id + " address:" + firestation.get().getAddress() + "] supprimée avec succès");
    }

    @Override
    public Firestation findById(int id) throws FirestationNotFoundException {
        Optional<Firestation> firestation = firestationRepository.findById(id);
        logger.info("Requête=> Recherche firestation id : "+id);
        if(firestation.isEmpty()) {
            throw new FirestationNotFoundException("La station d'ID: " + id + " n'existe pas ");
        }
        logger.info("Firestation  [id:" + id + " address:" + firestation.get().getAddress() + "]");
        return  firestation.get();
    }

    @Override
    public List<Firestation> findByStation(int id) throws FirestationNotFoundException {
        logger.info("Requête=>Recherche Firestation id: "+id);
        Optional<List<Firestation>> foundFirestation = firestationRepository.findByStation(id);
        if(foundFirestation.isEmpty()) {
            throw new FirestationNotFoundException("La station d'id : " +id+ " n'existe pas");
        }
        logger.info("Réponse=> firestation  "+foundFirestation.get());
        return foundFirestation.get();
    }

    @Override
    public List<Firestation> findByAddress(String address) throws FirestationNotFoundException {
        logger.info("Requête=>Recherche Firestation d'addresse: "+address);
        Optional<List<Firestation>> foundFirestation = firestationRepository.findByAddress(address);
        if(foundFirestation.isEmpty()) {
            throw new FirestationNotFoundException("La Firestaiton addresse : "+address+"n'existe pas");
        }
        logger.info("Réponse=> firestation  "+foundFirestation);
        return foundFirestation.get();
    }
}
