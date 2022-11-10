package com.poly.myapplication.ui.activities.product.appetizer;

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

import java.util.List;

public class AppetizerFragment extends Fragment {

    private AppetizerViewModel mViewModel;

    public static AppetizerFragment newInstance() {
        return new AppetizerFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(AppetizerViewModel.class);

        mViewModel.mListAppetizerLiveData.observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {

            }
        });

        return inflater.inflate(R.layout.fragment_appetizer, container, false);
    }
}