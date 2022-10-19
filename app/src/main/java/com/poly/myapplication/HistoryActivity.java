package com.poly.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.poly.myapplication.databinding.ActivityMainBinding;

public class HistoryActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}