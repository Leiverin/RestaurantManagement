package com.poly.myapplication.data.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "Product")
public class Product {
    @NonNull
    @SerializedName("_id")
    @PrimaryKey(autoGenerate = false)
    private String id;
    private String name;
    private String urlImage;
    private double price;
    private int total;
    private int amount;
    private int type;
    private String idCategory;

    public Product() {
    }

    public Product(String id, String name, String urlImage, double price, int total, int amount, int type, String idCategory) {
        this.id = id;
        this.name = name;
        this.urlImage = urlImage;
        this.price = price;
        this.total = total;
        this.amount = amount;
        this.type = type;
        this.idCategory = idCategory;
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

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(String idCategory) {
        this.idCategory = idCategory;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
