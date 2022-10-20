package com.poly.myapplication.ui.notification;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.poly.myapplication.databinding.ActivityNotificationBinding;

public class NotificationActivity extends AppCompatActivity {
    private ActivityNotificationBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityNotificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}