package com.poly.myapplication.ui.add_product;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.poly.myapplication.R;
import com.poly.myapplication.databinding.ActivityFoodBinding;
import com.poly.myapplication.ui.add_product.adapter.ViewPager2Adapter;

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
        binding.viewPager2Main.setAdapter(adapter);
        new TabLayoutMediator(binding.tabLayout,binding.viewPager2Main,((tab, position) -> {
            tab.setText(categories[position]);
        })).attach();
        binding.viewPager2Main.setCurrentItem(1,false);



    }
}