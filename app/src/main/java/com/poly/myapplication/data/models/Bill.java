package com.poly.myapplication.data.models;

import java.util.List;

public class Bill {
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
}
