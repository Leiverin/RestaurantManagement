package com.poly.restaurant.ui.notification;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.poly.restaurant.databinding.ActivityNotificationBinding;


public class NotificationActivity extends AppCompatActivity {
    private ActivityNotificationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}