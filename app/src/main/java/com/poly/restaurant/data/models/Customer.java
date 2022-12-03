package com.poly.restaurant.data.models;

public class Customer {
    private String id;
    private String phoneNumber;
    private String name;
    private String date;
    private int offer;

    public Customer() {
    }

    public Customer(String id, String phoneNumber, String name, String date, int offer) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.date = date;
        this.offer = offer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getOffer() {
        return offer;
    }

    public void setOffer(int offer) {
        this.offer = offer;
    }
}
