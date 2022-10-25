package com.poly.myapplication.data.models;

import java.util.List;

public class Table {
    private String id;
    private String name;
    private int status;
    private List<Staff> staffs;

    public Table() {
    }

    public Table(String id, String name, int status, List<Staff> staffs) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.staffs = staffs;
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
}
