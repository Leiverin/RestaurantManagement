package com.poly.restaurant.ui.activities.product.main;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.poly.restaurant.data.models.Product;
import com.poly.restaurant.data.retrofit.RetroInstance;
import com.poly.restaurant.data.retrofit.ServiceAPI;
import com.poly.restaurant.ui.base.BaseViewModel;

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
}