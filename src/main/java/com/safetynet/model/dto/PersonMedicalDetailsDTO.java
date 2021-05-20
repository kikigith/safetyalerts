package com.safetynet.model.dto;

import java.util.List;
import java.util.Map;

public class PersonMedicalDetailsDTO {

    String firstname;
    String lastname;
    String address;
    String email;
    int age;
    Map<String, List<String>> antecedents;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Map<String, List<String>> getAntecedents() {
        return antecedents;
    }

    public void setAntecedents(Map<String, List<String>> antecedents) {
        this.antecedents = antecedents;
    }
}
