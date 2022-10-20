package com.poly.myapplication.ui.Bill;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.poly.myapplication.databinding.ActivityBillBinding;

public class BillActivity extends AppCompatActivity {
    private ActivityBillBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityBillBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}