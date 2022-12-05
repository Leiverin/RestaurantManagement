package com.poly.restaurant.data.models;

import com.google.gson.annotations.SerializedName;

public class Staff {
    @SerializedName("_id")
    private String id;
    private String name;
    private String phoneNumber;
    private int gender;
    private String account;
    private String password;
    private int role;
    private Floor floor;
    private String tokenFCM;

    public Staff() {
    }

    public Staff(String id, String name, String phoneNumber, int gender, String account, String password, int role, Floor floor, String tokenFCM) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.account = account;
        this.password = password;
        this.role = role;
        this.floor = floor;
        this.tokenFCM = tokenFCM;
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

    public String getTokenFCM() {
        return tokenFCM;
    }

    public void setTokenFCM(String tokenFCM) {
        this.tokenFCM = tokenFCM;
    }
}
