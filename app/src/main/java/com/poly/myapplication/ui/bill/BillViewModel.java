package com.poly.myapplication.ui.bill;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.poly.myapplication.data.models.Bill;
import com.poly.myapplication.data.retrofit.RetroInstance;
import com.poly.myapplication.data.retrofit.ServiceAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillViewModel extends ViewModel {
    public MutableLiveData<List<Bill>> mListBillLiveData;

    public BillViewModel() {
        mListBillLiveData = new MutableLiveData<>();
    }

    public void getBill() {
        ServiceAPI serviceAPI = RetroInstance.getRetrofitInstance().create(ServiceAPI.class);
        Call<List<Bill>> call = serviceAPI.getBill();
        call.enqueue(new Callback<List<Bill>>() {
            @Override
            public void onResponse(Call<List<Bill>> call, Response<List<Bill>> response) {
                mListBillLiveData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Bill>> call, Throwable t) {
                mListBillLiveData.postValue(null);
            }
        });
    }
}
