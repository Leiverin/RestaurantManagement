package com.poly.myapplication.ui.bill;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.poly.myapplication.data.models.Bill;
import com.poly.myapplication.databinding.ActivityShowDetailBillBinding;
import com.poly.myapplication.utils.Constants;

public class ShowDetailBillActivity extends AppCompatActivity {
    private ActivityShowDetailBillBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShowDetailBillBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Bill bill = getIntent().getParcelableExtra(Constants.EXTRA_BILL_TO_DETAIL);
        Constants.setNameTable(bill, binding.txtNameTable);
        binding.tvTime.setText(bill.getTime());
        binding.tvDate.setText(bill.getDate());
        binding.tvPrice.setText(bill.getTotalPrice() + "");
//        binding.recyclerview.setAdapter();
    }
}