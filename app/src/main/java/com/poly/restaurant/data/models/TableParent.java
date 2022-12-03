package com.poly.restaurant.data.models;

import java.util.List;

public class TableParent {
    private String title;
    private List<Table> tables;

    public TableParent() {
    }

    public TableParent(String title, List<Table> tables) {
        this.title = title;
        this.tables = tables;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Table> getTables() {
        return tables;
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
    }
}
