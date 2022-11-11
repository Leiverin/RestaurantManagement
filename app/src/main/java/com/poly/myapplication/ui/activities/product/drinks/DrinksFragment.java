package com.poly.myapplication.ui.activities.product.drinks;

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
import com.poly.myapplication.databinding.FragmentDrinksBinding;
import com.poly.myapplication.ui.activities.product.appetizer.adapter.IOnEventProductListener;
import com.poly.myapplication.ui.activities.product.appetizer.adapter.ProductAdapter;
import com.poly.myapplication.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class DrinksFragment extends Fragment {
    private FragmentDrinksBinding binding;
    private DrinksViewModel mViewModel;
    private ProductAdapter adapter;
    private List<Product> mListProduct;
    public static DrinksFragment newInstance() {
        return new DrinksFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(DrinksViewModel.class);
        binding = FragmentDrinksBinding.inflate(getLayoutInflater());
        mListProduct = new ArrayList<>();
        adapter = new ProductAdapter(mListProduct, new IOnEventProductListener() {
            @Override
            public void onClickIncrease(@NonNull Product product, TextView tvQuantity) {
                Constants.handleIncrease(tvQuantity);
            }

            @Override
            public void onClickDecrease(@NonNull Product product, TextView tvQuantity) {
                Constants.handleDecrease(tvQuantity);
            }

            @Override
            public void onClickViewItem(@NonNull Product product) {

            }
        });
        binding.rvDrinks.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvDrinks.setAdapter(adapter);
        mViewModel.mListDrinkLiveData.observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                if (products != null){
                    mListProduct = products;
                    adapter.setList(products);
                }
            }
        });
        mViewModel.callToGetDrink();
        return binding.getRoot();
    }

}