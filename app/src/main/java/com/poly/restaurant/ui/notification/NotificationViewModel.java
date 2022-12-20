package com.poly.restaurant.ui.notification;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.poly.restaurant.data.models.Bill;
import com.poly.restaurant.data.models.Notification;
import com.poly.restaurant.data.retrofit.RetroInstance;
import com.poly.restaurant.data.retrofit.ServiceAPI;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationViewModel extends ViewModel {
    public MutableLiveData<List<Notification>> mListNotiLiveData;
    public MutableLiveData<Bill> mBillByIdLiveData;

    public NotificationViewModel() {
        mListNotiLiveData = new MutableLiveData<>();
        mBillByIdLiveData = new MutableLiveData<>();
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

    public void getBillById(String id) {
        ServiceAPI serviceAPI = RetroInstance.getRetrofitInstance().create(ServiceAPI.class);
        Observable<Response<Bill>> mBillObservable = serviceAPI.getBillById(id);
        mBillObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::onRetrieveBillById,
                        this::handleErrorsBillById
                );
    }

    private void onRetrieveBillById(Response<Bill> result) {
        if (result.isSuccessful()) {
            mBillByIdLiveData.postValue(result.body());
        }
    }

    private void handleErrorsBillById(Throwable throwable) {
        mBillByIdLiveData.postValue(null);
        Log.d("TAG", "Handle error update table: " + throwable.getMessage());
    }
}
