package com.poly.myapplication.ui.activities.product.desserts;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.poly.myapplication.R;
import com.poly.myapplication.data.models.Product;
import com.poly.myapplication.databinding.FragmentDessertBinding;
import com.poly.myapplication.ui.activities.product.appetizer.adapter.IOnEventProductListener;
import com.poly.myapplication.ui.activities.product.appetizer.adapter.ProductAdapter;
import com.poly.myapplication.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class DessertFragment extends Fragment {
    private FragmentDessertBinding binding;
    private DessertViewModel mViewModel;
    private ProductAdapter adapter;
    private List<Product> mListDessert;
    public static DessertFragment newInstance() {
        return new DessertFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(DessertViewModel.class);
        binding = FragmentDessertBinding.inflate(getLayoutInflater());
        mListDessert = new ArrayList<>();
        adapter = new ProductAdapter(mListDessert, new IOnEventProductListener() {
            @Override
            public void onClickIncrease(@NonNull Product product, @NonNull TextView textView) {
                Constants.handleIncrease(textView);
            }

            @Override
            public void onClickDecrease(@NonNull Product product, @NonNull TextView textView) {
                Constants.handleDecrease(textView);
            }

            @Override
            public void onClickViewItem(@NonNull Product product) {

            }
        });
        binding.rvDessert.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvDessert.setAdapter(adapter);
        mViewModel.mListDessertLiveData.observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                if (products != null){
                    mListDessert = products;
                    adapter.setList(products);
                }
            }
        });
        mViewModel.callToGetDessert();
        return binding.getRoot();
    }

}