package com.poly.restaurant.ui.activities.table;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.poly.restaurant.data.models.Bill;
import com.poly.restaurant.data.models.Product;
import com.poly.restaurant.data.models.Table;
import com.poly.restaurant.data.retrofit.RetroInstance;
import com.poly.restaurant.data.retrofit.ServiceAPI;
import com.poly.restaurant.ui.base.BaseViewModel;
import com.poly.restaurant.utils.Constants;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TableDetailViewModel extends BaseViewModel {
    public MutableLiveData<List<Product>> mListProductLiveData;
    public MutableLiveData<Bill> wasBillCreated;
    public MutableLiveData<List<Bill>> mBillLiveData;
    public MutableLiveData<List<Bill>> mBilExist;
    public MutableLiveData<Boolean> wasUpdated;
    public MutableLiveData<Boolean> wasPushed;
    public MutableLiveData<Boolean> wasUpdatedTable;

    public TableDetailViewModel(Context context) {
        super(context);
        mListProductLiveData = new MutableLiveData<>();
        wasBillCreated = new MutableLiveData<>();
        mBillLiveData = new MutableLiveData<>();
        wasUpdated = new MutableLiveData<>();
        mBilExist = new MutableLiveData<>();
        wasPushed = new MutableLiveData<>();
        wasUpdatedTable = new MutableLiveData<>();
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
                .subscribe(this::onRetrieveBillSuccess, this::handleErrors);
    }

    private void onRetrieveBillSuccess(Bill result) {
        wasBillCreated.postValue(result);
    }

    private void handleErrors(Throwable throwable) {
        wasBillCreated.postValue(null);
    }

    // Check bill exists
    public void callToGetBillExist(String idTable, int type){
        ServiceAPI serviceAPI = RetroInstance.getRetrofitInstance().create(ServiceAPI.class);
        Observable<List<Bill>> mListDrinkObservable = serviceAPI.getBillIfExists(idTable);
        mListDrinkObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        result -> onRetrieveBillSuccess(result, type),
                        err -> handleErrorsGetBill(err, type)
                );
    }

    private void handleErrorsGetBill(Throwable err, int type) {
        if (type == Constants.TYPE_CLICK){
            mBillLiveData.postValue(null);
        }else{
            mBilExist.postValue(null);
        }
        Log.d("TAG", "handleErrorsGetBill: " + err.getMessage());
    }

    private void onRetrieveBillSuccess(List<Bill> result, int type) {
        if (type == Constants.TYPE_CLICK){
            mBillLiveData.postValue(result);
        }else{
            mBilExist.postValue(result);
        }
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

    // Push notification
    public void callToPushNotification(String token, String title, String content, String idBill){
        ServiceAPI serviceAPI = RetroInstance.getRetrofitInstance().create(ServiceAPI.class);
        Observable<Bill> mListDrinkObservable = serviceAPI.pushNotificationToStaff(token, title, content, idBill);
        mListDrinkObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onRetrieveBillNotificationSuccess, this::handleErrorsNotificationBill);
    }

    private void onRetrieveBillNotificationSuccess(Bill result) {
        wasPushed.postValue(true);
    }

    private void handleErrorsNotificationBill(Throwable throwable) {
        wasPushed.postValue(false);
        Log.d("TAG", "handleErrorsUpdateBill: "+ throwable.getMessage());
    }

    // Update table
    public void updateTable(String idTable, Table table){
        ServiceAPI serviceAPI = RetroInstance.getRetrofitInstance().create(ServiceAPI.class);
        Observable<Table> mListDrinkObservable = serviceAPI.updateTable(idTable, table, "PUT");
        mListDrinkObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onRetrieveTableSuccess, this::handleErrorsGetTable);
    }


    private void onRetrieveTableSuccess(Table result) {
        wasUpdatedTable.postValue(true);
    }

    private void handleErrorsGetTable(Throwable throwable) {
        wasUpdatedTable.postValue(false);
        Log.d("TAG", "Handle error update table: "+ throwable.getMessage());
    }
}
