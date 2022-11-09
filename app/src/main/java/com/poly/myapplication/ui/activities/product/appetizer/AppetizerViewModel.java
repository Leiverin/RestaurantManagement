package com.poly.myapplication.ui.activities.product.appetizer;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.poly.myapplication.data.models.Product;
import com.poly.myapplication.data.models.Table;
import com.poly.myapplication.data.retrofit.RetroInstance;
import com.poly.myapplication.data.retrofit.ServiceAPI;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppetizerViewModel extends ViewModel {
    public MutableLiveData<List<Product>> mListAppetizerLiveData;

    public AppetizerViewModel() {
        mListAppetizerLiveData = new MutableLiveData<>();
    }

    public void callToGetAppetizer(){
        ServiceAPI serviceAPI = RetroInstance.getRetrofitInstance().create(ServiceAPI.class);
        Observable<List<Product>> mListProductObservable = serviceAPI.getProductByCategory();
        mListProductObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
//                .map(result -> result)
//                .subscribe(
//                    {result -> onRetrievePhotoListSuccess(result)},
//                    {error -> handleErrors(error)}
//                )
        ;
//        Call<List<Product>> call = serviceAPI.getProductByCategory();
//        call.enqueue(new Callback<List<Product>>() {
//            @Override
//            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
//                mListAppetizerLiveData.postValue(response.body());
//            }
//
//            @Override
//            public void onFailure(Call<List<Product>> call, Throwable t) {
//                mListAppetizerLiveData.postValue(null);
//            }
//        });
    }

    private void onRetrievePhotoListSuccess(List<Product> result) {

    }


}