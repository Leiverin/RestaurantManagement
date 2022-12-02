package com.poly.restaurant.ui.activities.product.drinks;

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

public class DrinksViewModel extends BaseViewModel {
    public MutableLiveData<List<Product>> mListDrinkLiveData;

    public DrinksViewModel(Context context) {
        super(context);
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