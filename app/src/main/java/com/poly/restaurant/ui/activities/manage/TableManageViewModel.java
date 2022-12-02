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

    public TableManageViewModel() {
        mListTableLiveData = new MutableLiveData<>();
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
}

