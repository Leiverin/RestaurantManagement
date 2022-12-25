package com.poly.restaurant.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "Product")
public class Product implements Parcelable {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private Long idProduct;
    @SerializedName("_id")
    private String id;
    private String name;
    private String urlImage;
    private double price;
    private String description;
    private int total;
    private int amount;
    private int type;
    private String idCategory;
    private String idTable;

    public Product() {
    }

    public Product(Long idProduct, String id, String name, String urlImage, double price, String description, int total, int amount, int type, String idCategory, String idTable) {
        this.idProduct = idProduct;
        this.id = id;
        this.name = name;
        this.urlImage = urlImage;
        this.price = price;
        this.description = description;
        this.total = total;
        this.amount = amount;
        this.type = type;
        this.idCategory = idCategory;
        this.idTable = idTable;
    }

    protected Product(Parcel in) {
        if (in.readByte() == 0) {
            idProduct = null;
        } else {
            idProduct = in.readLong();
        }
        id = in.readString();
        name = in.readString();
        urlImage = in.readString();
        price = in.readDouble();
        description = in.readString();
        total = in.readInt();
        amount = in.readInt();
        type = in.readInt();
        idCategory = in.readString();
        idTable = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (idProduct == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(idProduct);
        }
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(urlImage);
        dest.writeDouble(price);
        dest.writeString(description);
        dest.writeInt(total);
        dest.writeInt(amount);
        dest.writeInt(type);
        dest.writeString(idCategory);
        dest.writeString(idTable);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    @NonNull
    public Long getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(@NonNull Long idProduct) {
        this.idProduct = idProduct;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getIdTable() {
        return idTable;
    }

    public void setIdTable(String idTable) {
        this.idTable = idTable;
    }
}
