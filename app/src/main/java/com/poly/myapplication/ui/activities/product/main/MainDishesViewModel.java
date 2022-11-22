package com.poly.myapplication.ui.activities.product.main;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.poly.myapplication.data.models.Product;
import com.poly.myapplication.data.retrofit.RetroInstance;
import com.poly.myapplication.data.retrofit.ServiceAPI;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainDishesViewModel extends ViewModel {
    public MutableLiveData<List<Product>> mListProductLiveData;

    public MainDishesViewModel() {
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
}