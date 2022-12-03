package com.poly.restaurant.utils;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.poly.restaurant.R;
import com.poly.restaurant.data.models.Bill;
import com.poly.restaurant.data.models.Staff;
import com.poly.restaurant.data.retrofit.RetroInstance;
import com.poly.restaurant.data.retrofit.ServiceAPI;
import com.poly.restaurant.databinding.DialogShowDetailBillBinding;
import com.poly.restaurant.ui.bill.adapter.ShowDetailProductBillAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("SetTextI18n")
public class Constants {
    public static String EXTRA_TABLE_TO_DETAIL = "extra_table_to_detail";
    public static String TABLE_ID_SELECTED = "TABLE_ID_SELECTED";
    public static int TYPE_IN_TABLE = 0;
    public static int TYPE_IN_PRODUCT = 1;
    public static int CHECKOUT_BY_TRANSFER = 1;
    public static int CHECKOUT_BY_SWIPE = 1;
    public static Staff staff;
    public static String CHANNEL_ID = "Restaurant Chanel";

    public static void handleIncrease(TextView tvQuantity, int type){
        if (type == TYPE_IN_TABLE){
            int quantity = Integer.parseInt(tvQuantity.getText().toString());
            quantity++;
            tvQuantity.setText(quantity+"");
        }else{
            int quantity = Integer.parseInt(tvQuantity.getText().toString().subSequence(1, tvQuantity.getText().toString().length()).toString());
            quantity++;
            tvQuantity.setText("x"+quantity);
        }
    }

    public static void handleDecrease(TextView tvQuantity, int type){
        if (type == TYPE_IN_TABLE){
            int quantity = Integer.parseInt(tvQuantity.getText().toString());
            if (quantity > 0){
                quantity--;
                tvQuantity.setText(quantity+"");
            }
        }else{
            int quantity = Integer.parseInt(tvQuantity.getText().toString().subSequence(1, tvQuantity.getText().toString().length()).toString());
            if (quantity > 0){
                quantity--;
                tvQuantity.setText("x"+quantity);
            }
        }
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

    public static void dialogShowDetailBill(Bill bill, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        DialogShowDetailBillBinding showDetailBillBinding = DialogShowDetailBillBinding.inflate(LayoutInflater.from(context));
        builder.setView(showDetailBillBinding.getRoot());
        AlertDialog dialog = builder.create();
        showDetailBillBinding.txtNameTable.setText(bill.getTable().getName());
        showDetailBillBinding.tvTime.setText(bill.getTime());
        showDetailBillBinding.tvDate.setText(bill.getDate());
        showDetailBillBinding.tvPrice.setText(bill.getTotalPrice() + "");
        showDetailBillBinding.btnPay.setOnClickListener(view -> {
            dialog.dismiss();
        });
        if (bill.getProducts() != null) {
            showDetailBillBinding.rvProductBillDetail.setVisibility(View.VISIBLE);
            ShowDetailProductBillAdapter adapterShowDetailBill = new ShowDetailProductBillAdapter(context, bill.getProducts());
            showDetailBillBinding.rvProductBillDetail.setAdapter(adapterShowDetailBill);
        }
        dialog.show();
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.CENTER);
    }


}
