package com.poly.myapplication.ui.bill;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;
import com.poly.myapplication.data.models.Bill;
import com.poly.myapplication.databinding.ActivityBillBinding;
import com.poly.myapplication.ui.bill.adapter.BillAdapter;
import com.poly.myapplication.ui.bill.adapter.OnListener;
import com.poly.myapplication.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class BillActivity extends AppCompatActivity {
    private ActivityBillBinding binding;
    private BillAdapter adapter;
    private List<Bill> list;
    private BillViewModel viewModel;
    int current = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(BillViewModel.class);
        binding = ActivityBillBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        list = new ArrayList<>();
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
            public void onSetStatus(int status) {
                int typeBillHis = current + 1;
                onBill(typeBillHis);
            }
        });
        binding.rvBill.setAdapter(adapter);
    }

    private void initViewModel() {
        onBill(current);
        viewModel.mListBillLiveData.observe(this, new Observer<List<Bill>>() {
            @Override
            public void onChanged(List<Bill> bills) {
                list = bills;
                adapter.setList(list);
                Log.d("zzz", new Gson().toJson(list));
            }
        });
        viewModel.getBill();
    }

    private void onBill(int status) {
        viewModel.status=status;
    }
}