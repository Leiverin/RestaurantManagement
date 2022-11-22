package com.poly.myapplication.ui.activities.product.appetizer;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
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
import com.poly.myapplication.data.models.Bill;
import com.poly.myapplication.data.models.Product;
import com.poly.myapplication.databinding.FragmentAppetizerBinding;
import com.poly.myapplication.ui.activities.product.appetizer.adapter.IOnEventProductListener;
import com.poly.myapplication.ui.activities.product.appetizer.adapter.ProductAdapter;
import com.poly.myapplication.utils.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AppetizerFragment extends Fragment {
    private FragmentAppetizerBinding binding;
    private AppetizerViewModel mViewModel;
    private ProductAdapter adapter;
    private List<Product> mListAppetizer;
    private List<Product> mListProductBill;
    public static AppetizerFragment newInstance() {
        return new AppetizerFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(AppetizerViewModel.class);
        binding = FragmentAppetizerBinding.inflate(getLayoutInflater());
        mListAppetizer = new ArrayList<>();
        binding.prgLoadProduct.setVisibility(View.VISIBLE);
        mListProductBill = new ArrayList<>();
        adapter = new ProductAdapter(mListAppetizer, new IOnEventProductListener() {
            @Override
            public void onClickIncrease(@NonNull Product product, TextView tvQuantity) {
                mListProductBill.add(product);
                Constants.handleIncrease(tvQuantity, Constants.TYPE_IN_PRODUCT);
            }

            @Override
            public void onClickDecrease(@NonNull Product product, TextView tvQuantity) {
                Constants.handleDecrease(tvQuantity, Constants.TYPE_IN_PRODUCT);

            }

            @Override
            public void onClickViewItem(@NonNull Product product) {

            }
        });
        binding.rvAppetizer.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvAppetizer.setAdapter(adapter);
        mViewModel.mListAppetizerLiveData.observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                if (products != null){
                    mListAppetizer = products;
                    adapter.setList(products);
                    binding.prgLoadProduct.setVisibility(View.GONE);
                }
            }
        });

        mViewModel.callToGetAppetizer();

        mViewModel.mBillLiveData.observe(getViewLifecycleOwner(), new Observer<Bill>() {
            @Override
            public void onChanged(Bill bill) {
                String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Calendar.getInstance().getTime());
                String time = new SimpleDateFormat("hh:mm", Locale.getDefault()).format(Calendar.getInstance().getTime());
                if (bill == null){
//                    mViewModel.callToCreateBill(new Bill(null, date, time, ));
                }
            }
        });
        return binding.getRoot();
    }
}