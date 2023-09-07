package org.ordermgmt.model;

public class Address {
    private String address;
    private int zipCode;
    private String state;
    private String country;

    public Address(String address, int zipCode, String state, String country) {
        this.address = address;
        this.zipCode = zipCode;
        this.state = state;
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public int getZipCode() {
        return zipCode;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }
}
