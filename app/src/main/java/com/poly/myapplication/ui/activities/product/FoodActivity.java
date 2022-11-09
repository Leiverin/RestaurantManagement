package com.poly.myapplication.ui.activities.product;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.Window;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.poly.myapplication.R;
import com.poly.myapplication.databinding.ActivityFoodBinding;
import com.poly.myapplication.ui.activities.product.adapters.PagerProductAdapter;

public class FoodActivity extends AppCompatActivity {
    private PagerProductAdapter pagerProductAdapter;
    private ActivityFoodBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.status_bar_color));
        binding = ActivityFoodBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        pagerProductAdapter = new PagerProductAdapter(getSupportFragmentManager(), getLifecycle());
        binding.pager.setAdapter(pagerProductAdapter);
        new TabLayoutMediator(binding.tabLayout, binding.pager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:
                        tab.setText(R.string.appetizer);
                        break;
                    case 1:
                        tab.setText(R.string.main);
                        break;
                    case 2:
                        tab.setText(R.string.desserts);
                        break;
                    case 3:
                        tab.setText(R.string.drinks);
                        break;
                }
            }
        }).attach();
    }

}