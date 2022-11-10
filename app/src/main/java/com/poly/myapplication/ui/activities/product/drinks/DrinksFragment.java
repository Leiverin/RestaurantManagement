package com.poly.myapplication.ui.activities.product.drinks;

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
import com.poly.myapplication.ui.activities.product.appetizer.adapter.IOnEventProductListener;
import com.poly.myapplication.ui.activities.product.appetizer.adapter.ProductAdapter;

import java.util.ArrayList;
import java.util.List;

public class DrinksFragment extends Fragment {

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
        mListProduct = new ArrayList<>();

        adapter = new ProductAdapter(mListProduct, new IOnEventProductListener() {
            @Override
            public void onClickIncrease(@NonNull Product product) {

            }

            @Override
            public void onClickDecrease(@NonNull Product product) {

            }

            @Override
            public void onClickViewItem(@NonNull Product product) {

            }
        });

        mViewModel.mListDrinkLiveData.observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                if (products != null){
                    mListProduct = products;
                }
            }
        });
        mViewModel.callToGetDrink();
        return inflater.inflate(R.layout.fragment_drinks, container, false);
    }

}