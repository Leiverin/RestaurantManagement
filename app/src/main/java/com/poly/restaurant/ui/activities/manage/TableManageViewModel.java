package com.poly.restaurant.ui.activities.manage;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.poly.restaurant.data.models.Staff;
import com.poly.restaurant.data.models.Table;
import com.poly.restaurant.data.retrofit.RetroInstance;
import com.poly.restaurant.data.retrofit.ServiceAPI;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Response;

public class TableManageViewModel extends ViewModel {
    public MutableLiveData<List<Table>> mListTableLiveData;
    public MutableLiveData<List<Table>> mListLiveTableLiveData;
    public MutableLiveData<List<Table>> mListEmptyTableLiveData;
    public MutableLiveData<List<Table>> mListMergeTableLiveData;
    public MutableLiveData<List<Staff>> mListAdminLiveData;
    public MutableLiveData<List<Staff>> mListChefLiveData;
    public MutableLiveData<List<Staff>> mListCashierLiveData;

    public TableManageViewModel() {
        mListTableLiveData = new MutableLiveData<>();
        mListLiveTableLiveData = new MutableLiveData<>();
        mListEmptyTableLiveData = new MutableLiveData<>();
        mListAdminLiveData = new MutableLiveData<>();
        mListChefLiveData = new MutableLiveData<>();
        mListCashierLiveData = new MutableLiveData<>();
        mListMergeTableLiveData = new MutableLiveData<>();
    }

    public void callToGetTable(int floor) {
        ServiceAPI serviceAPI = RetroInstance.getRetrofitInstance().create(ServiceAPI.class);
        Observable<List<Table>> observable = serviceAPI.getTableByFloor(floor);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onRetrieveTableListSuccess, this::onHandleError);
    }

    private void onRetrieveTableListSuccess(List<Table> tables) {
        mListTableLiveData.postValue(tables);
    }

    private void onHandleError(Throwable throwable) {
        Log.e("TAG", "handleErrors: " + throwable.getMessage());
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
        Log.e("TAG", "handle error live: " + throwable.getMessage());
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
        Log.e("TAG", "handle error empty: " + throwable.getMessage());
        mListEmptyTableLiveData.postValue(null);
    }

    public void callToGetTableMerge(int floor, int status) {
        ServiceAPI serviceAPI = RetroInstance.getRetrofitInstance().create(ServiceAPI.class);
        Observable<List<Table>> observable = serviceAPI.getTableByFloorAndStatus(floor, status);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onRetrieveTableBookedSuccess, this::onHandleErrorTableBooked);
    }

    private void onRetrieveTableBookedSuccess(List<Table> tables) {
        mListMergeTableLiveData.postValue(tables);
    }

    private void onHandleErrorTableBooked(Throwable throwable) {
        Log.e("TAG", "handle error empty: " + throwable.getMessage());
        mListMergeTableLiveData.postValue(null);
    }

    public void callToGetAdmin() {
        ServiceAPI serviceAPI = RetroInstance.getRetrofitInstance().create(ServiceAPI.class);
        Observable<Response<List<Staff>>> observable = serviceAPI.getListStaffByRole(1);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onRetrieveAdmin, this::onHandleErrorAdmin);
    }

    private void onRetrieveAdmin(Response<List<Staff>> staffs) {
        if (staffs.isSuccessful()) {
            mListAdminLiveData.postValue(staffs.body());
        }
    }

    private void onHandleErrorAdmin(Throwable throwable) {
        Log.e("TAG", "handle error admin: " + throwable.getMessage());
        mListAdminLiveData.postValue(null);
    }


    public void callToGetChef() {
        ServiceAPI serviceAPI = RetroInstance.getRetrofitInstance().create(ServiceAPI.class);
        Observable<Response<List<Staff>>> observable = serviceAPI.getListStaffByRole(3);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onRetrieveChef, this::onHandleErrorChef);
    }

    private void onRetrieveChef(Response<List<Staff>> staffs) {
        if (staffs.isSuccessful()) {
            mListChefLiveData.postValue(staffs.body());
        }
    }

    private void onHandleErrorChef(Throwable throwable) {
        Log.e("TAG", "handle error admin: " + throwable.getMessage());
        mListChefLiveData.postValue(null);
    }


    public void callToGetCashier() {
        ServiceAPI serviceAPI = RetroInstance.getRetrofitInstance().create(ServiceAPI.class);
        Observable<Response<List<Staff>>> observable = serviceAPI.getListStaffByRole(2);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onRetrieveCashier, this::onHandleErrorCashier);
    }

    private void onRetrieveCashier(Response<List<Staff>> staffs) {
        if (staffs.isSuccessful()) {
            mListCashierLiveData.postValue(staffs.body());
        }
    }

    private void onHandleErrorCashier(Throwable throwable) {
        Log.e("TAG", "handle error cashier: " + throwable.getMessage());
        mListCashierLiveData.postValue(null);
    }


}

