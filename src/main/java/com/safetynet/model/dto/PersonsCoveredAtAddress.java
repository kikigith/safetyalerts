package com.safetynet.model.dto;

import java.util.List;

public class PersonsCoveredAtAddress {
    String address;
    int station;
    List<PersonMedicalInfoDTO> personsCovered;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getStation() {
        return station;
    }

    public void setStation(int station) {
        this.station = station;
    }

    public List<PersonMedicalInfoDTO> getPersonsCovered() {
        return personsCovered;
    }

    public void setPersonsCovered(List<PersonMedicalInfoDTO> personsCovered) {
        this.personsCovered = personsCovered;
    }
}
