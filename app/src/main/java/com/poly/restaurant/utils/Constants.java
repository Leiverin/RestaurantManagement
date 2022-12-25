package com.poly.restaurant.utils;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.poly.restaurant.R;
import com.poly.restaurant.data.models.Bill;
import com.poly.restaurant.data.models.Notification;
import com.poly.restaurant.data.models.Product;
import com.poly.restaurant.data.models.Staff;
import com.poly.restaurant.data.retrofit.RetroInstance;
import com.poly.restaurant.data.retrofit.ServiceAPI;
import com.poly.restaurant.databinding.DialogShowDetailBillBinding;
import com.poly.restaurant.ui.activities.product.appetizer.adapter.ProductAdapter;
import com.poly.restaurant.ui.bill.adapter.ShowDetailProductBillAdapter;
import com.poly.restaurant.ui.notification.adapter.NotificationAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("SetTextI18n")
public class Constants {
    public static final int TABLE_EMPTY_STATUS = 0;
    public static final int TABLE_LIVE_STATUS = 1;
    public static final String EXTRA_ID_BILL_TO_TABLE_DETAIL = "EXTRA_ID_BILL_TO_TABLE_DETAIL";
    public static final String EXTRA_ID_STAFF_TO_TABLE_DETAIL = "EXTRA_ID_STAFF_TO_TABLE_DETAIL";
    public static final String STRING_CONTENT_TO_ANNOUNCE = "content_to_announce";
    public static String EXTRA_TABLE_TO_DETAIL = "extra_table_to_detail";
    public static String EXTRA_ADMIN_TO_DETAIL = "extra_admin_to_detail";
    public static String EXTRA_CHEF_TO_DETAIL = "extra_chef_to_detail";
    public static String EXTRA_CASHIER_TO_DETAIL = "extra_cashier_to_detail";

    public static String TABLE_ID_SELECTED = "TABLE_ID_SELECTED";
    public static String KEY_BEFORE_TABLE_ID = "KEY_BEFORE_TABLE_ID";

    public static String KEY_ID_STAFF = "KEY_ID_STAFF";
    public static String KEY_USERNAME = "KEY_USERNAME";
    public static String KEY_PASSWORD = "KEY_PASSWORD";
    public static String KEY_REMEMBER = "KEY_REMEMBER";

    public static int TYPE_CLICK = 0;
    public static int TYPE_NON_CLICK = 1;
    public static final int TYPE_PAY_BILL = 2;

    public static final int TYPE_PAY = 1;
    public static final int TYPE_UPDATE = 2;

    public static int TYPE_IN_TABLE = 0;
    public static int TYPE_IN_PRODUCT = 1;
    public static String BASE_URL = "https://restaurant-server-eight.vercel.app/restaurant/api/";
    public static int CHECKOUT_BY_TRANSFER = 1;
    public static int CHECKOUT_BY_SWIPE = 1;
    public static Staff staff;
    public static String CHANNEL_ID = "Restaurant Chanel";
    public static String EXTRA_ID_BILL_TO_BROADCAST = "EXTRA_ID_BILL_TO_BROADCAST";
    public static String REQUEST_TO_ACTIVITY = "REQUEST_TO_ACTIVITY";
    public static String CREATE_FEEDBACK = "CREATE_FEEDBACK";

    public static String CALL_CENTER_NUMBER = "0372546891";

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


