package com.poly.restaurant.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.poly.restaurant.data.models.Table;

import java.util.List;

@Dao
public interface TableDao {
    @Query("SELECT * FROM `Table`")
    LiveData<List<Table>> getListTable();

    @Insert
    void insertTable(Table table);

    @Update
    void updateTable(Table table);

    @Query("DELETE FROM `Table` WHERE id = :id")
    void deleteTable(String id);

}
