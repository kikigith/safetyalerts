package com.safetynet.model;

import java.util.Objects;

public class Firestation {
    private int id;
    private int station;
    private String address;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStation() {
        return station;
    }

    public void setStation(int station) {
        this.station = station;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Firestation that = (Firestation) o;
        return station == that.station && Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(station, address);
    }

    @Override
    public String toString() {
        return "Firestation [station=" + station + ", address=" + address + "]";
    }
}
