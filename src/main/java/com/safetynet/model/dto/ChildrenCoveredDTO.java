package com.safetynet.model.dto;

import com.safetynet.model.Person;

import java.util.List;

public class ChildrenCoveredDTO {
    List<ChildDTO> enfants;
    List<Person> autresMembres;

    public List<ChildDTO> getEnfants() {
        return enfants;
    }

    public void setEnfants(List<ChildDTO> enfants) {
        this.enfants = enfants;
    }

    public List<Person> getAutresMembres() {
        return autresMembres;
    }

    public void setAutresMembres(List<Person> autresMembres) {
        this.autresMembres = autresMembres;
    }
}
