package com.poly.restaurant.data.models;

import com.google.gson.annotations.SerializedName;

public class Feedback {
    @SerializedName("_id")
    private String id;
    private int status;
    private int title;
    private String content;
    private Table table;
    private Staff staff;
    private String date;
    private String time;
    private String idBill;

    public Feedback(String id, int status, int title, String content, Table table, Staff staff, String date, String time, String idBill) {
        this.id = id;
        this.status = status;
        this.title = title;
        this.content = content;
        this.table = table;
        this.staff = staff;
        this.date = date;
        this.time = time;
        this.idBill = idBill;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
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

    public String getIdBill() {
        return idBill;
    }

    public void setIdBill(String idBill) {
        this.idBill = idBill;
    }
}
