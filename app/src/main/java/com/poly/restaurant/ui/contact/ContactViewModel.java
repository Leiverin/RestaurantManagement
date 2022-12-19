package com.poly.restaurant.ui.contact;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.poly.restaurant.data.models.Staff;
import com.poly.restaurant.data.retrofit.RetroInstance;
import com.poly.restaurant.data.retrofit.ServiceAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactViewModel extends ViewModel {

    public MutableLiveData<List<Staff>> mContactLiveData;

    public ContactViewModel() {
        mContactLiveData = new MutableLiveData<>();
    }

    public void getAdminContact() {
        ServiceAPI serviceAPI = RetroInstance.getRetrofitInstance().create(ServiceAPI.class);
        Call<List<Staff>> contact = serviceAPI.getAdminContact();
        contact.enqueue(new Callback<List<Staff>>() {
            @Override
            public void onResponse(Call<List<Staff>> call, Response<List<Staff>> response) {
                mContactLiveData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Staff>> call, Throwable t) {
                mContactLiveData.postValue(null);
            }
        });
    }
}
