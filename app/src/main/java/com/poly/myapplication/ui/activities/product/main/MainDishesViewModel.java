package com.poly.myapplication.ui.activities.product.main;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.poly.myapplication.data.models.Product;
import com.poly.myapplication.data.retrofit.RetroInstance;
import com.poly.myapplication.data.retrofit.ServiceAPI;
import com.poly.myapplication.ui.base.BaseViewModel;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainDishesViewModel extends BaseViewModel {
    public MutableLiveData<List<Product>> mListProductLiveData;

    public MainDishesViewModel(Context context) {
        super(context);
        mListProductLiveData = new MutableLiveData<>();
    }

    public void callToGetDrink(){
        ServiceAPI serviceAPI = RetroInstance.getRetrofitInstance().create(ServiceAPI.class);
        Observable<List<Product>> mListProductObservable = serviceAPI.getProductByCategory(2);
        mListProductObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onRetrieveProductListSuccess, this::handleErrors);
    }

    private void onRetrieveProductListSuccess(List<Product> result) {
        mListProductLiveData.postValue(result);
    }

    private void handleErrors(Throwable throwable) {
        Log.e("TAG", "handleErrors: "+ throwable.getMessage());
    }

    public List<Product> getListProduct(){
        return productDao.getListProducts();
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

    public LiveData<List<Product>> getLocalProductsLiveData(){
        return productDao.getProducts();
    }

}