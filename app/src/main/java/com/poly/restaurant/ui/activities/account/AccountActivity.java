package com.poly.restaurant.ui.activities.account;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.poly.restaurant.databinding.ActivityAccountBinding;

public class AccountActivity extends AppCompatActivity {
   private ActivityAccountBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}