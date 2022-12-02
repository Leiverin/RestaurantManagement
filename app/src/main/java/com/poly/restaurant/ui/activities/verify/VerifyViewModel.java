package com.poly.restaurant.ui.activities.verify;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.poly.restaurant.data.models.Bill;
import com.poly.restaurant.data.models.Product;
import com.poly.restaurant.data.retrofit.RetroInstance;
import com.poly.restaurant.data.retrofit.ServiceAPI;
import com.poly.restaurant.ui.base.BaseViewModel;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class VerifyViewModel extends BaseViewModel {
    private MutableLiveData<Boolean> isSuccessfullyLiveData;

    public VerifyViewModel(Context context) {
        super(context);
        isSuccessfullyLiveData = new MutableLiveData<>();
    }

    public LiveData<List<Product>> getListProductByIdTableLive(String idTable){
        return productDao.getProductByIdTableLiveData(idTable);
    }

    public void callToCreateBill(Bill bill){
        ServiceAPI serviceAPI = RetroInstance.getRetrofitInstance().create(ServiceAPI.class);
        Observable<Bill> mListDrinkObservable = serviceAPI.createBill(bill);
        mListDrinkObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onRetrieveDrinkListSuccess, this::handleErrors);
    }

    private void onRetrieveDrinkListSuccess(Bill result) {
        isSuccessfullyLiveData.postValue(true);
    }

    private void handleErrors(Throwable throwable) {
        isSuccessfullyLiveData.postValue(false);
        Log.e("TAG", "handleErrors: "+ throwable.getMessage());
    }
}
