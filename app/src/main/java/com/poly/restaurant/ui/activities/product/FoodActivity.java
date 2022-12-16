package com.poly.restaurant.ui.activities.product;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.poly.restaurant.R;
import com.poly.restaurant.data.models.Product;
import com.poly.restaurant.databinding.ActivityFoodBinding;
import com.poly.restaurant.preference.AppSharePreference;
import com.poly.restaurant.ui.activities.product.adapters.PagerProductAdapter;
import com.poly.restaurant.ui.activities.product.appetizer.AppetizerFragment;
import com.poly.restaurant.ui.activities.product.desserts.DessertFragment;
import com.poly.restaurant.ui.activities.product.drinks.DrinksFragment;
import com.poly.restaurant.ui.activities.product.main.MainDishesFragment;
import com.poly.restaurant.ui.base.BaseActivity;
import com.poly.restaurant.ui.bottomsheet.BottomSheetProduct;
import com.poly.restaurant.utils.helps.ViewModelFactory;

import java.util.List;

public class FoodActivity extends BaseActivity {
    private PagerProductAdapter pagerProductAdapter;
    private ActivityFoodBinding binding;
    private FoodViewModel viewModel;
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
                Log.d("TAG", "onChanged: "+ isScrolling);
                if (isScrolling){
                    hideBottomSheet();
                }else{
                    if (viewModel.getListProductByIdTable(sharePreference.getTableId()).size() == 0){
                        binding.viewBottomSheet.setVisibility(View.GONE);
                    }else{
                        visibleBottomSheet();
                    }
                }
            }
        });

        binding.btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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