    public static void skype(String number, Context ctx) {
        try {
            //Intent sky = new Intent("android.intent.action.CALL_PRIVILEGED");
            //the above line tries to create an intent for which the skype app doesn't supply public api
            Intent sky = new Intent("android.intent.action.VIEW");
            sky.setData(Uri.parse("skype:" + number));
            ctx.startActivity(sky);
        } catch (ActivityNotFoundException e) {
            String appPackageName = "com.skype.raider";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(
                    "https://play.google.com/store/apps/details?id=" + appPackageName));
            intent.setPackage("com.android.vending");
            ctx.startActivity(intent);
        }

    }

    public static void dialogShowDetailBill(Bill bill, Context context, CardView cardView) {
        Toast.makeText(context, "Yêu cầu xác nhận thông tin", Toast.LENGTH_SHORT).show();
        cardView.setEnabled(false);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        DialogShowDetailBillBinding showDetailBillBinding = DialogShowDetailBillBinding.inflate(LayoutInflater.from(context));
        builder.setView(showDetailBillBinding.getRoot());
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.setCancelable(false);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.CENTER);
        showDetailBillBinding.txtNameTable.setText(bill.getTable().getName());
        showDetailBillBinding.tvTime.setText(bill.getTime());
        showDetailBillBinding.tvDate.setText(bill.getDate());
        showDetailBillBinding.tvPrice.setText(bill.getTotalPrice() + "");
        if (bill.getProducts() != null) {
            showDetailBillBinding.rvProductBillDetail.setVisibility(View.VISIBLE);
            ShowDetailProductBillAdapter adapterShowDetailBill = new ShowDetailProductBillAdapter(context, bill.getProducts());
            showDetailBillBinding.rvProductBillDetail.setAdapter(adapterShowDetailBill);
        }
        showDetailBillBinding.btnPay.setOnClickListener(view -> {
            dialog.dismiss();
            cardView.setEnabled(true);
            Toast.makeText(context, "Đã xác nhận thông tin", Toast.LENGTH_SHORT).show();
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }

    public static void changePasswordStaff(String pass) {
        Staff staff1 = new Staff();
        staff1.setPassword(pass);
        ServiceAPI serviceAPI = RetroInstance.getRetrofitInstance().create(ServiceAPI.class);
        Call<Staff> changePass = serviceAPI.changePassword(staff.getId(), "PUT", staff1);
        changePass.enqueue(new Callback<Staff>() {
            @Override
            public void onResponse(Call<Staff> call, Response<Staff> response) {
                if (response.isSuccessful()) {

                }
            }

            @Override
            public void onFailure(Call<Staff> call, Throwable t) {

            }
        });
    }


    public static void filterList(String s, List<Product> mListProduct, ProductAdapter adapter) {
        List<Product> mListFilter = new ArrayList<>();
        for (Product product : mListProduct) {
            if (product.getName().toLowerCase().contains(s.toLowerCase())) {
                mListFilter.add(product);
            }
            adapter.setList(mListFilter);
        }
    }

    public static void dialogShowDetailNoti(String idBill, Context context, CardView cardView) {
        ServiceAPI serviceAPI = RetroInstance.getRetrofitInstance().create(ServiceAPI.class);
        Call<List<Bill>> listCall = serviceAPI.getBillNoti();
        listCall.enqueue(new Callback<List<Bill>>() {
            @Override
            public void onResponse(Call<List<Bill>> call, Response<List<Bill>> response) {
                for (int i = 0; i < response.body().size(); i++) {
                    if (Objects.equals(idBill, response.body().get(i).getId())) {
                        Toast.makeText(context, "Yêu cầu xác nhận thông tin", Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        DialogShowDetailBillBinding showDetailBillBinding = DialogShowDetailBillBinding.inflate(LayoutInflater.from(context));
                        builder.setView(showDetailBillBinding.getRoot());
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        dialog.setCancelable(false);
                        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                        dialog.getWindow().setGravity(Gravity.CENTER);
                        showDetailBillBinding.txtNameTable.setText(response.body().get(i).getTable().getName());
                        showDetailBillBinding.tvTime.setText(response.body().get(i).getTime());
                        showDetailBillBinding.tvDate.setText(response.body().get(i).getDate());
                        showDetailBillBinding.tvPrice.setText(response.body().get(i).getTotalPrice() + "");
                        if (response.body().get(i).getProducts() != null) {
                            showDetailBillBinding.rvProductBillDetail.setVisibility(View.VISIBLE);
                            ShowDetailProductBillAdapter adapterShowDetailBill = new ShowDetailProductBillAdapter(context, response.body().get(i).getProducts());
                            showDetailBillBinding.rvProductBillDetail.setAdapter(adapterShowDetailBill);
                        }
                        showDetailBillBinding.btnPay.setOnClickListener(view -> {
                            dialog.dismiss();
                            cardView.setEnabled(true);
                            Toast.makeText(context, "Đã xác nhận thông tin", Toast.LENGTH_SHORT).show();
                        });
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Bill>> call, Throwable t) {

            }
        });
    }

    public static void filterNoti(String text, List<Notification> list, NotificationAdapter adapter) {
        List<Notification> notificationList = new ArrayList<>();
        for (Notification notification : list) {
            if (notification.getTitle().contains(text) || notification.getContent().contains(text)) {
                notificationList.add(notification);
            }
        }
        adapter.setList(notificationList);
    }

}
