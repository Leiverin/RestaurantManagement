package com.poly.myapplication.ui.base;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.poly.myapplication.data.db.AppDatabase;
import com.poly.myapplication.data.db.dao.ProductDao;
import com.poly.myapplication.utils.helps.AppExecutors;

import java.util.concurrent.Executors;

public abstract class BaseViewModel extends ViewModel {
    protected ProductDao productDao;
    protected AppExecutors appExecutors;

    protected BaseViewModel(Context context) {
        productDao = AppDatabase.getInstance(context).productDao();
        appExecutors = new AppExecutors();
    }



}
