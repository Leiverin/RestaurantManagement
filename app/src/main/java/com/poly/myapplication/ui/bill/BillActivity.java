package com.poly.myapplication.ui.bill;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        });
        binding.rvBill.setAdapter(adapter);
    }

    private void initViewModel() {
        int a = 10;
        for (int i = 0; i < a; i++) {
            list.add(new Bill("ta", 100, "9:30", "13-01-2002"));
        }
    }
}