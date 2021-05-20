package com.safetynet.service;

import com.safetynet.exception.FirestationNotFoundException;
import com.safetynet.model.Firestation;
import com.safetynet.model.MedicalRecord;
import com.safetynet.model.Person;
import com.safetynet.model.dto.*;
import com.safetynet.repository.FirestationRepository;
import com.safetynet.repository.MedicalRecordRepository;
import com.safetynet.repository.PersonRepository;
import com.safetynet.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
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
     *
     * @param stationId - id de la station
     * @return - retourne la liste des personnes couvertes par la station
     * @throws FirestationNotFoundException - exception station introuvable
     */
    @Override
    public PersonsCoveredByStation produceStationCoverage(int stationId) throws FirestationNotFoundException {
        logger.info("Couverture station  " + stationId);
        PersonsCoveredByStation personsCoveredByStation = new PersonsCoveredByStation();
        AtomicInteger nombreEnfants = new AtomicInteger();
        AtomicInteger nombreAdultes = new AtomicInteger();
        Optional<Firestation> firestation = firestationRepository.findById(stationId);
        if (firestation.isEmpty())
            throw new FirestationNotFoundException("La station d'id : " + stationId + " n'existe pas");
        List<PersonInfoDTO> personsInfoDTOCovered = new ArrayList<>();

        List<Person> personsCovered = personRepository.findByAddress(firestation.get().getAddress());
        personsCovered.forEach(personCovered -> {
            MedicalRecord medicalRecord = medicalRecordRepository.findByLastnameAndFirstname(personCovered.getLastName(), personCovered.getFirstName());
            PersonInfoDTO personInfo = new PersonInfoDTO();
            personInfo.setLastname(personCovered.getLastName());
            personInfo.setFirstname(personCovered.getFirstName());
            personInfo.setPhone(personCovered.getPhone());
            personInfo.setAddresse(personCovered.getAddress());
            personsInfoDTOCovered.add(personInfo);
            if (Utils.calculateAge(medicalRecord.getBirthdate()) <= 18) {
                nombreEnfants.getAndIncrement();
            } else {
                nombreAdultes.getAndIncrement();
            }
        });


        personsCoveredByStation.setStationId(stationId);
        personsCoveredByStation.setPersons(personsInfoDTOCovered);
        personsCoveredByStation.setNombreEnfants(nombreEnfants.get());
        personsCoveredByStation.setNombreAdults(nombreAdultes.get());
        logger.info("Couverture station  " + personsCoveredByStation);

        return personsCoveredByStation;
    }

    /**
     * http://localhost:8080/childAlert?address=<address>
     * produceChildrenAtAddress -
     *
     * @param address - addresse couverte par une station
     * @return - retourne une liste d'enfants
     */
    @Override
    public ChildrenCoveredDTO produceChildrenAtAddress(String address) {
        logger.info("Couverture des enfants à l'adresse :  " + address);
        ChildrenCoveredDTO childrenCovered = new ChildrenCoveredDTO();
        List<ChildDTO> lesEnfants = new ArrayList<>();
        List<Person> autreMembres = new ArrayList<>();
       Optional<List<Firestation>> foundFirestation = firestationRepository.findByAddress(address);
        if (foundFirestation.isEmpty())
            throw new FirestationNotFoundException("Aucune station ne couvre l'addresse : " + address);
        List<Person> personsCovered = personRepository.findByAddress(address);
        personsCovered.forEach(personCovered -> {
            MedicalRecord medicalRecord = medicalRecordRepository.findByLastnameAndFirstname(personCovered.getLastName(), personCovered.getFirstName());
            if (Utils.calculateAge(medicalRecord.getBirthdate()) <= 18) {
                ChildDTO enfant = new ChildDTO();
                enfant.setFirstname(personCovered.getFirstName());
                enfant.setLastname(personCovered.getLastName());
                enfant.setAge(Utils.calculateAge(medicalRecord.getBirthdate()));
                lesEnfants.add(enfant);
            } else {
                autreMembres.add(personCovered);
            }
        });
        childrenCovered.setEnfants(lesEnfants);
        childrenCovered.setAutresMembres(autreMembres);
        logger.info("Couverture des enfants à l'adresse :  " + address + " ::" + childrenCovered);
        return childrenCovered;
    }

    /**
     * http://localhost:8080/phoneAlert?firestation=<firestation_number>
     * getResidentPhoneNumber -
     *
     * @param stationId - id de la station
     * @return - retourne une liste de numéro de téléphone
     * @throws FirestationNotFoundException - exception station introuvable
     */
    @Override
    public List<String> getResidentPhoneNumber(int stationId) throws FirestationNotFoundException {
        List<String> phones = new ArrayList<>();
        logger.info("Numero de téléphone des personne couverte par la station id :  " + stationId);
        Optional<Firestation> firestation = firestationRepository.findById(stationId);
        if (firestation.isEmpty())
            throw new FirestationNotFoundException("Station d'id : " + stationId + " introuvable");
        List<String> phonesStation = personRepository.findByAddressAndSelectPhone(firestation.get().getAddress());
        phones.addAll(phonesStation);

        logger.info("Numéro  de téléphone des personne couverte par la station id :  " + stationId + " :: " + phones);
        return phones;
    }

    @Override
    public List<String> getCommunityEmails(String city) {//findByCity dans le repository serait mieux
        List<String> cityInhabitantsEmails = new ArrayList<>();
        personRepository.findAll().forEach(person -> {

                    if (person.getCity().equalsIgnoreCase(city)) {
                        cityInhabitantsEmails.add(person.getEmail());
                    }
                }
        );
        return cityInhabitantsEmails;
    }

    @Override
    public PersonsCoveredAtAddress getAddressCoverage(String address) {
        PersonsCoveredAtAddress personsCovered = new PersonsCoveredAtAddress();
        List<Person> personsAtAddress = personRepository.findByAddress(address);
        List<PersonMedicalInfoDTO> personsMedicalInfo = new ArrayList<>();
        personsAtAddress.forEach( person -> {
            MedicalRecord personMedicalRecord = medicalRecordRepository.findByLastnameAndFirstname(person.getLastName(),person.getFirstName());
            PersonMedicalInfoDTO personMedicalInfoDTO = new PersonMedicalInfoDTO();
            personMedicalInfoDTO.setFirstname(person.getFirstName());
            personMedicalInfoDTO.setLastname(person.getLastName());
            personMedicalInfoDTO.setPhone(person.getPhone());
            personMedicalInfoDTO.setAge(Utils.calculateAge(personMedicalRecord.getBirthdate()));
            Map<String, List<String>> antecedents = new HashMap<>();
            antecedents.put("medications", personMedicalRecord.getMedications());
            antecedents.put("allergies",personMedicalRecord.getAllergies());
            personMedicalInfoDTO.setAntecedents(antecedents);
            personsMedicalInfo.add(personMedicalInfoDTO);
        });
        Optional<List<Firestation>> lesStations = firestationRepository.findByAddress(address);
        Firestation firestation = new Firestation();
        if(lesStations.isPresent())
            firestation = lesStations.get().get(0);
        personsCovered.setPersonsCovered(personsMedicalInfo);
        personsCovered.setAddress(address);
        personsCovered.setStation(firestation.getStation());

        return personsCovered;
    }

    @Override
    public List<PersonMedicalDetailsDTO> getPersonMedicalDetails(String lastname, String firstname) {
        List<PersonMedicalDetailsDTO> personMedicalDetailsDTOS = new ArrayList<>();
        List<Person> persons = personRepository.findAllByLastNameAndFirstName(lastname,firstname);
        persons.forEach( person -> {
            PersonMedicalDetailsDTO personMedicalDetailsDTO = new PersonMedicalDetailsDTO();
            MedicalRecord personMedicalRecord = medicalRecordRepository.findByLastnameAndFirstname(lastname,firstname);
            personMedicalDetailsDTO.setFirstname(person.getFirstName());
            personMedicalDetailsDTO.setLastname(person.getLastName());
            personMedicalDetailsDTO.setEmail(person.getEmail());
            personMedicalDetailsDTO.setAddress(person.getAddress());
            Map<String,List<String>> antecedents = new HashMap<>();
            antecedents.put("allergies", personMedicalRecord.getAllergies());
            antecedents.put("medications", personMedicalRecord.getMedications());
            personMedicalDetailsDTO.setAntecedents(antecedents);

            personMedicalDetailsDTOS.add(personMedicalDetailsDTO);
        });
        return personMedicalDetailsDTOS;
    }

    @Override
    public List<PersonsCoveredAtAddress> getAddressesCoverage(List<String> stations) {
        List<PersonsCoveredAtAddress> lesPersonnesCouvertes = new ArrayList<>();
        stations.forEach(s -> {
            int station = Integer.parseInt(s);
            Optional<List<Firestation>> firestations = firestationRepository.findByStation(station);
            if(firestations.isPresent()){
                firestations.get().forEach(
                        firestation -> {
                            String address = firestation.getAddress();
                            List<Person> personsAtAddress = personRepository.findByAddress(address);
                            List<PersonMedicalInfoDTO> personsMedicalInfo = new ArrayList<>();
                            personsAtAddress.forEach( person -> {
                                MedicalRecord personMedicalRecord = medicalRecordRepository.findByLastnameAndFirstname(person.getLastName(),person.getFirstName());
                                PersonMedicalInfoDTO personMedicalInfoDTO = new PersonMedicalInfoDTO();
                                personMedicalInfoDTO.setFirstname(person.getFirstName());
                                personMedicalInfoDTO.setLastname(person.getLastName());
                                personMedicalInfoDTO.setPhone(person.getPhone());
                                personMedicalInfoDTO.setAge(Utils.calculateAge(personMedicalRecord.getBirthdate()));
                                Map<String, List<String>> antecedents = new HashMap<>();
                                antecedents.put("medications", personMedicalRecord.getMedications());
                                antecedents.put("allergies",personMedicalRecord.getAllergies());
                                personMedicalInfoDTO.setAntecedents(antecedents);
                                personsMedicalInfo.add(personMedicalInfoDTO);
                            });
                            PersonsCoveredAtAddress personsCoveredAtAddress = new PersonsCoveredAtAddress();
                            personsCoveredAtAddress.setAddress(address);
                            personsCoveredAtAddress.setStation(firestation.getStation());
                            personsCoveredAtAddress.setPersonsCovered(personsMedicalInfo);
                            lesPersonnesCouvertes.add(personsCoveredAtAddress);
                        }
                );
            }
        });
        return lesPersonnesCouvertes;
    }

}
