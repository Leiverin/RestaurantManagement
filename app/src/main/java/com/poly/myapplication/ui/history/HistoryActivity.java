package com.poly.myapplication.ui.history;

import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.widget.DatePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;
import com.poly.myapplication.R;
import com.poly.myapplication.data.models.Bill;
import com.poly.myapplication.databinding.ActivityHistoryBinding;
import com.poly.myapplication.databinding.DialogFilterBinding;
import com.poly.myapplication.ui.bill.BillViewModel;
import com.poly.myapplication.ui.history.adapter.HistoryAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class HistoryActivity extends AppCompatActivity {
    private ActivityHistoryBinding binding;
    private HistoryAdapter adapter;
    private List<Bill> list;
    private HistoryViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(HistoryViewModel.class);
        binding = ActivityHistoryBinding.inflate(getLayoutInflater());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(binding.getRoot());
        binding.prgLoadBill.setVisibility(View.VISIBLE);
        binding.rvHistory.setVisibility(View.GONE);
        binding.imgFilter.setOnClickListener(view -> {
            dialogBottomSheetFilter();
        });
        list = new ArrayList<>();
        initRec();
        initViewModel();
    }

    private void initRec() {
        adapter = new HistoryAdapter(HistoryActivity.this, list);
        binding.rvHistory.setAdapter(adapter);
    }

    private void initViewModel() {
        viewModel.mListHisLiveData.observe(this, new Observer<List<Bill>>() {
            @Override
            public void onChanged(List<Bill> bills) {
                list = bills;
                adapter.setList(list);
                binding.rvHistory.setVisibility(View.VISIBLE);
                binding.prgLoadBill.setVisibility(View.GONE);
                Log.d("zzz", new Gson().toJson(list));
            }
        });
        viewModel.getHis();
    }

    private void dialogBottomSheetFilter() {
        Dialog dialog = new Dialog(HistoryActivity.this);
        DialogFilterBinding bindingFilter = DialogFilterBinding.inflate(LayoutInflater.from(HistoryActivity.this));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(bindingFilter.getRoot());
        final Calendar calendar = Calendar.getInstance();
        // time first
        DatePickerDialog.OnDateSetListener timeFirst = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                bindingFilter.timeFirstTv.setText("Time first :" + dayOfMonth + "/" + (month + 1) + "/" + year);
            }
        };
        // time second
        DatePickerDialog.OnDateSetListener timeSecond = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                bindingFilter.timeSecondTv.setText("Time second :" + dayOfMonth + "/" + (month + 1) + "/" + year);
            }
        };
        bindingFilter.imgFirstTime.setOnClickListener(view -> {
            new DatePickerDialog(HistoryActivity.this, timeFirst, calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        });
        bindingFilter.imgSecondTime.setOnClickListener(view -> {
            new DatePickerDialog(HistoryActivity.this, timeSecond, calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
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