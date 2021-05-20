package com.safetynet.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class Person {
    //@NotNull
    private int id;
    //@NotBlank(message = "le prénom est obligatoire")
    private String lastName;
    //@NotBlank(message = "le nom est est obligatoire")
    private String firstName;
    private String address;
    private String city;
    private String zip;
    private String phone;
    //@NotBlank(message = "l'email est requis")
    private String email;

    public Person(int id, String fname, String lname, String address, String city, String zip, String phone, String email) {
        this.setId(id);
        this.setLastName(lname);
        this.setFirstName(fname);
        this.setAddress(address);
        this.setCity(city);
        this.setZip(zip);
        this.setPhone(phone);
        this.setEmail(email);
    }

    public Person() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Person other = (Person) obj;
        if (firstName == null) {
            if (other.firstName != null)
                return false;
        } else if (!firstName.equals(other.firstName))
            return false;
        if (lastName == null) {
            return other.lastName == null;
        } else return lastName.equals(other.lastName);
    }

    @Override
    public String toString() {
        return "Person [lastName=" + lastName + ", firstName=" + firstName + ", address=" + address + ", city=" + city
                + ", zip=" + zip + ", phone=" + phone + ", email=" + email + "]";
    }
}
