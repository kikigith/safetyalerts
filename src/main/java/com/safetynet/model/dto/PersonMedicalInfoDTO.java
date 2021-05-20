package com.safetynet.model.dto;

import java.util.List;
import java.util.Map;

public class PersonMedicalInfoDTO {
    String firstname;
    String lastname;
    String phone;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Map<String,List<String>> getAntecedents() {
        return antecedents;
    }

    public void setAntecedents(Map<String, List<String>> antecedents) {
        this.antecedents = antecedents;
    }
}
