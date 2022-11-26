package com.poly.myapplication.ui.activities.product.appetizer;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.poly.myapplication.data.models.Bill;
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
    public MutableLiveData<Bill> mBillLiveData;

    public AppetizerViewModel() {
        mListAppetizerLiveData = new MutableLiveData<>();
        mBillLiveData = new MutableLiveData<>();
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

    // Add a bill
    public void callToCreateBill(Bill bill){
        ServiceAPI serviceAPI = RetroInstance.getRetrofitInstance().create(ServiceAPI.class);
        Observable<Bill> mBillObservable = serviceAPI.createBill(bill);
        mBillObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::onCreateSuccessfully, this::onErrorCreateBill);
    }

    private void onCreateSuccessfully(Bill result) {
        Log.d("TAG", "onCreateSuccessfully: ");
    }

    private void onErrorCreateBill(Throwable throwable) {
        Log.e("TAG", "handleErrors: "+ throwable.getMessage());
    }

    // Check table
    public void callToGetBillByTable(String idTable){
        ServiceAPI serviceAPI = RetroInstance.getRetrofitInstance().create(ServiceAPI.class);
        Observable<Bill> mBillObservable = serviceAPI.getBillByTable(idTable);
        mBillObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(this::onCreateSuccessfully, this::onErrorCreateBill);
    }

    private void onRetrieveBillSuccess(Bill result) {
        mBillLiveData.postValue(result);
    }

    private void onRetrieveBillFail(Throwable throwable) {
        mBillLiveData.postValue(null);
        Log.e("TAG", "handleErrors: "+ throwable.getMessage());
    }

}