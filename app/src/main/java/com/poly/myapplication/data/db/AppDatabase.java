package com.poly.myapplication.data.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.poly.myapplication.data.db.dao.BillDao;
import com.poly.myapplication.data.models.Bill;
import com.poly.myapplication.data.models.Product;

@Database(entities = {Bill.class, Product.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public static final String DB_NAME = "restaurant.db";
    public static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context, AppDatabase.class, DB_NAME)
                    .allowMainThreadQueries().build();
        }
        return instance;
    }
    public abstract BillDao billDao();
}
