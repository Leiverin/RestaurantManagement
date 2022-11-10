package com.poly.myapplication.ui.activities.product.main;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.poly.myapplication.R;
import com.poly.myapplication.data.models.Product;
import com.poly.myapplication.ui.activities.product.appetizer.adapter.ProductAdapter;

import java.util.List;

public class MainDishesFragment extends Fragment {

    private MainDishesViewModel mViewModel;
    private ProductAdapter adapter;
    public static MainDishesFragment newInstance() {
        return new MainDishesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(MainDishesViewModel.class);
//        adapter = new ProductAdapter()
        mViewModel.mListProductLiveData.observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {

            }
        });
        mViewModel.callToGetDrink();
        return inflater.inflate(R.layout.fragment_main_dishes, container, false);
    }

}