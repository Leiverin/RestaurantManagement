package com.poly.restaurant.ui.history;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.Spinner;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.poly.restaurant.R;
import com.poly.restaurant.data.models.Bill;
import com.poly.restaurant.data.models.Table;
import com.poly.restaurant.databinding.ActivityHistoryBinding;
import com.poly.restaurant.databinding.DialogFilterBinding;
import com.poly.restaurant.ui.activities.manage.TableManageViewModel;
import com.poly.restaurant.ui.base.BaseActivity;
import com.poly.restaurant.ui.bill.adapter.OnListener;
import com.poly.restaurant.ui.feedback.FeedBackActivity;
import com.poly.restaurant.ui.history.adapter.HistoryAdapter;
import com.poly.restaurant.ui.history.adapter.SpinnerTableAdapter;
import com.poly.restaurant.utils.Constants;
import com.poly.restaurant.utils.view.CustomSpinner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


public class HistoryActivity extends BaseActivity implements CustomSpinner.OnSpinnerEventsListener {
    private ActivityHistoryBinding binding;
    private HistoryAdapter adapter;
    private List<Bill> list;
    private List<Table> tableList;
    private HistoryViewModel viewModel;
    private TableManageViewModel tableManageViewModel;
    private SpinnerTableAdapter spinnerTableAdapter;
    private String firstDateParam, secondDateParam;
    private Table table;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(HistoryViewModel.class);
        tableManageViewModel = new ViewModelProvider(this).get(TableManageViewModel.class);
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
            public void onClickFeedback(Bill bill) {
                Intent intent = new Intent(HistoryActivity.this, FeedBackActivity.class);
                intent.putExtra(Constants.CREATE_FEEDBACK, bill);
                startActivity(intent);
            }

        });
        binding.rvHistory.setAdapter(adapter);
    }

    private void initViewModel() {
        viewModel.mListHisLiveData.observe(this, new Observer<List<Bill>>() {
            @Override
            public void onChanged(List<Bill> bills) {
                if (bills.isEmpty()) {
                    binding.empty.setVisibility(View.VISIBLE);
                    binding.prgLoadBill.setVisibility(View.GONE);
                } else {
                    list = bills;
                    adapter.setList(list);
                    binding.rvHistory.setVisibility(View.VISIBLE);
                    binding.prgLoadBill.setVisibility(View.GONE);
                    binding.empty.setVisibility(View.GONE);
                    spinnerTable();
                }

            }
        });
        viewModel.getHis(Constants.staff.getFloor().getNumberFloor(), Constants.staff.getId());
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
                bindingFilter.timeFirstTv.setText("Date first :" + dayOfMonth + "/" + (month + 1) + "/" + year);
                String firstDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                SimpleDateFormat dateFormatOfStringInDB = new SimpleDateFormat("dd/MM/yyyy");
                Date d1 = null;
                try {
                    d1 = dateFormatOfStringInDB.parse(firstDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                SimpleDateFormat dateFormatYouWant = new SimpleDateFormat("dd/MM/yyyy");
                firstDateParam = dateFormatYouWant.format(d1);

            }
        };
        // date second
        DatePickerDialog.OnDateSetListener timeSecond = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                bindingFilter.timeSecondTv.setText("Date second :" + dayOfMonth + "/" + (month + 1) + "/" + year);
                String secondDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                SimpleDateFormat dateFormatOfStringInDB = new SimpleDateFormat("dd/MM/yyyy");
                Date d1 = null;
                try {
                    d1 = dateFormatOfStringInDB.parse(secondDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                SimpleDateFormat dateFormatYouWant = new SimpleDateFormat("dd/MM/yyyy");
                secondDateParam = dateFormatYouWant.format(d1);

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
            viewModel.mListHisLiveData.observe(this, new Observer<List<Bill>>() {
                @Override
                public void onChanged(List<Bill> bills) {
                    if(bills.isEmpty()){
                        binding.empty.setVisibility(View.VISIBLE);
                    }else {
                        list = bills;
                        adapter.setList(list);
                        binding.empty.setVisibility(View.GONE);
                    }

                }
            });
            viewModel.getBillByDate(table.getId(), 3, firstDateParam, secondDateParam, Constants.staff.getFloor().getNumberFloor(), Constants.staff.getId());
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
        getTableSpin();
        spinnerTableAdapter = new SpinnerTableAdapter(HistoryActivity.this, tableList);
        binding.spinnerTable.setAdapter(spinnerTableAdapter);
        binding.spinnerTable.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                table = tableList.get(i);
                filter(table.getName());
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

    private void filter(String text) {
        List<Bill> billList = new ArrayList<>();
        for (Bill bill : list) {
            if (bill.getTable().getName().contains(text)) {
                billList.add(bill);
                binding.empty.setVisibility(View.GONE);
            }
            if(billList.isEmpty()){
                binding.empty.setVisibility(View.VISIBLE);
            }
        }
        adapter.setList(billList);
    }

    private void getTableSpin() {
        tableManageViewModel.mListTableLiveData.observe(this, new Observer<List<Table>>() {
            @Override
            public void onChanged(List<Table> tables) {
                Collections.sort(tables, new Comparator<Table>() {
                    @Override
                    public int compare(Table t1, Table t2) {
                        return t1.getName().compareTo(t2.getName());
                    }
                });
                tableList = tables;
                spinnerTableAdapter.setList(tables);
                binding.rvHistory.setVisibility(View.VISIBLE);
                binding.prgLoadBill.setVisibility(View.GONE);
            }
        });
        tableManageViewModel.callToGetTable(Constants.staff.getFloor().getNumberFloor());
    }
}