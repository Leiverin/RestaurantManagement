package com.poly.myapplication.data.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.poly.myapplication.data.models.Bill;

import java.util.List;

@Dao
public interface BillDao {

    @Query("SELECT * FROM Bill")
    List<Bill> getListBill();

    @Insert
    void insertBill(Bill bill);

    @Update
    void updateBill(Bill bill);
}
