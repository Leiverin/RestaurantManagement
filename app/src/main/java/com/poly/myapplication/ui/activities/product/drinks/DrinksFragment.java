package com.poly.myapplication.ui.activities.product.drinks;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.poly.myapplication.R;

public class DrinksFragment extends Fragment {

    private DrinksViewModel mViewModel;

    public static DrinksFragment newInstance() {
        return new DrinksFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(DrinksViewModel.class);
        return inflater.inflate(R.layout.fragment_drinks, container, false);
    }

}