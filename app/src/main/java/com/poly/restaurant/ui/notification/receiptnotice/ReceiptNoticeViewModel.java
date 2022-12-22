package com.poly.restaurant.ui.notification.receiptnotice;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.poly.restaurant.data.models.Notification;
import com.poly.restaurant.data.retrofit.RetroInstance;
import com.poly.restaurant.data.retrofit.ServiceAPI;
import com.poly.restaurant.ui.base.BaseViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReceiptNoticeViewModel extends BaseViewModel {
    public MutableLiveData<List<Notification>> mListNotiLiveData;

    public ReceiptNoticeViewModel(Context context) {
        super(context);
        mListNotiLiveData = new MutableLiveData<>();
    }

    public void getNotificationReceider(String receider) {
        ServiceAPI serviceAPI = RetroInstance.getRetrofitInstance().create(ServiceAPI.class);
        Call<List<Notification>> call = serviceAPI.getNotification(receider);
        call.enqueue(new Callback<List<Notification>>() {
            @Override
            public void onResponse(Call<List<Notification>> call, Response<List<Notification>> response) {
                mListNotiLiveData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Notification>> call, Throwable t) {
                mListNotiLiveData.postValue(null);
            }
        });
    }
}
