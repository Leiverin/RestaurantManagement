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
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;
import com.poly.myapplication.R;
import com.poly.myapplication.data.models.Bill;
import com.poly.myapplication.data.models.Table;
import com.poly.myapplication.databinding.ActivityHistoryBinding;
import com.poly.myapplication.databinding.DialogFilterBinding;
import com.poly.myapplication.ui.bill.adapter.OnListener;
import com.poly.myapplication.ui.history.adapter.HistoryAdapter;
import com.poly.myapplication.ui.history.adapter.SpinnerTableAdapter;
import com.poly.myapplication.utils.Constants;
import com.poly.myapplication.utils.view.CustomSpinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class HistoryActivity extends AppCompatActivity implements CustomSpinner.OnSpinnerEventsListener {
    private ActivityHistoryBinding binding;
    private HistoryAdapter adapter;
    private List<Bill> list;
    private List<Table> tableList;
    private HistoryViewModel viewModel;
    private SpinnerTableAdapter spinnerTableAdapter;

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
        tableList = new ArrayList<>();
        initRec();
        initViewModel();
        spinnerTable();
    }

    private void initRec() {
        adapter = new HistoryAdapter(HistoryActivity.this, list, new OnListener() {
            @Override
            public void onClickBill(Bill bill) {
                Constants.dialogShowDetailBill(bill, HistoryActivity.this);
            }

            @Override
            public void onStatus(Bill bill) {

            }
        });
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
        // date first
        DatePickerDialog.OnDateSetListener timeFirst = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                bindingFilter.timeFirstTv.setText("Time first :" + dayOfMonth + "/" + (month + 1) + "/" + year);
            }
        };
        // date second
        DatePickerDialog.OnDateSetListener timeSecond = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                bindingFilter.timeSecondTv.setText("Time second :" + dayOfMonth + "/" + (month + 1) + "/" + year);
            }
        };
        bindingFilter.btnFirstTime.setOnClickListener(view -> {
            new DatePickerDialog(HistoryActivity.this, timeFirst, calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        });
        bindingFilter.btnSecondTime.setOnClickListener(view -> {
            new DatePickerDialog(HistoryActivity.this, timeSecond, calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        });
        bindingFilter.btnCloseDialog.setOnClickListener(view -> {
            dialog.dismiss();
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    private void spinnerTable() {
        binding.spinnerTable.setSpinnerEventsListener(this);
        for (int i = 1; i <= 8; i++) {
            tableList.add(new Table(null, "Table " + i, null, 0, null));
        }
        spinnerTableAdapter = new SpinnerTableAdapter(HistoryActivity.this, tableList);
        binding.spinnerTable.setAdapter(spinnerTableAdapter);
        binding.spinnerTable.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int index = adapterView.getSelectedItemPosition();
                Log.d("abc", String.valueOf(index));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onPopupWindowOpened(Spinner spinner) {
        binding.spinnerTable.setBackground(getResources().getDrawable(R.drawable.bg_spinner_table_up));
    }

    @Override
    public void onPopupWindowClosed(Spinner spinner) {
        binding.spinnerTable.setBackground(getResources().getDrawable(R.drawable.bg_spinner_table));
    }
}