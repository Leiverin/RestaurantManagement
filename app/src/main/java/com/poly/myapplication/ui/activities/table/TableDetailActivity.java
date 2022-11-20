package com.poly.myapplication.ui.activities.table;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.poly.myapplication.R;
import com.poly.myapplication.data.models.Bill;
import com.poly.myapplication.data.models.Product;
import com.poly.myapplication.data.models.Table;
import com.poly.myapplication.databinding.ActivityTableDetailBinding;
import com.poly.myapplication.ui.activities.product.FoodActivity;
import com.poly.myapplication.ui.activities.table.adapter.IOnItemProductTableListener;
import com.poly.myapplication.ui.activities.table.adapter.ProductTableAdapter;
import com.poly.myapplication.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class TableDetailActivity extends AppCompatActivity {
    private ActivityTableDetailBinding binding;
    private TableDetailViewModel viewModel;
    private List<Product> mListProduct;
    private ProductTableAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(TableDetailViewModel.class);
        binding = ActivityTableDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.status_bar_color));

        mListProduct = new ArrayList();
        Table table = getIntent().getParcelableExtra(Constants.EXTRA_TABLE_TO_DETAIL);
        binding.tvNameTable.setText(table.getName());

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.rvFood.setLayoutManager(layoutManager);

        adapter = new ProductTableAdapter(mListProduct, new IOnItemProductTableListener() {
            @Override
            public void onClickDelete(@NonNull Product product) {

            }

            @Override
            public void onClickDecrease(@NonNull Product product, @NonNull TextView textView) {
                Constants.handleDecrease(textView, Constants.TYPE_IN_TABLE);
            }

            @Override
            public void onClickIncrease(@NonNull Product product, @NonNull TextView textView) {
                Constants.handleIncrease(textView, Constants.TYPE_IN_TABLE);
            }

            @Override
            public void onClickViewItem(@NonNull Product product) {

            }
        });
        binding.rvFood.setAdapter(adapter);

        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TableDetailActivity.this, FoodActivity.class));
            }
        });

        binding.imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TableDetailActivity.this, FoodActivity.class));
            }
        });

        viewModel.mListProductLiveData.observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                if (products != null){
                    mListProduct = products;
                    adapter.setList(products);
                }
                showOrHideView(products);
            }
        });
        viewModel.getListProductInBill(table.getId());
    }

    private void showOrHideView(List<Product> products){
        if (products == null || products.size() == 0){
            binding.rvFood.setVisibility(View.GONE);
            binding.viewNoneItem.setVisibility(View.VISIBLE);
        }else{
            binding.viewNoneItem.setVisibility(View.GONE);
            binding.rvFood.setVisibility(View.VISIBLE);
        }
    }
}