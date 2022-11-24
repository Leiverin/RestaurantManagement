package com.poly.myapplication.ui.bill;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;
import com.poly.myapplication.R;
import com.poly.myapplication.data.models.Bill;
import com.poly.myapplication.databinding.ActivityBillBinding;
import com.poly.myapplication.databinding.DialogFilterBinding;
import com.poly.myapplication.databinding.DialogShowDetailBillBinding;
import com.poly.myapplication.ui.bill.adapter.BillAdapter;
import com.poly.myapplication.ui.bill.adapter.OnListener;
import com.poly.myapplication.ui.bill.adapter.ShowDetailProductBillAdapter;
import com.poly.myapplication.utils.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BillActivity extends AppCompatActivity {
    private ActivityBillBinding binding;
    private BillAdapter adapter;
    private ShowDetailProductBillAdapter adapterShowDetailBill;
    private List<Bill> list;
    private BillViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(BillViewModel.class);
        binding = ActivityBillBinding.inflate(getLayoutInflater());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(binding.getRoot());
        binding.prgLoadBill.setVisibility(View.VISIBLE);
        binding.rvBill.setVisibility(View.GONE);
        list = new ArrayList<>();
        binding.imgFilter.setOnClickListener(view -> {
            dialogBottomSheetFilter();
        });
        initRec();
        initViewModel();
    }

    private void initRec() {
        adapter = new BillAdapter(this, list, new OnListener() {
            @Override
            public void onClickBill(Bill bill) {
                dialogShowDetailBill(bill);
            }

            @Override
            public void onStatus(Bill bill) {
                Constants.setOnStatus(bill);
                list.remove(bill);
                adapter.setList(list);
                adapter.notifyDataSetChanged();
            }
        });
        binding.rvBill.setAdapter(adapter);
    }

    private void initViewModel() {
        viewModel.mListBillLiveData.observe(this, new Observer<List<Bill>>() {
            @Override
            public void onChanged(List<Bill> bills) {
                list = bills;
                adapter.setList(list);
                Log.d("TAG", new Gson().toJson(list));
                binding.rvBill.setVisibility(View.VISIBLE);
                binding.prgLoadBill.setVisibility(View.GONE);
            }
        });
        viewModel.getBill();
    }

    private void dialogBottomSheetFilter() {
        Dialog dialog = new Dialog(BillActivity.this);
        DialogFilterBinding bindingFilter = DialogFilterBinding.inflate(LayoutInflater.from(BillActivity.this));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(bindingFilter.getRoot());
        final Calendar calendar = Calendar.getInstance();
        // time first
        TimePickerDialog.OnTimeSetListener timeFirst = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                calendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                calendar.set(Calendar.MINUTE, selectedMinute);
                bindingFilter.timeFirstTv.setText("Time first :" + selectedHour + ":" + selectedMinute);
            }
        };
        // time second
        TimePickerDialog.OnTimeSetListener timeSecond = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                calendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                calendar.set(Calendar.MINUTE, selectedMinute);
                bindingFilter.timeSecondTv.setText("Time first :" + selectedHour + ":" + selectedMinute);
            }
        };
        bindingFilter.btnFirstTime.setOnClickListener(view -> {
            new TimePickerDialog(BillActivity.this, timeFirst, calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE), true).show();
        });
        bindingFilter.btnSecondTime.setOnClickListener(view -> {
            new TimePickerDialog(BillActivity.this, timeSecond, calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE), true).show();
        });
        bindingFilter.btnCloseDialog.setOnClickListener(view -> {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            String str1 = bindingFilter.timeFirstTv.getText().toString();
            String str2 = bindingFilter.timeSecondTv.getText().toString();
            try {
                Date time1 = sdf.parse(str1);
                Date time2 = sdf.parse(str2);
                if (time1.compareTo(time2) > 0) {
                    Toast.makeText(this, "abc", Toast.LENGTH_SHORT).show();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            dialog.dismiss();
        });
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }


    private void dialogShowDetailBill(Bill bill) {
        AlertDialog.Builder builder = new AlertDialog.Builder(BillActivity.this);
        DialogShowDetailBillBinding showDetailBillBinding = DialogShowDetailBillBinding.inflate(LayoutInflater.from(BillActivity.this));
        builder.setView(showDetailBillBinding.getRoot());
        AlertDialog dialog = builder.create();
        Constants.setNameTable(bill, showDetailBillBinding.txtNameTable);
        showDetailBillBinding.tvTime.setText(bill.getTime());
        showDetailBillBinding.tvDate.setText(bill.getDate());
        showDetailBillBinding.tvPrice.setText(bill.getTotalPrice() + "");
        showDetailBillBinding.btnPay.setOnClickListener(view -> {
            dialog.dismiss();
        });
        if (bill.getProducts() != null) {
            showDetailBillBinding.rvProductBillDetail.setVisibility(View.VISIBLE);
            adapterShowDetailBill = new ShowDetailProductBillAdapter(BillActivity.this, bill.getProducts());
            showDetailBillBinding.rvProductBillDetail.setAdapter(adapterShowDetailBill);
        }
        dialog.show();
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.CENTER);
    }
}
