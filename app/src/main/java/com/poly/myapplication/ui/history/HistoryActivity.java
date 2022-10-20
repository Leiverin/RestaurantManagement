package com.poly.myapplication.ui.history;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.poly.myapplication.databinding.ActivityHistoryBinding;


public class HistoryActivity extends AppCompatActivity {
    private ActivityHistoryBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}