package com.poly.restaurant.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Floor implements Parcelable {
    @SerializedName("_id")
    private String id;
    private int numberFloor;

    public Floor(String id, int numberFloor) {
        this.id = id;
        this.numberFloor = numberFloor;
    }

    protected Floor(Parcel in) {
        id = in.readString();
        numberFloor = in.readInt();
    }

    public static final Creator<Floor> CREATOR = new Creator<Floor>() {
        @Override
        public Floor createFromParcel(Parcel in) {
            return new Floor(in);
        }

        @Override
        public Floor[] newArray(int size) {
            return new Floor[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeInt(numberFloor);
    }
}
