package com.safetynet.model.dto;

import com.safetynet.model.Person;

public class ChildDTO {
    Person person;
    int age;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
