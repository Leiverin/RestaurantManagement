package com.poly.restaurant.ui.activities.merge;

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

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MergeTableViewModel extends BaseViewModel {
    public MutableLiveData<List<Table>> mListLiveTableLiveData;
    public MutableLiveData<List<Table>> mListEmptyTableLiveData;
    public MutableLiveData<Bill> wasBillCreated;
    public MutableLiveData<List<Bill>> getProductByIdTable;
    public MutableLiveData<List<Bill>> getBillByIdTable;
    public MutableLiveData<Boolean> wasUpdatedTable;
    public MutableLiveData<Boolean> wasUpdated;
    public MutableLiveData<Boolean> wasDeleted;
    public MutableLiveData<List<Bill>> mBillLiveData;

    public MergeTableViewModel(Context context) {
        super(context);
        mListLiveTableLiveData = new MutableLiveData<>();
        mListEmptyTableLiveData = new MutableLiveData<>();
        wasBillCreated = new MutableLiveData<>();
        getProductByIdTable = new MutableLiveData<>();
        wasUpdatedTable=new MutableLiveData<>();
        mBillLiveData=new MutableLiveData<>();
        getBillByIdTable=new MutableLiveData<>();
    }

    public LiveData<List<Table>> getTableLiveData() {
        return tableDao.getListTable();
    }

    public LiveData<List<Product>> getListProductByIdTableLive(String idTable) {
        return productDao.getProductByIdTableLiveData(idTable);
    }

    public void callToGetTableLive(int floor, int status) {
        ServiceAPI serviceAPI = RetroInstance.getRetrofitInstance().create(ServiceAPI.class);
        Observable<List<Table>> observable = serviceAPI.getTableByFloorAndStatus(floor, status);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onRetrieveTableLiveSuccess, this::onHandleErrorTableLive);
    }

    private void onRetrieveTableLiveSuccess(List<Table> tables) {
        mListLiveTableLiveData.postValue(tables);
    }

    private void onHandleErrorTableLive(Throwable throwable) {
        mListLiveTableLiveData.postValue(null);
    }


    public void callToGetTableEmpty(int floor, int status) {
        ServiceAPI serviceAPI = RetroInstance.getRetrofitInstance().create(ServiceAPI.class);
        Observable<List<Table>> observable = serviceAPI.getTableByFloorAndStatus(floor, status);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onRetrieveTableEmptySuccess, this::onHandleErrorTableEmpty);
    }

    private void onRetrieveTableEmptySuccess(List<Table> tables) {
        mListEmptyTableLiveData.postValue(tables);
    }

    private void onHandleErrorTableEmpty(Throwable throwable) {
        mListEmptyTableLiveData.postValue(null);
    }

    public void callToCreateBill(Bill bill) {
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

    public void checkBillAlreadyExists(String idTable) {
        ServiceAPI serviceAPI = RetroInstance.getRetrofitInstance().create(ServiceAPI.class);
        Observable<List<Bill>> mListDrinkObservable = serviceAPI.getBillIfExists(idTable);
        mListDrinkObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onRetrieveBillExist, this::handleErrorsBillExist);
    }

    private void onRetrieveBillExist(List<Bill> result) {
        getProductByIdTable.postValue(result);
    }

    private void handleErrorsBillExist(Throwable throwable) {
        getProductByIdTable.postValue(null);
    }

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
    }

    public void callToUpdateBill(String id, Bill bill, int type){
        ServiceAPI serviceAPI = RetroInstance.getRetrofitInstance().create(ServiceAPI.class);
        Observable<Bill> mListDrinkObservable = serviceAPI.updateBillById(id, bill, "PUT");
        mListDrinkObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        result -> onRetrieveUpdateBillSuccess(result, type),
                        err -> handleErrorsUpdateBill(err, type)
                );
    }

    private void onRetrieveUpdateBillSuccess(Bill result, int type) {
        wasUpdated.postValue(true);
    }

    private void handleErrorsUpdateBill(Throwable throwable, int type) {
        wasUpdated.postValue(false);
    }

    public void checkBillByIdTable(String idTable) {
        ServiceAPI serviceAPI = RetroInstance.getRetrofitInstance().create(ServiceAPI.class);
        Observable<List<Bill>> mListDrinkObservable = serviceAPI.getBillIfExists(idTable);
        mListDrinkObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onRetrieveBill, this::handleErrorsBill);
    }

    private void onRetrieveBill(List<Bill> result) {
        getBillByIdTable.postValue(result);
    }

    private void handleErrorsBill(Throwable throwable) {
        getBillByIdTable.postValue(null);
    }
    public void deleteBill(String id){
        ServiceAPI serviceAPI = RetroInstance.getRetrofitInstance().create(ServiceAPI.class);
        Observable<Response<Bill>> mBill = serviceAPI.deleteBill(id);
        mBill.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onRetrieveDeleteBillSuccessfully, this::handleErrorsDeleteBill);
    }

    private void onRetrieveDeleteBillSuccessfully(Response<Bill> result) {
        if (result.isSuccessful()){
            wasDeleted.postValue(true);
        }
    }

    private void handleErrorsDeleteBill(Throwable throwable) {
        Log.d("TAG", "Handle error delete bill: "+ throwable.getMessage());
        wasDeleted.postValue(false);
    }
}
