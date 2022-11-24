package com.poly.myapplication.utils;

import android.annotation.SuppressLint;
import android.widget.TextView;

import com.poly.myapplication.data.models.Bill;
import com.poly.myapplication.data.models.Table;
import com.poly.myapplication.data.retrofit.RetroInstance;
import com.poly.myapplication.data.retrofit.ServiceAPI;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("SetTextI18n")

public class Constants {
    public static String EXTRA_TABLE_TO_DETAIL = "extra_table_to_detail";
    public static String BASE_URL = "https://restaurant-order.onrender.com/restaurant/api/";
    public static int TYPE_IN_TABLE = 0;
    public static int TYPE_IN_PRODUCT = 1;

    public static void handleIncrease(TextView tvQuantity, int type) {
        if (type == TYPE_IN_TABLE) {
            int quantity = Integer.parseInt(tvQuantity.getText().toString());
            quantity++;
            tvQuantity.setText(quantity + "");
        } else {
            int quantity = Integer.parseInt(tvQuantity.getText().toString().subSequence(1, tvQuantity.getText().toString().length()).toString());
            quantity++;
            tvQuantity.setText("x" + quantity);
        }

    }

    public static void handleDecrease(TextView tvQuantity, int type) {
        if (type == TYPE_IN_TABLE) {
            int quantity = Integer.parseInt(tvQuantity.getText().toString());
            if (quantity > 0) {
                quantity--;
                tvQuantity.setText(quantity + "");
            }
        } else {
            int quantity = Integer.parseInt(tvQuantity.getText().toString().subSequence(1, tvQuantity.getText().toString().length()).toString());
            if (quantity > 0) {
                quantity--;
                tvQuantity.setText("x" + quantity);
            }
        }
    }

    public static void setNameTable(Bill bill, TextView txtNameTable) {
        ServiceAPI serviceAPI = RetroInstance.getRetrofitInstance().create(ServiceAPI.class);
        Call<List<Table>> call = serviceAPI.getTableByFloorBill(1);
        call.enqueue(new Callback<List<Table>>() {
            @Override
            public void onResponse(Call<List<Table>> call, Response<List<Table>> response) {
                assert response.body() != null;
                for (int i = 0; i < response.body().size(); i++) {
                    if (Objects.equals(bill.getIdTable(), response.body().get(i).getId())) {
                        txtNameTable.setText(response.body().get(i).getName());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Table>> call, Throwable t) {
            }
        });
    }

    public static void setOnStatus(Bill bill) {
        Bill bill1 = new Bill();
        bill1.setStatus(1);
        bill1.setTotalPrice(bill.getTotalPrice());
        ServiceAPI serviceAPI = RetroInstance.getRetrofitInstance().create(ServiceAPI.class);
        Call<List<Bill>> callDon = serviceAPI.doneBill(bill.getId(), "PUT", bill1);
        callDon.enqueue(new Callback<List<Bill>>() {
            @Override
            public void onResponse(Call<List<Bill>> call, Response<List<Bill>> response) {
                if (response.isSuccessful()) {

                }
            }

            @Override
            public void onFailure(Call<List<Bill>> call, Throwable t) {

            }
        });
    }

}
