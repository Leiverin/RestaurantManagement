package com.poly.restaurant.ui.bill;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.poly.restaurant.data.models.Bill;
import com.poly.restaurant.data.models.Table;
import com.poly.restaurant.data.retrofit.RetroInstance;
import com.poly.restaurant.data.retrofit.ServiceAPI;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillViewModel extends ViewModel {
    public MutableLiveData<List<Bill>> mListBillLiveData;
    public MutableLiveData<Boolean> wasUpdatedTable;
    public MutableLiveData<Bill> mBillByIdLiveData;

    public BillViewModel() {
        mListBillLiveData = new MutableLiveData<>();
        wasUpdatedTable = new MutableLiveData<>();
        mBillByIdLiveData=new MutableLiveData<>();
    }

    public void getBill(int status,int numberFloor,String idStaff) {
        ServiceAPI serviceAPI = RetroInstance.getRetrofitInstance().create(ServiceAPI.class);
        Call<List<Bill>> call = serviceAPI.getTypeBill(status,numberFloor,idStaff);
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
    public void updateTable(String idTable, Table table){
        ServiceAPI serviceAPI = RetroInstance.getRetrofitInstance().create(ServiceAPI.class);
        Observable<Table> mListDrinkObservable = serviceAPI.updateTable(idTable, table, "PUT");
        mListDrinkObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onRetrieveTableSuccess, this::handleErrorsGetTable);
    }
    private void onRetrieveTableSuccess(Table result) {
        wasUpdatedTable.postValue(true);
    }

    private void handleErrorsGetTable(Throwable throwable) {
        wasUpdatedTable.postValue(false);
        Log.d("TAG", "Handle error update table: "+ throwable.getMessage());
    }
    public void getBillById(String id){
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
        if (result.isSuccessful()){
            mBillByIdLiveData.postValue(result.body());
        }
    }

    private void handleErrorsBillById(Throwable throwable) {
        mBillByIdLiveData.postValue(null);
        Log.d("TAG", "Handle error update table: "+ throwable.getMessage());
    }
}
