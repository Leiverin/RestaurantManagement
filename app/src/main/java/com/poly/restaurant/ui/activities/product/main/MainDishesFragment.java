package com.poly.restaurant.ui.activities.product.main;

import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.MutableLiveData;
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
import android.widget.Toast;

import com.poly.restaurant.R;
import com.poly.restaurant.data.models.Product;
import com.poly.restaurant.databinding.FragmentMainDishesBinding;
import com.poly.restaurant.preference.AppSharePreference;
import com.poly.restaurant.ui.activities.product.FoodActivity;
import com.poly.restaurant.ui.activities.product.appetizer.adapter.IOnEventProductListener;
import com.poly.restaurant.ui.activities.product.appetizer.adapter.ProductAdapter;
import com.poly.restaurant.ui.base.BaseFragment;
import com.poly.restaurant.utils.Constants;
import com.poly.restaurant.utils.helps.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class MainDishesFragment extends BaseFragment {

    private FragmentMainDishesBinding binding;
    private MainDishesViewModel mViewModel;
    private ProductAdapter adapter;
    private List<Product> mListMainDishes;
    private AppSharePreference sharePreference;

    public static MainDishesFragment newInstance() {
        return new MainDishesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ViewModelFactory factory = new ViewModelFactory(getContext());
        mViewModel = new ViewModelProvider(this, factory).get(MainDishesViewModel.class);
        binding = FragmentMainDishesBinding.inflate(getLayoutInflater());
        sharePreference = new AppSharePreference(getContext());
        binding.prgLoadProduct.setVisibility(View.VISIBLE);
        mListMainDishes = new ArrayList<>();
        adapter = new ProductAdapter(mListMainDishes, new IOnEventProductListener() {
            @Override
            public void onClickIncrease(@NonNull Product product, @NonNull TextView tvQuantity, int position) {
                Constants.handleIncrease(tvQuantity, Constants.TYPE_IN_PRODUCT);
                int quantity = Integer.parseInt(tvQuantity.getText().toString().subSequence(1, tvQuantity.getText().toString().length()).toString());
                if (quantity <= product.getTotal()){
                    handleAddProduct(product, quantity);
                    tvQuantity.setText("x" + quantity);
                    adapter.getMListProduct().get(position).setAmount(quantity);
                }else{
                    Toast.makeText(requireContext(), "Không được vượt quá sản lượng", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onClickDecrease(@NonNull Product product, @NonNull TextView tvQuantity, int position) {
                int quantity = Integer.parseInt(tvQuantity.getText().toString().subSequence(1, tvQuantity.getText().toString().length()).toString());
                if (quantity > 0){
                    quantity --;
                    tvQuantity.setText("x"+quantity);
                    handleDecreaseProduct(product, quantity);
                    adapter.getMListProduct().get(position).setAmount(quantity);
                }
            }

            @Override
            public void onClickViewItem(@NonNull Product product) {

            }
        });
        binding.rvMainDishes.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvMainDishes.setAdapter(adapter);
        mViewModel.mListProductLiveData.observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                if (products != null){
                    for (int i = 0; i < products.size(); i++){
                        if (mViewModel.getListProduct().size() != 0){
                            for (int j = 0; j < mViewModel.getListProductByIdTable(sharePreference.getTableId()).size(); j++){
                                if (products.get(i).getId().equals(mViewModel.getListProductByIdTable(sharePreference.getTableId()).get(j).getId())){
                                    products.set(i, mViewModel.getListProductByIdTable(sharePreference.getTableId()).get(j));
                                }
                            }
                        }
                    }
                    mListMainDishes = products;
                    adapter.setList(products);
                    binding.prgLoadProduct.setVisibility(View.GONE);
                }
            }
        });

        initListener();
        eventScrollRecycleView();
        mViewModel.callToGetDrink();
        return binding.getRoot();
    }

    private void handleDecreaseProduct(Product product, int quantity) {
        if (mViewModel.getProductById(product.getId(), sharePreference.getTableId()) != null){
            if (quantity == 0){
                mViewModel.deleteProduct(product.getId(), sharePreference.getTableId());
            }else{
                mViewModel.updateAmountProduct(quantity, product.getId(), sharePreference.getTableId());
            }
        }
    }

    private boolean isScrolling = true;
    private void eventScrollRecycleView() {
        int height = ((FoodActivity) requireActivity()).findViewById(R.id.view_bottom_sheet).getHeight();
        binding.rvMainDishes.setPadding(0, 0, 0, height);
        binding.rvMainDishes.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy < 0){
                    if(!isScrolling){
                        ((FoodActivity) requireActivity()).isScrollingLiveData.postValue(false);
                        isScrolling = !isScrolling;
                    }
                }else if (dy > 0){
                    if (isScrolling){
                        ((FoodActivity) requireActivity()).isScrollingLiveData.postValue(true);
                        isScrolling = !isScrolling;
                    }
                }
            }
        });
    }

    private void handleAddProduct(Product product, int quantity) {
        if (mViewModel.getProductById(product.getId(), sharePreference.getTableId()) == null){
            mViewModel.insertProduct(new Product(null,
                    product.getId(), product.getName(), product.getUrlImage(), product.getPrice(), product.getDescription(), product.getTotal(), quantity,
                    product.getType(),
                    product.getIdCategory(),
                    sharePreference.getTableId(),
                    0
            ));
        }else{
            mViewModel.updateAmountProduct(quantity, product.getId(), sharePreference.getTableId());
        }
    }

    private void initListener() {
        SearchView searchView = ((FoodActivity) getActivity()).findViewById(R.id.sv_food);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Constants.filterList(newText, mListMainDishes, adapter);
                return true;
            }
        });
    }

    @Override
    public void onResume() {
        initListener();
//        adapter.setList(mViewModel.getListProductByIdTable(sharePreference.getTableId()));
        super.onResume();
    }
}
