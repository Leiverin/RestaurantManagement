package com.poly.myapplication.ui.activities.product;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.Gson;
import com.poly.myapplication.R;
import com.poly.myapplication.data.models.Product;
import com.poly.myapplication.databinding.ActivityFoodBinding;
import com.poly.myapplication.preference.AppSharePreference;
import com.poly.myapplication.ui.activities.product.adapters.PagerProductAdapter;
import com.poly.myapplication.ui.activities.verify.VerifyActivity;
import com.poly.myapplication.ui.bottomsheet.BottomSheetProduct;
import com.poly.myapplication.utils.helps.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class FoodActivity extends AppCompatActivity {
    private PagerProductAdapter pagerProductAdapter;
    private ActivityFoodBinding binding;
    private FoodViewModel viewModel;
    private BottomSheetProduct bottomSheetProduct;
    public MutableLiveData<Boolean> isScrollingLiveData;
    private boolean isShowing = false;
    private AppSharePreference sharePreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.status_bar_color));
        binding = ActivityFoodBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewModelFactory factory = new ViewModelFactory(this);
        isScrollingLiveData = new MutableLiveData<>();
        viewModel = new ViewModelProvider(this, factory).get(FoodViewModel.class);
        sharePreference = new AppSharePreference(this);

        pagerProductAdapter = new PagerProductAdapter(getSupportFragmentManager(), getLifecycle());
        binding.pager.setAdapter(pagerProductAdapter);
        bottomSheetProduct = new BottomSheetProduct();
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

        initListener();
    }

    private void initListener() {
        binding.viewBottomSheet.setAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_bottom_to_top));
        binding.viewBottomSheet.setVisibility(View.GONE);
        viewModel.getListProductByIdTableLive(sharePreference.getTableId()).observe(this, new Observer<List<Product>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(List<Product> products) {
                if (products != null && products.size() != 0){
                    if (!isShowing){
                        visibleBottomSheet();
                    }
                    isShowing = true;
                    binding.tvTotalDishes.setText("Total: "+products.size()+" dishes");
                    StringBuilder names = new StringBuilder();
                    for (Product product: products) {
                        names.append(product.getName()).append(", ");
                    }
                    binding.tvNameProduct.setText(names);
                }else{
                    hideBottomSheet();
                    isShowing = false;
                }
            }
        });
        isScrollingLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isScrolling) {
                if (isScrolling){
                    hideBottomSheet();
                }else{
                    if (viewModel.getLocalProducts().size() == 0){
                        binding.viewBottomSheet.setVisibility(View.GONE);
                    }else{
                        visibleBottomSheet();
                    }
                }
            }
        });

        binding.viewContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    private void visibleBottomSheet(){
        binding.viewBottomSheet.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_bottom_to_top));
        binding.viewBottomSheet.setVisibility(View.VISIBLE);
    }

    private void hideBottomSheet(){
        binding.viewBottomSheet.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_top_to_bottom));
        binding.viewBottomSheet.setVisibility(View.GONE);
    }


}