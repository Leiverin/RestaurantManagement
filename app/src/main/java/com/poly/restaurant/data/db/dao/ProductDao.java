package com.poly.restaurant.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.poly.restaurant.data.models.Product;

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

    @Query("UPDATE Product SET amount = :amount WHERE id = :id AND idTable = :idTable")
    void updateAmount(int amount, String id, String idTable);

    @Query("DELETE FROM Product WHERE id = :id AND idTable = :idTable")
    void deleteProduct(String id, String idTable);

    @Query("SELECT id FROM Product WHERE id = :id AND idTable = :idTable")
    String findProductById(String id, String idTable);

    @Query("SELECT * FROM Product WHERE idTable = :idTable")
    List<Product> getProductByIdTable(String idTable);

    @Query("SELECT * FROM Product WHERE idTable = :idTable")
    LiveData<List<Product>> getProductByIdTableLiveData(String idTable);

    @Delete
    void deleteProduct(Product product);

    @Query("DELETE FROM Product")
    void deleteAllProduct();

    @Query("UPDATE Product SET STATUS = :status WHERE id = :id AND idTable = :idTable")
    void updateStatusProductInBill(int status, String id, String idTable);

    @Query("UPDATE Product SET idTable = :idTable WHERE id = :id")
    void updateProductMerge(String idTable, String id);

}
