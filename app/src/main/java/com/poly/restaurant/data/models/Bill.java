package com.poly.restaurant.data.models;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Bill implements Parcelable {
    @SerializedName("_id")
    private String id;
    private String date;
    private String time;
    private double totalPrice;
    private int checkoutType;
    private int status;
    private List<Product> foods;
    private Table table;
    private List<Table> tables;
    private String idCustomer;
    private Staff staff;
    private List<Staff> staffs;

    public Bill() {
    }

    public Bill(String time, String id) {
        this.time = time;
        this.id = id;
    }

    public Bill(String id, String date, String time, double totalPrice, int checkoutType, int status, List<Product> foods, Table table, List<Table> tables, String idCustomer, Staff staff, List<Staff> staffs) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.totalPrice = totalPrice;
        this.checkoutType = checkoutType;
        this.status = status;
        this.foods = foods;
        this.table = table;
        this.tables = tables;
        this.idCustomer = idCustomer;
        this.staff = staff;
        this.staffs = staffs;
    }

    protected Bill(Parcel in) {
        id = in.readString();
        date = in.readString();
        time = in.readString();
        totalPrice = in.readDouble();
        checkoutType = in.readInt();
        status = in.readInt();
        foods = in.createTypedArrayList(Product.CREATOR);
        table = in.readParcelable(Table.class.getClassLoader());
        tables = in.createTypedArrayList(Table.CREATOR);
        idCustomer = in.readString();
        staff = in.readParcelable(Staff.class.getClassLoader());
        staffs = in.createTypedArrayList(Staff.CREATOR);
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
        return foods;
    }

    public void setProducts(List<Product> foods) {
        this.foods = foods;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public String getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(String idCustomer) {
        this.idCustomer = idCustomer;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public List<Table> getTables() {
        return tables;
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
    }

    public List<Staff> getStaffs() {
        return staffs;
    }

    public void setStaffs(List<Staff> staffs) {
        this.staffs = staffs;
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
        parcel.writeTypedList(foods);
        parcel.writeParcelable(table, i);
        parcel.writeTypedList(tables);
        parcel.writeString(idCustomer);
        parcel.writeParcelable(staff, i);
        parcel.writeTypedList(staffs);
    }

    @Override
    public String toString() {
        return "Bill{" +
                "id='" + id + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", totalPrice=" + totalPrice +
                ", checkoutType=" + checkoutType +
                ", status=" + status +
                ", table=" + table +
                ", idCustomer='" + idCustomer + '\'' +
                ", staff=" + staff +
                '}';
    }
}
