package com.poly.restaurant.ui.notification;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.poly.restaurant.databinding.ActivityNotificationBinding;
import com.poly.restaurant.ui.base.BaseActivity;
import com.poly.restaurant.ui.notification.adapter.PagerNotiAdapter;


public class NotificationActivity extends BaseActivity {
    private ActivityNotificationBinding binding;
    private PagerNotiAdapter pagerNotiAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        tabLayout();
    }


    private void tabLayout() {
        pagerNotiAdapter = new PagerNotiAdapter(getSupportFragmentManager(), getLifecycle());
        binding.pager.setAdapter(pagerNotiAdapter);
        new TabLayoutMediator(binding.tabLayout, binding.pager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText("Send notification");
                        break;
                    case 1:
                        tab.setText("Receipt notice");
                        break;
                }
            }
        }).attach();
    }
}