package com.poly.myapplication.ui.bill;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;
import com.poly.myapplication.R;
import com.poly.myapplication.data.models.Bill;
import com.poly.myapplication.databinding.ActivityBillBinding;
import com.poly.myapplication.databinding.DialogFilterBinding;
import com.poly.myapplication.ui.bill.adapter.BillAdapter;
import com.poly.myapplication.ui.bill.adapter.OnListener;
import com.poly.myapplication.utils.Constants;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BillActivity extends AppCompatActivity {
    private ActivityBillBinding binding;
    private BillAdapter adapter;
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
        binding.imgFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBottomSheetFilter();
            }
        });
        initRec();
        initViewModel();
    }

    private void initRec() {
        adapter = new BillAdapter(this, list, new OnListener() {
            @Override
            public void onClickBill(Bill bill) {
                Intent intent = new Intent(BillActivity.this, ShowDetailBillActivity.class);
                intent.putExtra(Constants.EXTRA_BILL_TO_DETAIL, bill);
                startActivity(intent);
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
                binding.rvBill.setVisibility(View.VISIBLE);
                binding.prgLoadBill.setVisibility(View.GONE);
                Log.d("zzz", new Gson().toJson(list));
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
                bindingFilter.timeFirstTv.setText("Time first :" + selectedHour + ":" + selectedMinute);
            }
        };
        bindingFilter.imgFirstTime.setOnClickListener(view -> {
            new TimePickerDialog(BillActivity.this, timeFirst, calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE), true).show();
        });
        bindingFilter.imgSecondTime.setOnClickListener(view -> {
            new TimePickerDialog(BillActivity.this, timeSecond, calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE), true).show();
        });
        bindingFilter.btnCloseDialog.setOnClickListener(view -> {
            dialog.dismiss();
        });
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }
}
