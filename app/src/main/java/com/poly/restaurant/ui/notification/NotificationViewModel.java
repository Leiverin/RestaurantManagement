package com.poly.restaurant.ui.notification;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.poly.restaurant.data.models.Notification;
import com.poly.restaurant.data.retrofit.RetroInstance;
import com.poly.restaurant.data.retrofit.ServiceAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationViewModel extends ViewModel {
    public MutableLiveData<List<Notification>> mListNotiLiveData;

    public NotificationViewModel() {
        mListNotiLiveData = new MutableLiveData<>();
    }

    public void getNotification(String idStaff) {
        ServiceAPI serviceAPI = RetroInstance.getRetrofitInstance().create(ServiceAPI.class);
        Call<List<Notification>> call = serviceAPI.getNotification(idStaff);
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
