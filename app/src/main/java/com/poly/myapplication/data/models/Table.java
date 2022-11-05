package com.poly.myapplication.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Table implements Parcelable {
    @SerializedName("_id")
    private String id;
    private String name;
    private String capacity;
    private int status;
    private List<Staff> staffs;

    public Table() {
    }

    public Table(String id, String name, String capacity, int status, List<Staff> staffs) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.status = status;
        this.staffs = staffs;
    }

    protected Table(Parcel in) {
        id = in.readString();
        name = in.readString();
        capacity = in.readString();
        status = in.readInt();
    }

    public static final Creator<Table> CREATOR = new Creator<Table>() {
        @Override
        public Table createFromParcel(Parcel in) {
            return new Table(in);
        }

        @Override
        public Table[] newArray(int size) {
            return new Table[size];
        }
    };

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

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Staff> getStaffs() {
        return staffs;
    }

    public void setStaffs(List<Staff> staffs) {
        this.staffs = staffs;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(capacity);
        parcel.writeInt(status);
    }
}
