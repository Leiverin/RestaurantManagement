package com.poly.myapplication.ui.activities.product.desserts;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.poly.myapplication.R;

public class DessertFragment extends Fragment {

    private DessertViewModel mViewModel;

    public static DessertFragment newInstance() {
        return new DessertFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(DessertViewModel.class);
        return inflater.inflate(R.layout.fragment_dessert, container, false);
    }

}