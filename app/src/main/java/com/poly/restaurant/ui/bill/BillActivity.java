package com.poly.restaurant.ui.bill;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.poly.restaurant.databinding.ActivityBillBinding;
import com.poly.restaurant.ui.base.BaseActivity;
import com.poly.restaurant.ui.bill.adapter.PagerBillAdapter;

public class BillActivity extends BaseActivity {
    private ActivityBillBinding binding;
    private PagerBillAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBillBinding.inflate(getLayoutInflater());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(binding.getRoot());
        tabLayout();
    }

    private void tabLayout() {
        adapter = new PagerBillAdapter(getSupportFragmentManager(), getLifecycle());
        binding.pager.setAdapter(adapter);
        new TabLayoutMediator(binding.tabLayout, binding.pager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText("Hóa đơn");
                        break;
                    case 1:
                        tab.setText("Hóa đơn gộp");
                        break;
                }
            }
        }).attach();
    }


}
