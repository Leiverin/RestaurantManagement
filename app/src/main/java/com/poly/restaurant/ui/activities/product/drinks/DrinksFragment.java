package com.poly.restaurant.ui.activities.product.drinks;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.poly.restaurant.R;
import com.poly.restaurant.data.models.Product;
import com.poly.restaurant.databinding.FragmentDrinksBinding;
import com.poly.restaurant.preference.AppSharePreference;
import com.poly.restaurant.ui.activities.product.FoodActivity;
import com.poly.restaurant.ui.activities.product.appetizer.adapter.IOnEventProductListener;
import com.poly.restaurant.ui.activities.product.appetizer.adapter.ProductAdapter;
import com.poly.restaurant.utils.Constants;
import com.poly.restaurant.utils.helps.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class DrinksFragment extends Fragment {
    private FragmentDrinksBinding binding;
    private DrinksViewModel mViewModel;
    private ProductAdapter adapter;
    private List<Product> mListProduct;
    private AppSharePreference sharePreference;
    public static DrinksFragment newInstance() {
        return new DrinksFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ViewModelFactory factory = new ViewModelFactory(getContext());
        mViewModel = new ViewModelProvider(this, factory).get(DrinksViewModel.class);
        binding = FragmentDrinksBinding.inflate(getLayoutInflater());
        initView();
        initListener();

        return binding.getRoot();
    }

    private void initView() {
        binding.prgLoadProduct.setVisibility(View.VISIBLE);
        mListProduct = new ArrayList<>();
        sharePreference = new AppSharePreference(getContext());
        adapter = new ProductAdapter(mListProduct, new IOnEventProductListener() {
            @Override
            public void onClickIncrease(@NonNull Product product, TextView tvQuantity, int position) {
                Constants.handleIncrease(tvQuantity, Constants.TYPE_IN_PRODUCT);
                int quantity = Integer.parseInt(tvQuantity.getText().toString().subSequence(1, tvQuantity.getText().toString().length()).toString());
                handleAddProduct(product, quantity);
                adapter.getMListProduct().get(position).setAmount(quantity);
            }

            @Override
            public void onClickDecrease(@NonNull Product product, TextView tvQuantity, int position) {
                Constants.handleDecrease(tvQuantity, Constants.TYPE_IN_PRODUCT);
                int quantity = Integer.parseInt(tvQuantity.getText().toString().subSequence(1, tvQuantity.getText().toString().length()).toString());
                handleDecreaseProduct(product, quantity);
                adapter.getMListProduct().get(position).setAmount(quantity);
            }

            @Override
            public void onClickViewItem(@NonNull Product product) {

            }
        });
        binding.rvDrinks.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvDrinks.setAdapter(adapter);
        mViewModel.mListDrinkLiveData.observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                if (products != null){
                    for (int i = 0; i < products.size(); i++){
                        if (mViewModel.getListProductByIdTable(sharePreference.getTableId()).size() != 0){
                            for (int j = 0; j < mViewModel.getListProductByIdTable(sharePreference.getTableId()).size(); j++){
                                if (products.get(i).getId().equals(mViewModel.getListProductByIdTable(sharePreference.getTableId()).get(j).getId())){
                                    products.set(i, mViewModel.getListProductByIdTable(sharePreference.getTableId()).get(j));
                                }
                            }
                        }
                    }
                    mListProduct = products;
                    adapter.setList(products);
                    binding.prgLoadProduct.setVisibility(View.GONE);
                }
            }
        });
        eventScrollRecycleView();
        mViewModel.callToGetDrink();
    }

    private void eventScrollRecycleView() {
        int height = requireActivity().findViewById(R.id.view_bottom_sheet).getHeight();
        binding.rvDrinks.setPadding(0, 0, 0, height);
        binding.rvDrinks.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        if (mViewModel.getProductById(product.getId(), sharePreference.getTableId()) != null){
            if (quantity == 0){
                mViewModel.deleteProduct(product);
            }
            mViewModel.updateProduct(new Product(
                    product.getIdProduct(),
                    product.getId(),
                    product.getName(),
                    product.getUrlImage(),
                    product.getPrice(),
                    product.getTotal(),
                    quantity,
                    product.getType(),
                    product.getIdCategory(),
                    sharePreference.getTableId()
            ));
        }
    }

    private void handleAddProduct(Product product, int quantity) {
        if (mViewModel.getProductById(product.getId(), sharePreference.getTableId()) == null){
            mViewModel.insertProduct(new Product(
                    null,
                    product.getId(),
                    product.getName(),
                    product.getUrlImage(),
                    product.getPrice(),
                    product.getTotal(),
                    quantity,
                    product.getType(),
                    product.getIdCategory(),
                    sharePreference.getTableId()
            ));
        }else{
            mViewModel.updateAmountProduct(quantity, product.getId(), sharePreference.getTableId());
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

    @Override
    public void onResume() {
//        adapter.setList(mViewModel.getListProductByIdTable(sharePreference.getTableId()));
        super.onResume();
    }
}