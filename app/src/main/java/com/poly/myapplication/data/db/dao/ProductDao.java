package com.poly.myapplication.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.poly.myapplication.data.models.Bill;
import com.poly.myapplication.data.models.Product;

import java.util.List;

@Dao
public interface ProductDao {

    @Query("SELECT * FROM Product")
    LiveData<List<Product>> getProducts();

    @Query("SELECT * FROM Product")
    List<Product> getListProducts();

    @Insert
    void insertProduct(Product product);

    @Update
    void updateProduct(Product product);

    @Query("SELECT id FROM Product WHERE id = :id")
    String findProductById(String id);

    @Query("SELECT * FROM Product WHERE idTable = :idTable")
    List<Product> getProductByIdTable(String idTable);

    @Delete
    void deleteProduct(Product product);

}
