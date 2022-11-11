package com.poly.myapplication.ui.activities.product.appetizer;

import android.util.Log;

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
        Observable<List<Product>> mListProductObservable = serviceAPI.getProductByCategory(1);
        mListProductObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onRetrieveProductListSuccess, this::handleErrors);
    }

    private void onRetrieveProductListSuccess(List<Product> result) {
        mListAppetizerLiveData.postValue(result);
    }

    private void handleErrors(Throwable throwable) {
        Log.e("TAG", "handleErrors: "+ throwable.getMessage());
    }


}