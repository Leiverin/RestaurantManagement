package com.poly.myapplication.ui.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.poly.myapplication.R;
import com.poly.myapplication.databinding.ActivitySplashBinding;

public class SplashActivity extends AppCompatActivity {
    private ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

}