package com.poly.myapplication.data.models;

public class NotificationSender {
    public Bill bill;
    public String to;

    public NotificationSender(Bill bill, String to) {
        this.bill = bill;
        this.to = to;
    }

    public NotificationSender() {
    }
}
