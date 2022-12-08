package com.poly.restaurant.ui;

import android.os.Bundle;

import com.poly.restaurant.databinding.ActivityFeedBackBinding;
import com.poly.restaurant.ui.base.BaseActivity;

public class FeedBackActivity extends BaseActivity {
    private ActivityFeedBackBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFeedBackBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}