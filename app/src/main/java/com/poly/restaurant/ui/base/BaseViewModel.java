package com.poly.restaurant.ui.base;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.poly.restaurant.data.db.AppDatabase;
import com.poly.restaurant.data.db.dao.ProductDao;
import com.poly.restaurant.data.models.Product;
import com.poly.restaurant.utils.helps.AppExecutors;

import java.util.List;

public abstract class BaseViewModel extends ViewModel {
    protected ProductDao productDao;
    protected AppExecutors appExecutors;

    protected BaseViewModel(Context context) {
        productDao = AppDatabase.getInstance(context).productDao();
        appExecutors = new AppExecutors();
    }

    public List<Product> getListProductByIdTable(String idTable){
        return productDao.getProductByIdTable(idTable);
    }

    public LiveData<List<Product>> getLocalProductsLiveData(){
        return productDao.getProducts();
    }

    public void insertProduct(Product product){
        appExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                productDao.insertProduct(product);
            }
        });
    }

    public void updateProduct(Product product){
        appExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                productDao.updateProduct(product);
            }
        });
    }

    public void updateAmountProduct(int amount, String id, String idTable){
        appExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                productDao.updateAmount(amount, id, idTable);
            }
        });
    }

    public void deleteProduct(Product product){
        appExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                productDao.deleteProduct(product);
            }
        });
    }

    public List<Product> getListProduct(){
        return productDao.getListProducts();
    }

    public String getProductById(String id, String idTable){
        return productDao.findProductById(id, idTable);
    }


}