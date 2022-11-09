package com.poly.myapplication.ui.activities.product.appetizer;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.poly.myapplication.R;

public class AppetizerFragment extends Fragment {

    private AppetizerViewModel mViewModel;

    public static AppetizerFragment newInstance() {
        return new AppetizerFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(AppetizerViewModel.class);
        return inflater.inflate(R.layout.fragment_appetizer, container, false);
    }
}