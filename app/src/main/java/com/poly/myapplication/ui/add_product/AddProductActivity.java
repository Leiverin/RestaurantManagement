package com.poly.myapplication.ui.add_product;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.poly.myapplication.R;
import com.poly.myapplication.databinding.ActivityAddProductBinding;
import com.poly.myapplication.ui.add_product.adapter.ViewPager2Adapter;

public class AddProductActivity extends AppCompatActivity {
    private ActivityAddProductBinding binding;
    public static String[] categories = new String[]{"Appetizer","Main dishes","Desserts","Drinks"};
    private ViewPager2Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        adapter = new ViewPager2Adapter(this);
        binding.viewpager2.setAdapter(adapter);
        new TabLayoutMediator(binding.tabLayout,binding.viewpager2,((tab, position) -> {
            tab.setText(categories[position]);
        })).attach();
        binding.viewpager2.setCurrentItem(1,false);



    }
}