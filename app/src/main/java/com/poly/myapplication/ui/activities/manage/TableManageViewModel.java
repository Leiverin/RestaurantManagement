package com.poly.myapplication.ui.activities.manage;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.poly.myapplication.data.models.Table;
import com.poly.myapplication.data.retrofit.RetroInstance;
import com.poly.myapplication.data.retrofit.ServiceAPI;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TableManageViewModel extends ViewModel {
    public MutableLiveData<List<Table>> mListTableLiveData;

    public TableManageViewModel() {
        mListTableLiveData = new MutableLiveData<>();
    }

    public void callToGetTable(){
        ServiceAPI serviceAPI = RetroInstance.getRetrofitInstance().create(ServiceAPI.class);
        Observable<List<Table>> observable = serviceAPI.getTableByFloor(1);
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

