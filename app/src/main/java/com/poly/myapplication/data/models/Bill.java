package com.poly.myapplication.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Bill implements Parcelable {
    @SerializedName("_id")
    private String id = null;
    private String date = null;
    private String time = null;
    private double totalPrice = 0;
    private int checkoutType = 0;
    private int status = 0;
    @SerializedName("foods")
    private List<Product> products;
    private String idTable = null;
    private String idCustomer = null;
    private String idStaff = null;

    public Bill() {
    }

    public Bill(String id, String date, String time, double totalPrice, int checkoutType, int status, List<Product> products, String idTable, String idCustomer, String idStaff) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.totalPrice = totalPrice;
        this.checkoutType = checkoutType;
        this.status = status;
        this.products = products;
        this.idTable = idTable;
        this.idCustomer = idCustomer;
        this.idStaff = idStaff;
    }

    protected Bill(Parcel in) {
        id = in.readString();
        date = in.readString();
        time = in.readString();
        totalPrice = in.readDouble();
        checkoutType = in.readInt();
        status = in.readInt();
        idTable = in.readString();
        idCustomer = in.readString();
        idStaff = in.readString();
    }

    public static final Creator<Bill> CREATOR = new Creator<Bill>() {
        @Override
        public Bill createFromParcel(Parcel in) {
            return new Bill(in);
        }

        @Override
        public Bill[] newArray(int size) {
            return new Bill[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getCheckoutType() {
        return checkoutType;
    }

    public void setCheckoutType(int checkoutType) {
        this.checkoutType = checkoutType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getIdTable() {
        return idTable;
    }

    public void setIdTable(String idTable) {
        this.idTable = idTable;
    }

    public String getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(String idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getIdStaff() {
        return idStaff;
    }

    public void setIdStaff(String idStaff) {
        this.idStaff = idStaff;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(date);
        parcel.writeString(time);
        parcel.writeDouble(totalPrice);
        parcel.writeInt(checkoutType);
        parcel.writeInt(status);
        parcel.writeString(idTable);
        parcel.writeString(idCustomer);
        parcel.writeString(idStaff);
    }
}
