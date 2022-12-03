package com.poly.restaurant.data.models;

public class Staff {
    private String id;
    private String name;
    private String phoneNumber;
    private int gender;
    private String account;
    private String password;
    private int role;
    private Floor floor;

    public Staff() {
    }

    public Staff(String id, String name, String phoneNumber, int gender, String account, String password, int role, Floor floor) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.account = account;
        this.password = password;
        this.role = role;
        this.floor = floor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public Floor getFloor() {
        return floor;
    }

    public void setFloor(Floor floor) {
        this.floor = floor;
    }

    public Floor getIdFloor() {
        return floor;
    }

    public void setIdFloor(Floor floor) {
        this.floor = floor;
    }
}
