package com.poly.restaurant.ui.add_product;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayoutMediator;
import com.poly.restaurant.R;
import com.poly.restaurant.databinding.ActivityFoodBinding;
import com.poly.restaurant.ui.add_product.adapter.ViewPager2Adapter;

public class AddProductActivity extends AppCompatActivity {
    private ActivityFoodBinding binding;
    public static String[] categories = new String[]{"Appetizer","Main dishes","Desserts","Drinks"};
    private ViewPager2Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFoodBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        adapter = new ViewPager2Adapter(this);
        binding.pager.setAdapter(adapter);
        new TabLayoutMediator(binding.tabLayout,binding.pager,((tab, position) -> {
            tab.setText(categories[position]);
        })).attach();
        binding.pager.setCurrentItem(1,false);



    }
}