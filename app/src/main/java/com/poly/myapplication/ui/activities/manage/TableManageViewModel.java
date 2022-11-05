package com.poly.myapplication.ui.activities.manage;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.poly.myapplication.data.models.Table;
import com.poly.myapplication.data.retrofit.RetroInstance;
import com.poly.myapplication.data.retrofit.ServiceAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TableManageViewModel extends ViewModel {
    public MutableLiveData<List<Table>> mListTableLiveData;

    public TableManageViewModel() {
        mListTableLiveData = new MutableLiveData<>();
    }

    public void callToGetTable(){
        ServiceAPI serviceAPI = RetroInstance.getRetrofitInstance().create(ServiceAPI.class);
        Call<List<Table>> call = serviceAPI.getTableByFloor(1);
        call.enqueue(new Callback<List<Table>>() {
            @Override
            public void onResponse(Call<List<Table>> call, Response<List<Table>> response) {
                mListTableLiveData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Table>> call, Throwable t) {
                mListTableLiveData.postValue(null);
            }
        });
    }
}

