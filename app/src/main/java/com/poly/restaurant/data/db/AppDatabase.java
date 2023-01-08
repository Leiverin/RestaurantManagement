package com.poly.restaurant.data.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.poly.restaurant.data.db.dao.ProductDao;
import com.poly.restaurant.data.db.dao.TableDao;
import com.poly.restaurant.data.models.Product;
import com.poly.restaurant.data.models.Table;

@Database(entities = {Product.class, Table.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public static final String DB_NAME = "restaurant.db";
    public static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context, AppDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries().build();
        }
        return instance;
    }
    public abstract ProductDao productDao();
    public abstract TableDao tableDao();
}
