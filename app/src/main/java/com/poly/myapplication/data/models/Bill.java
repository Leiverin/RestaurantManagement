package com.poly.myapplication.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Bill implements Parcelable {
    private String id;
    private String date;
    private String time;
    private double totalPrice;
    private int checkoutType;
    private int status;
    private List<Product> products;
    private String idTable;
    private String idCustomer;

    public Bill() {
    }

    public Bill(String id, String date, String time, double totalPrice, int checkoutType, int status, List<Product> products, String idTable, String idCustomer) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.totalPrice = totalPrice;
        this.checkoutType = checkoutType;
        this.status = status;
        this.products = products;
        this.idTable = idTable;
        this.idCustomer = idCustomer;
    }

    public Bill(String idTable, double totalPrice, String time, String date) {
        this.idTable = idTable;
        this.totalPrice = totalPrice;
        this.time = time;
        this.date = date;
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
    }
}
