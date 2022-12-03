package com.poly.restaurant.ui.activities.table;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.poly.restaurant.R;
import com.poly.restaurant.data.models.Bill;
import com.poly.restaurant.data.models.Product;
import com.poly.restaurant.data.models.Table;
import com.poly.restaurant.databinding.ActivityTableDetailBinding;
import com.poly.restaurant.preference.AppSharePreference;
import com.poly.restaurant.ui.activities.product.FoodActivity;
import com.poly.restaurant.ui.activities.table.adapter.IOnItemProductTableListener;
import com.poly.restaurant.ui.activities.table.adapter.ProductTableAdapter;
import com.poly.restaurant.utils.Constants;
import com.poly.restaurant.utils.helps.ViewModelFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

@SuppressLint("SetTextI18n")
public class TableDetailActivity extends AppCompatActivity {
    private ActivityTableDetailBinding binding;
    private TableDetailViewModel viewModel;
    private List<Product> mListProduct;
    private ProductTableAdapter adapter;
    private AppSharePreference sharePreference;
    private boolean isShowing = false;
    private double total = 0;
    private String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Calendar.getInstance().getTime());
    private String time = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(Calendar.getInstance().getTime());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewModelFactory factory = new ViewModelFactory(this);
        viewModel = new ViewModelProvider(this, factory).get(TableDetailViewModel.class);
        binding = ActivityTableDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.status_bar_color));
        mListProduct = new ArrayList();
        sharePreference = new AppSharePreference(this);
        Table table = getIntent().getParcelableExtra(Constants.EXTRA_TABLE_TO_DETAIL);
        sharePreference.setTableId(table.getId());
        binding.tvNameTable.setText(table.getName());

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.rvFood.setLayoutManager(layoutManager);

        adapter = new ProductTableAdapter(mListProduct, new IOnItemProductTableListener() {
            @Override
            public void onClickDelete(@NonNull Product product) {

            }

            @Override
            public void onClickDecrease(@NonNull Product product, @NonNull TextView tvQuantity, int position) {
                Constants.handleDecrease(tvQuantity, Constants.TYPE_IN_TABLE);
                int quantity = Integer.parseInt(tvQuantity.getText().toString().trim());
                handleDecreaseProduct(product, quantity);
                adapter.getMListProduct().get(position).setAmount(quantity);
            }

            @Override
            public void onClickIncrease(@NonNull Product product, @NonNull TextView tvQuantity, int position) {
                Constants.handleIncrease(tvQuantity, Constants.TYPE_IN_TABLE);
                int quantity = Integer.parseInt(tvQuantity.getText().toString().trim());
                handleAddProduct(product, quantity);
                adapter.getMListProduct().get(position).setAmount(quantity);
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

        viewModel.getListProductByIdTableLive(sharePreference.getTableId()).observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                if (products != null && products.size() != 0){
                    if (!isShowing){
                        visibleBottomSheet();
                        isShowing = true;
                    }
                    for (Product product : products) {
                        total += Double.parseDouble((product.getPrice() * product.getAmount())+"");
                    }
                    binding.tvTotalDishes.setText("Total dishes: "+products.size()+" dishes");
                    binding.tvTotalPrice.setText("Total price: "+total+"$");
                    total = 0;
                    mListProduct = products;
                    adapter.setList(products);
                }else{
                    isShowing = false;
                    hideBottomSheet();
                }
                showOrHideView(products);
            }
        });

        eventScrollRecycleView();

        binding.btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.callToCreateBill(new Bill(null, date, time, total, 0, 0, mListProduct, table, "12321312", "6385ade7180bbd1b100746b6"));
            }
        });

        viewModel.wasCreated.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean wasCreated) {
                if (wasCreated){
                    Toast.makeText(TableDetailActivity.this, "Create bill successfully", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(TableDetailActivity.this, "Failed to create bill successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void eventScrollRecycleView() {
        binding.rvFood.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == 0){
                    visibleBottomSheet();
                }else{
                    hideBottomSheet();
                }
            }
        });
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

    private void handleDecreaseProduct(Product product, int quantity) {
        if (viewModel.getProductById(product.getId(), sharePreference.getTableId()) != null){
            if (quantity == 0){
                viewModel.deleteProduct(product);
            }
            viewModel.updateProduct(new Product(
                    product.getIdProduct(),
                    product.getId(), product.getName(), product.getUrlImage(), product.getPrice(), product.getTotal(), quantity,
                    product.getType(),
                    product.getIdCategory(),
                    sharePreference.getTableId()
            ));
        }
    }

    private void handleAddProduct(Product product, int quantity) {
        if (viewModel.getProductById(product.getId(), sharePreference.getTableId()) == null){
            viewModel.insertProduct(new Product(
                    null,
                    product.getId(), product.getName(), product.getUrlImage(), product.getPrice(), product.getTotal(), quantity,
                    product.getType(),
                    product.getIdCategory(),
                    sharePreference.getTableId()
            ));
        }else{
            viewModel.updateProduct(new Product(
                    product.getIdProduct(),
                    product.getId(), product.getName(), product.getUrlImage(), product.getPrice(), product.getTotal(), quantity,
                    product.getType(),
                    product.getIdCategory(),
                    sharePreference.getTableId()
            ));
        }
    }

    private void visibleBottomSheet(){
        binding.viewBottomSheet.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_bottom_to_top));
        binding.viewBottomSheet.setVisibility(View.VISIBLE);
    }

    private void hideBottomSheet(){
        binding.viewBottomSheet.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_top_to_bottom));
        binding.viewBottomSheet.setVisibility(View.GONE);
    }
}