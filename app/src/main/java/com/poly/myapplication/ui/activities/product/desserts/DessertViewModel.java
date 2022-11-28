package com.poly.myapplication.ui.activities.product.desserts;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.poly.myapplication.data.models.Product;
import com.poly.myapplication.data.retrofit.RetroInstance;
import com.poly.myapplication.data.retrofit.ServiceAPI;
import com.poly.myapplication.ui.base.BaseViewModel;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DessertViewModel extends BaseViewModel {
    public MutableLiveData<List<Product>> mListDessertLiveData;

    public DessertViewModel(Context context) {
        super(context);
        mListDessertLiveData = new MutableLiveData<>();
    }

    public void callToGetDessert(){
        ServiceAPI serviceAPI = RetroInstance.getRetrofitInstance().create(ServiceAPI.class);
        Observable<List<Product>> mListProductObservable = serviceAPI.getProductByCategory(3);
        mListProductObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onRetrieveProductListSuccess, this::handleErrors);
    }

    private void onRetrieveProductListSuccess(List<Product> result) {
        mListDessertLiveData.postValue(result);
    }

    private void handleErrors(Throwable throwable) {
        Log.e("TAG", "handleErrors: "+ throwable.getMessage());
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

    public void deleteProduct(Product product){
        appExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                productDao.deleteProduct(product);
            }
        });
    }

    public String getProductById(String id){
        return productDao.findProductById(id);
    }

    public List<Product> getListProduct(){
        return productDao.getListProducts();
    }
}