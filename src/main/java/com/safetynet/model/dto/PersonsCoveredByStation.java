package com.safetynet.model.dto;

import com.safetynet.model.Person;

import java.util.List;

public class PersonsCoveredByStation {
    int stationId;
    List<PersonInfoDTO> persons;
    int nombreAdults;
    int nombreEnfants;

    public int getStationId() {
        return stationId;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    public List<PersonInfoDTO> getPersons() {
        return persons;
    }

    public void setPersons(List<PersonInfoDTO> persons) {
        this.persons = persons;
    }

    public int getNombreAdults() {
        return nombreAdults;
    }

    public void setNombreAdults(int nombreAdults) {
        this.nombreAdults = nombreAdults;
    }

    public int getNombreEnfants() {
        return nombreEnfants;
    }

    public void setNombreEnfants(int nombreEnfants) {
        this.nombreEnfants = nombreEnfants;
    }
}
