package com.poly.restaurant.ui.activities.login;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.poly.restaurant.data.models.Staff;
import com.poly.restaurant.data.retrofit.RetroInstance;
import com.poly.restaurant.data.retrofit.ServiceAPI;
import com.poly.restaurant.ui.base.BaseViewModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginViewModel extends BaseViewModel {
    public MutableLiveData<Staff> mStaffLiveData;

    public LoginViewModel(Context context) {
        super(context);
        mStaffLiveData = new MutableLiveData<>();
    }

    // Create bill
    public void callToLogin(String account, String password){
        ServiceAPI serviceAPI = RetroInstance.getRetrofitInstance().create(ServiceAPI.class);
        Observable<Staff> mListDrinkObservable = serviceAPI.login(account, password);
        mListDrinkObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onRetrieveStaffSuccess, this::handleErrors);
    }

    private void onRetrieveStaffSuccess(Staff result) {
        mStaffLiveData.postValue(result);
    }

    private void handleErrors(Throwable throwable) {
        mStaffLiveData.postValue(null);
        Log.d("TAG", "Error in login: " + throwable.getMessage());
    }
}
