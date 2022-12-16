package com.poly.restaurant.ui.activities.manage;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.poly.restaurant.data.models.Table;
import com.poly.restaurant.data.retrofit.RetroInstance;
import com.poly.restaurant.data.retrofit.ServiceAPI;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TableManageViewModel extends ViewModel {
    public MutableLiveData<List<Table>> mListTableLiveData;
    public MutableLiveData<List<Table>> mListLiveTableLiveData;
    public MutableLiveData<List<Table>> mListEmptyTableLiveData;

    public TableManageViewModel() {
        mListTableLiveData = new MutableLiveData<>();
        mListLiveTableLiveData = new MutableLiveData<>();
        mListEmptyTableLiveData = new MutableLiveData<>();
    }

    public void callToGetTable(int floor){
        ServiceAPI serviceAPI = RetroInstance.getRetrofitInstance().create(ServiceAPI.class);
        Observable<List<Table>> observable = serviceAPI.getTableByFloor(floor);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onRetrieveTableListSuccess, this::onHandleError);
    }

    private void onRetrieveTableListSuccess(List<Table> tables){
        mListTableLiveData.postValue(tables);
    }

    private void onHandleError(Throwable throwable){
        Log.e("TAG", "handleErrors: " + throwable.getMessage());
    }

    public void callToGetTableLive(int floor, int status){
        ServiceAPI serviceAPI = RetroInstance.getRetrofitInstance().create(ServiceAPI.class);
        Observable<List<Table>> observable = serviceAPI.getTableByFloorAndStatus(floor, status);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onRetrieveTableLiveSuccess, this::onHandleErrorTableLive);
    }

    private void onRetrieveTableLiveSuccess(List<Table> tables){
        mListLiveTableLiveData.postValue(tables);
    }

    private void onHandleErrorTableLive(Throwable throwable){
        Log.e("TAG", "handle error live: " + throwable.getMessage());
        mListLiveTableLiveData.postValue(null);
    }
    public void callToGetTableEmpty(int floor, int status){
        ServiceAPI serviceAPI = RetroInstance.getRetrofitInstance().create(ServiceAPI.class);
        Observable<List<Table>> observable = serviceAPI.getTableByFloorAndStatus(floor, status);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onRetrieveTableEmptySuccess, this::onHandleErrorTableEmpty);
    }

    private void onRetrieveTableEmptySuccess(List<Table> tables){
        mListEmptyTableLiveData.postValue(tables);
    }

    private void onHandleErrorTableEmpty(Throwable throwable){
        Log.e("TAG", "handle error empty: " + throwable.getMessage());
        mListEmptyTableLiveData.postValue(null);
    }
}

