package com.poly.myapplication.data.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.poly.myapplication.data.models.Bill;
import com.poly.myapplication.data.models.Product;

import java.util.List;

@Dao
public interface ProductDao {

    @Query("SELECT * FROM Product")
    List<Product> getProducts();

    @Insert
    void insertBill(Product product);

    @Update
    void updateBill(Product product);
}
