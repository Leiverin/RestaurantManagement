package com.poly.restaurant.ui.contact;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.poly.restaurant.databinding.ActivityContactBinding;

public class ContactActivity extends AppCompatActivity {
    private ActivityContactBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContactBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}