package com.poly.restaurant.ui.activities.table;

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

public class TableDetailViewModel extends BaseViewModel {
    public MutableLiveData<List<Product>> mListProductLiveData;
    public MutableLiveData<Boolean> wasCreated;

    public TableDetailViewModel(Context context) {
        super(context);
        mListProductLiveData = new MutableLiveData<>();
        wasCreated = new MutableLiveData<>();
    }

    void getListProductInBill(String idTable){
        ServiceAPI serviceAPI = RetroInstance.getRetrofitInstance().create(ServiceAPI.class);
        Observable<List<Product>> mBillObservable = serviceAPI.getListProductInBill(idTable);
        mBillObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::onRetrieveBillSuccess, this::handleErrorsProduct);
    }

    private void onRetrieveBillSuccess(List<Product> products) {
        mListProductLiveData.postValue(products);
    }

    private void handleErrorsProduct(Throwable throwable) {
        mListProductLiveData.postValue(null);
        Log.e("TAG", "handleErrors: "+ throwable.getMessage());
    }


    public LiveData<List<Product>> getListProductByIdTableLive(String idTable){
        return productDao.getProductByIdTableLiveData(idTable);
    }

    // Create bill
    public void callToCreateBill(Bill bill){
        ServiceAPI serviceAPI = RetroInstance.getRetrofitInstance().create(ServiceAPI.class);
        Observable<Bill> mListDrinkObservable = serviceAPI.createBill(bill);
        mListDrinkObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onRetrieveDrinkListSuccess, this::handleErrors);
    }

    private void onRetrieveDrinkListSuccess(Bill result) {
        wasCreated.postValue(true);
    }

    private void handleErrors(Throwable throwable) {
        wasCreated.postValue(false);
        Log.e("TAG", "Error in create bill: "+ throwable.getMessage());
    }
}
