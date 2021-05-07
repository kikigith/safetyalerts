package com.safetynet.service;

import com.safetynet.exception.FirestationNotFoundException;
import com.safetynet.model.Firestation;
import com.safetynet.model.MedicalRecord;
import com.safetynet.model.Person;
import com.safetynet.model.dto.ChildDTO;
import com.safetynet.model.dto.ChildrenCoveredDTO;
import com.safetynet.model.dto.PersonsCoveredByStation;
import com.safetynet.repository.FirestationRepository;
import com.safetynet.repository.MedicalRecordRepository;
import com.safetynet.repository.PersonRepository;
import com.safetynet.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CommonServiceImpl implements CommonService {
    private final Logger logger = LoggerFactory.getLogger(CommonServiceImpl.class);

    @Autowired
    PersonRepository personRepository;
    @Autowired
    FirestationRepository firestationRepository;
    @Autowired
    MedicalRecordRepository medicalRecordRepository;

    /**
     * http://localhost:8080/firestation?stationNumber=<station_number>
     * produceStationCoverage -
     * @param stationId
     * @return
     * @throws FirestationNotFoundException
     */
    @Override
    public PersonsCoveredByStation produceStationCoverage(int stationId) throws FirestationNotFoundException {
        logger.info("Couverture station  " +stationId);
        PersonsCoveredByStation personsCoveredByStation = new PersonsCoveredByStation();
        AtomicInteger nombreEnfants = new AtomicInteger();
        AtomicInteger nombreAdultes = new AtomicInteger();
        Firestation firestation = firestationRepository.findById(stationId);
        if(firestation == null )
            throw new FirestationNotFoundException("La station d'id : " +stationId+ " n'existe pas");
        List<Person> personsCovered = personRepository.getPersonsAtAddress(firestation.getAddress());
        personsCovered.forEach(personCovered->{
            MedicalRecord medicalRecord = medicalRecordRepository.findByLastnameAndFirstname(personCovered.getLastName(),personCovered.getFirstName());
            if(Utils.calculateAge(medicalRecord.getBirthdate()) <= 18){
                nombreEnfants.getAndIncrement();
            }else{
                nombreAdultes.getAndIncrement();
            }
        });
        personsCoveredByStation.setStationId(stationId);
        personsCoveredByStation.setPersons(personsCovered);
        personsCoveredByStation.setNombreEnfants(nombreEnfants.get());
        personsCoveredByStation.setNombreAdults(nombreAdultes.get());
        logger.info("Couverture station  " +personsCoveredByStation);

        return personsCoveredByStation;
    }

    /**
     * http://localhost:8080/childAlert?address=<address>
     * produceChildrenAtAddress -
     * @param address
     * @return
     */
    @Override
    public ChildrenCoveredDTO produceChildrenAtAddress(String address) {
        logger.info("Couverture des enfants à l'adresse :  " +address);
        ChildrenCoveredDTO childrenCovered = new ChildrenCoveredDTO();
        List<ChildDTO> lesEnfants = new ArrayList<>();
        List<Person>  autreMembres = new ArrayList<>();
        Firestation firestation = firestationRepository.findByAddress(address);
        if(firestation == null )
            throw new FirestationNotFoundException("Aucune station ne couvre l'addresse : " +address);
        List<Person> personsCovered = personRepository.getPersonsAtAddress(address);
        personsCovered.forEach(personCovered-> {
            MedicalRecord medicalRecord = medicalRecordRepository.findByLastnameAndFirstname(personCovered.getLastName(), personCovered.getFirstName());
            if (Utils.calculateAge(medicalRecord.getBirthdate()) <= 18) {
                ChildDTO enfant = new ChildDTO();
                enfant.setPerson(personCovered);
                enfant.setAge(Utils.calculateAge(medicalRecord.getBirthdate()));
                lesEnfants.add(enfant);
            } else {
                autreMembres.add(personCovered);
            }
        });
        childrenCovered.setEnfants(lesEnfants);
        childrenCovered.setAutresMembres(autreMembres);
        logger.info("Couverture des enfants à l'adresse :  " +address+ " ::" + childrenCovered);
        return childrenCovered;
    }

    /**
     * http://localhost:8080/phoneAlert?firestation=<firestation_number>
     * getResidentPhoneNumber -
     * @param stationId
     * @return
     * @throws FirestationNotFoundException
     */
    @Override
    public List<String> getResidentPhoneNumber(int stationId) throws FirestationNotFoundException {
        logger.info("Numero de téléphone des personne couverte par la station id :  " +stationId);
        Firestation firestation = firestationRepository.findById(stationId);
        if(firestation == null )
            throw new FirestationNotFoundException("Station d'id : " +stationId+ " introuvable");
        List<String> phones = personRepository.getPhonesPersonAtAddress(firestation.getAddress());
        logger.info("Numéro  de téléphone des personne couverte par la station id :  " +stationId+ " :: " +phones);
        return phones;
    }

    @Override
    public List<String> getCommunityEmails(String city) {
        List<String> cityInhabitantsEmails=new ArrayList<>();
        personRepository.findAll().forEach(pers -> {
                    if (pers.getCity().equalsIgnoreCase(city)) {
                        cityInhabitantsEmails.add(pers.getEmail());
                    }
                }
        );
        return cityInhabitantsEmails;
    }

}
