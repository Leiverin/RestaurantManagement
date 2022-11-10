package com.poly.myapplication.ui.activities.product.desserts;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.poly.myapplication.R;
import com.poly.myapplication.data.models.Product;

import java.util.List;

public class DessertFragment extends Fragment {

    private DessertViewModel mViewModel;

    public static DessertFragment newInstance() {
        return new DessertFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(DessertViewModel.class);

        mViewModel.mListDessertLiveData.observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {

            }
        });
        mViewModel.callToGetDessert();
        return inflater.inflate(R.layout.fragment_dessert, container, false);
    }

}