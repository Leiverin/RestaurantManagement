package com.poly.myapplication.ui.activities.product.desserts;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.poly.myapplication.R;
import com.poly.myapplication.data.models.Product;
import com.poly.myapplication.databinding.FragmentDessertBinding;
import com.poly.myapplication.preference.AppSharePreference;
import com.poly.myapplication.ui.activities.product.FoodActivity;
import com.poly.myapplication.ui.activities.product.appetizer.adapter.IOnEventProductListener;
import com.poly.myapplication.ui.activities.product.appetizer.adapter.ProductAdapter;
import com.poly.myapplication.utils.Constants;
import com.poly.myapplication.utils.helps.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class DessertFragment extends Fragment {
    private FragmentDessertBinding binding;
    private DessertViewModel mViewModel;
    private ProductAdapter adapter;
    private List<Product> mListDessert;
    private AppSharePreference sharePreference;
    public static DessertFragment newInstance() {
        return new DessertFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ViewModelFactory factory = new ViewModelFactory(getContext());
        mViewModel = new ViewModelProvider(this, factory).get(DessertViewModel.class);
        binding = FragmentDessertBinding.inflate(getLayoutInflater());
        binding.prgLoadProduct.setVisibility(View.VISIBLE);
        mListDessert = new ArrayList<>();
        sharePreference = new AppSharePreference(getContext());
        adapter = new ProductAdapter(mListDessert, new IOnEventProductListener() {
            @Override
            public void onClickIncrease(@NonNull Product product, @NonNull TextView tvQuantity) {
                Constants.handleIncrease(tvQuantity, Constants.TYPE_IN_PRODUCT);
                int quantity = Integer.parseInt(tvQuantity.getText().toString().subSequence(1, tvQuantity.getText().toString().length()).toString());
                handleAddProduct(product, quantity);
            }

            @Override
            public void onClickDecrease(@NonNull Product product, @NonNull TextView tvQuantity) {
                Constants.handleDecrease(tvQuantity, Constants.TYPE_IN_PRODUCT);
                int quantity = Integer.parseInt(tvQuantity.getText().toString().subSequence(1, tvQuantity.getText().toString().length()).toString());
                handleDecreaseProduct(product, quantity);
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
                    for (int i = 0; i < products.size(); i++){
                        if (mViewModel.getListProduct().size() != 0){
                            for (int j = 0; j < mViewModel.getListProduct().size(); j++){
                                if (products.get(i).getId().equals(mViewModel.getListProduct().get(j).getId())){
                                    products.set(i, mViewModel.getListProduct().get(j));
                                }
                            }
                        }
                    }
                    mListDessert = products;
                    adapter.setList(products);
                    binding.prgLoadProduct.setVisibility(View.GONE);
                }
            }
        });
        mViewModel.callToGetDessert();
        eventScrollRecycleView();
        initListener();

        return binding.getRoot();
    }


    private void eventScrollRecycleView() {
        int height = ((FoodActivity) requireActivity()).findViewById(R.id.view_bottom_sheet).getHeight();
        binding.rvDessert.setPadding(0, 0, 0, height);
        binding.rvDessert.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == 0){
                    ((FoodActivity) requireActivity()).isScrollingLiveData.postValue(false);
                }else{
                    ((FoodActivity) requireActivity()).isScrollingLiveData.postValue(true);
                }
            }
        });
    }

    private void handleDecreaseProduct(Product product, int quantity) {
        if (mViewModel.getProductById(product.getId()) != null){
            if (quantity == 0){
                mViewModel.deleteProduct(product);
            }
            mViewModel.updateProduct(new Product(
                    product.getId(), product.getName(), product.getUrlImage(), product.getPrice(), product.getTotal(), quantity,
                    product.getType(),
                    product.getIdCategory(),
                    sharePreference.getTableId()
            ));

        }
    }

    private void handleAddProduct(Product product, int quantity) {
        if (mViewModel.getProductById(product.getId()) == null){
            mViewModel.insertProduct(new Product(
                    product.getId(), product.getName(), product.getUrlImage(), product.getPrice(), product.getTotal(), quantity,
                    product.getType(),
                    product.getIdCategory(),
                    sharePreference.getTableId()
            ));
        }else{
            mViewModel.updateProduct(new Product(
                    product.getId(), product.getName(), product.getUrlImage(), product.getPrice(), product.getTotal(), quantity,
                    product.getType(),
                    product.getIdCategory(),
                    sharePreference.getTableId()
            ));
        }
    }

    private void initListener() {
        mViewModel.getLocalProductsLiveData().observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                if (products != null && products.size() != 0){

                }
            }
        });
    }

}