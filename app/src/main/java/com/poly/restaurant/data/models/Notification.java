package com.poly.restaurant.data.models;

public class Notification {
    private String title;
    private String content;
    private String date;
    private String time;
    private Staff sender,receiver;
    private String idBill;

    public Notification() {
    }

    public Notification(String title, String content, String time) {
        this.title = title;
        this.content = content;
        this.time = time;
    }

    public Notification(String title, String content, String date, String time, Staff sender, Staff receiver, String idBill) {
        this.title = title;
        this.content = content;
        this.date = date;
        this.time = time;
        this.sender = sender;
        this.receiver = receiver;
        this.idBill = idBill;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public Staff getSender() {
        return sender;
    }

    public void setSender(Staff sender) {
        this.sender = sender;
    }

    public Staff getReceiver() {
        return receiver;
    }

    public void setReceiver(Staff receiver) {
        this.receiver = receiver;
    }

    public String getIdBill() {
        return idBill;
    }

    public void setIdBill(String idBill) {
        this.idBill = idBill;
    }
}
