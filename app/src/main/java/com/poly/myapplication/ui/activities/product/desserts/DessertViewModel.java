package com.poly.myapplication.ui.activities.product.desserts;

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

public class DessertViewModel extends ViewModel {
    public MutableLiveData<List<Product>> mListDessertLiveData;

    public DessertViewModel() {
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
}