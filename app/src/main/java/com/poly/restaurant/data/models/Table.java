package com.poly.restaurant.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;
@Entity(tableName = "Table")
public class Table implements Parcelable {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private Long idTable;
    @SerializedName("_id")
    private String id;
    private String name;
    private int floor;
    private String capacity;
    private int status;

    public Table() {
    }

    public Table(@NonNull Long idTable, String id, String name, int floor, String capacity, int status) {
        this.idTable = idTable;
        this.id = id;
        this.name = name;
        this.floor = floor;
        this.capacity = capacity;
        this.status = status;
    }

    public Table(String id, String name, int floor, String capacity, int status) {
        this.id = id;
        this.name = name;
        this.floor = floor;
        this.capacity = capacity;
        this.status = status;
    }

    protected Table(Parcel in) {
        id = in.readString();
        name = in.readString();
        floor = in.readInt();
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

    @NonNull
    public Long getIdTable() {
        return idTable;
    }

    public void setIdTable(@NonNull Long idTable) {
        this.idTable = idTable;
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

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeInt(floor);
        parcel.writeString(capacity);
        parcel.writeInt(status);
    }

    @Override
    public String toString() {
        return "Table{" +
                "idTable=" + idTable +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", floor=" + floor +
                ", capacity='" + capacity + '\'' +
                ", status=" + status +
                '}';
    }
}
