package com.poly.restaurant.ui.activities.product;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.poly.restaurant.data.models.Product;
import com.poly.restaurant.ui.base.BaseViewModel;

import java.util.List;

public class FoodViewModel extends BaseViewModel {

    public FoodViewModel(Context context) {
        super(context);
    }

    public LiveData<List<Product>> getLocalProductsLiveData(){
        return productDao.getProducts();
    }

    public List<Product> getLocalProducts(){
        return productDao.getListProducts();
    }

    public LiveData<List<Product>> getListProductByIdTableLive(String idTable){
        return productDao.getProductByIdTableLiveData(idTable);
    }
}
