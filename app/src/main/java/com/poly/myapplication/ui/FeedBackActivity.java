package com.poly.myapplication.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.poly.myapplication.databinding.ActivityFeedBackBinding;

public class FeedBackActivity extends AppCompatActivity {
    private ActivityFeedBackBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityFeedBackBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}