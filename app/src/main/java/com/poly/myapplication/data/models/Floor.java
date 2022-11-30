package com.poly.myapplication.data.models;

import com.google.gson.annotations.SerializedName;

public class Floor {
    @SerializedName("_id")
    private String id;
    private int numberFloor;

    public Floor(String id, int numberFloor) {
        this.id = id;
        this.numberFloor = numberFloor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNumberFloor() {
        return numberFloor;
    }

    public void setNumberFloor(int numberFloor) {
        this.numberFloor = numberFloor;
    }
}
