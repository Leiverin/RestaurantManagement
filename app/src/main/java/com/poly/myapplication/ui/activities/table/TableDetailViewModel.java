package com.poly.myapplication.ui.activities.table;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.poly.myapplication.data.models.Bill;
import com.poly.myapplication.data.models.Product;
import com.poly.myapplication.data.retrofit.RetroInstance;
import com.poly.myapplication.data.retrofit.ServiceAPI;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TableDetailViewModel extends ViewModel {
    public MutableLiveData<List<Product>> mListProductLiveData;

    public TableDetailViewModel() {
        mListProductLiveData = new MutableLiveData<>();
    }

    void getListProductInBill(String idTable){
        ServiceAPI serviceAPI = RetroInstance.getRetrofitInstance().create(ServiceAPI.class);
        Observable<List<Product>> mBillObservable = serviceAPI.getListProductInBill(idTable);
        mBillObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::onRetrieveBillSuccess, this::handleErrors);
    }

    private void onRetrieveBillSuccess(List<Product> products) {
        mListProductLiveData.postValue(products);
    }

    private void handleErrors(Throwable throwable) {
        mListProductLiveData.postValue(null);
        Log.e("TAG", "handleErrors: "+ throwable.getMessage());
    }
}
