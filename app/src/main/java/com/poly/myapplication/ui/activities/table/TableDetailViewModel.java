package com.poly.myapplication.ui.activities.table;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.poly.myapplication.data.models.Bill;
import com.poly.myapplication.data.models.Product;
import com.poly.myapplication.data.retrofit.RetroInstance;
import com.poly.myapplication.data.retrofit.ServiceAPI;
import com.poly.myapplication.ui.base.BaseViewModel;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TableDetailViewModel extends BaseViewModel {
    public MutableLiveData<List<Product>> mListProductLiveData;
    public MutableLiveData<Boolean> wasCreated;
    public MutableLiveData<List<Bill>> mBillLiveData;
    public MutableLiveData<Boolean> wasUpdated;

    public TableDetailViewModel(Context context) {
        super(context);
        mListProductLiveData = new MutableLiveData<>();
        wasCreated = new MutableLiveData<>();
        mBillLiveData = new MutableLiveData<>();
        wasUpdated = new MutableLiveData<>();
    }

    void getListProductInBill(String idTable){
        ServiceAPI serviceAPI = RetroInstance.getRetrofitInstance().create(ServiceAPI.class);
        Observable<List<Product>> mBillObservable = serviceAPI.getListProductInBill(idTable);
        mBillObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::onRetrieveBillListSuccess, this::handleErrorsProduct);
    }

    private void onRetrieveBillListSuccess(List<Product> products) {
        mListProductLiveData.postValue(products);
    }

    private void handleErrorsProduct(Throwable throwable) {
        mListProductLiveData.postValue(null);
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
    }

    // Check bill exists
    public void callToGetBillExist(String idTable){
        ServiceAPI serviceAPI = RetroInstance.getRetrofitInstance().create(ServiceAPI.class);
        Observable<List<Bill>> mListDrinkObservable = serviceAPI.getBillIfExists(idTable);
        mListDrinkObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onRetrieveBillSuccess, this::handleErrorsGetBill);
    }

    private void onRetrieveBillSuccess(List<Bill> result) {
        mBillLiveData.postValue(result);
    }

    private void handleErrorsGetBill(Throwable throwable) {
        mBillLiveData.postValue(null);
    }

    // Check bill exists
    public void callToUpdateBill(String id, Bill bill){
        ServiceAPI serviceAPI = RetroInstance.getRetrofitInstance().create(ServiceAPI.class);
        Observable<Bill> mListDrinkObservable = serviceAPI.updateBillById(id, bill, "PUT");
        mListDrinkObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onRetrieveUpdateBillSuccess, this::handleErrorsUpdateBill);
    }

    private void onRetrieveUpdateBillSuccess(Bill result) {
        wasUpdated.postValue(true);
    }

    private void handleErrorsUpdateBill(Throwable throwable) {
        wasUpdated.postValue(false);
        Log.d("TAG", "handleErrorsUpdateBill: "+ throwable.getMessage());
    }

}
