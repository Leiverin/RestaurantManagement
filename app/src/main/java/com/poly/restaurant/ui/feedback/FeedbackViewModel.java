package com.poly.restaurant.ui.feedback;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.poly.restaurant.data.models.Feedback;
import com.poly.restaurant.data.models.Staff;
import com.poly.restaurant.data.models.Table;
import com.poly.restaurant.data.retrofit.RetroInstance;
import com.poly.restaurant.data.retrofit.ServiceAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedbackViewModel extends ViewModel {

    public MutableLiveData<Feedback> mFeedbackLiveData;

    public FeedbackViewModel() {
        mFeedbackLiveData = new MutableLiveData<>();
    }

    public void createFeedback(Feedback feedback) {
        ServiceAPI serviceAPI = RetroInstance.getRetrofitInstance().create(ServiceAPI.class);
        Call<Feedback> feedbackCall = serviceAPI.createFeedBack(feedback);
        feedbackCall.enqueue(new Callback<Feedback>() {
            @Override
            public void onResponse(Call<Feedback> call, Response<Feedback> response) {
                mFeedbackLiveData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<Feedback> call, Throwable t) {
                mFeedbackLiveData.postValue(null);
            }
        });
    }

}
