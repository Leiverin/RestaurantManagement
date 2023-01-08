package com.poly.restaurant.ui.activities.merge;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.poly.restaurant.data.models.Product;
import com.poly.restaurant.data.models.Table;
import com.poly.restaurant.data.retrofit.RetroInstance;
import com.poly.restaurant.data.retrofit.ServiceAPI;
import com.poly.restaurant.ui.base.BaseViewModel;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MergeTableViewModel extends BaseViewModel {
    public MutableLiveData<List<Table>> mListLiveTableLiveData;
    public MutableLiveData<List<Table>> mListEmptyTableLiveData;

    public MergeTableViewModel(Context context) {
        super(context);
        mListLiveTableLiveData = new MutableLiveData<>();
        mListEmptyTableLiveData = new MutableLiveData<>();
    }

    public LiveData<List<Table>> getTableLiveData(){
        return tableDao.getListTable();
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
}
