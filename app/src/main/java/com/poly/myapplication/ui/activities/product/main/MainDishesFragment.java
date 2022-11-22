package com.poly.myapplication.ui.activities.product.main;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.poly.myapplication.R;
import com.poly.myapplication.data.models.Product;
import com.poly.myapplication.databinding.FragmentMainDishesBinding;
import com.poly.myapplication.ui.activities.product.appetizer.adapter.IOnEventProductListener;
import com.poly.myapplication.ui.activities.product.appetizer.adapter.ProductAdapter;
import com.poly.myapplication.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class MainDishesFragment extends Fragment {

    private FragmentMainDishesBinding binding;
    private MainDishesViewModel mViewModel;
    private ProductAdapter adapter;
    private List<Product> mListMainDishes;

    public static MainDishesFragment newInstance() {
        return new MainDishesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(MainDishesViewModel.class);
        binding = FragmentMainDishesBinding.inflate(getLayoutInflater());
        binding.prgLoadProduct.setVisibility(View.VISIBLE);
        mListMainDishes = new ArrayList<>();
        adapter = new ProductAdapter(mListMainDishes, new IOnEventProductListener() {
            @Override
            public void onClickIncrease(@NonNull Product product, @NonNull TextView textView) {
                Constants.handleIncrease(textView, Constants.TYPE_IN_PRODUCT);
            }

            @Override
            public void onClickDecrease(@NonNull Product product, @NonNull TextView textView) {
                Constants.handleDecrease(textView, Constants.TYPE_IN_PRODUCT);
            }

            @Override
            public void onClickViewItem(@NonNull Product product) {

            }
        });
        binding.rvMainDishes.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvMainDishes.setAdapter(adapter);
        mViewModel.mListProductLiveData.observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                if (products != null){
                    mListMainDishes = products;
                    adapter.setList(products);
                    binding.prgLoadProduct.setVisibility(View.GONE);
                }
            }
        });
        mViewModel.callToGetDrink();
        return binding.getRoot();
    }

}