package com.poly.myapplication.ui.activities.product.drinks;

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

public class DrinksViewModel extends ViewModel {
    public MutableLiveData<List<Product>> mListDrinkLiveData;

    public DrinksViewModel() {
        mListDrinkLiveData = new MutableLiveData<>();
    }

    public void callToGetDrink(){
        ServiceAPI serviceAPI = RetroInstance.getRetrofitInstance().create(ServiceAPI.class);
        Observable<List<Product>> mListDrinkObservable = serviceAPI.getProductByCategory(4);
        mListDrinkObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onRetrieveDrinkListSuccess, this::handleErrors);
    }

    private void onRetrieveDrinkListSuccess(List<Product> result) {
        mListDrinkLiveData.postValue(result);
    }

    private void handleErrors(Throwable throwable) {
        Log.e("TAG", "handleErrors: "+ throwable.getMessage());
    }
}