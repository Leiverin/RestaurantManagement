package com.poly.restaurant.ui.activities.verify;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.poly.restaurant.data.models.Product;
import com.poly.restaurant.databinding.ActivityVerifyBinding;
import com.poly.restaurant.preference.AppSharePreference;
import com.poly.restaurant.ui.activities.product.appetizer.adapter.IOnEventProductListener;
import com.poly.restaurant.ui.activities.product.appetizer.adapter.ProductAdapter;
import com.poly.restaurant.ui.base.BaseActivity;
import com.poly.restaurant.utils.Constants;
import com.poly.restaurant.utils.helps.ViewModelFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class VerifyActivity extends BaseActivity {
    private ActivityVerifyBinding binding;
    private VerifyViewModel mViewModel;
    private ProductAdapter adapter;
    private List<Product> mListProduct;
    private AppSharePreference sharePreference;
    private String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Calendar.getInstance().getTime());
    private String time = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(Calendar.getInstance().getTime());
    private double total = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVerifyBinding.inflate(getLayoutInflater());
        ViewModelFactory factory = new ViewModelFactory(this);
        mViewModel = new ViewModelProvider(this, factory).get(VerifyViewModel.class);
        setContentView(binding.getRoot());
        initView();
    }

    private void initView() {
        sharePreference = new AppSharePreference(this);
        mListProduct = new ArrayList<>();
        adapter = new ProductAdapter(mListProduct, new IOnEventProductListener() {
            @Override
            public void onClickIncrease(@NonNull Product product, @NonNull TextView tvQuantity, int position) {
                Constants.handleIncrease(tvQuantity, Constants.TYPE_IN_PRODUCT);
                int quantity = Integer.parseInt(tvQuantity.getText().toString().subSequence(1, tvQuantity.getText().toString().length()).toString());
                handleAddProduct(product, quantity);
                adapter.getMListProduct().get(position).setAmount(quantity);
            }

            @Override
            public void onClickDecrease(@NonNull Product product, @NonNull TextView tvQuantity, int position) {
                Constants.handleDecrease(tvQuantity, Constants.TYPE_IN_PRODUCT);
                int quantity = Integer.parseInt(tvQuantity.getText().toString().subSequence(1, tvQuantity.getText().toString().length()).toString());
                handleDecreaseProduct(product, quantity);
                adapter.getMListProduct().get(position).setAmount(quantity);
            }

            @Override
            public void onClickViewItem(@NonNull Product product) {

            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.rvProductDetail.setLayoutManager(layoutManager);
        binding.rvProductDetail.setAdapter(adapter);

        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mViewModel.callToCreateBill(new Bill(
//                        null,
//                        date,
//                        time,
//                        total,
//                        0,
//                        0,
//                        mListProduct,
//                        sharePreference.getTableId(),
//                        "12321312",
//                        "6385ade7180bbd1b100746b6"
//                        ));
            }
        });
        mViewModel.getListProductByIdTableLive(sharePreference.getTableId()).observe(this, new Observer<List<Product>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(List<Product> products) {
                mListProduct = products;
                adapter.setList(products);

                for (Product product : products) {
                    total += Double.parseDouble((product.getPrice() * product.getAmount())+"");
                }
                binding.tvTotalDishes.setText("Total dishes: "+products.size()+" dishes");
                binding.tvTotalPrice.setText("Total price: "+total+"$");
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
                    product.getId(), product.getName(), product.getUrlImage(), product.getPrice(),
                    product.getDescription(),
                    product.getTotal(), quantity,
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
                    product.getId(), product.getName(), product.getUrlImage(), product.getPrice(),
                    product.getDescription(),
                    product.getTotal(), quantity,
                    product.getType(),
                    product.getIdCategory(),
                    sharePreference.getTableId()
            ));
        }else{
            mViewModel.updateProduct(new Product(
                    product.getIdProduct(),
                    product.getId(), product.getName(), product.getUrlImage(), product.getPrice(),
                    product.getDescription(),
                    product.getTotal(), quantity,
                    product.getType(),
                    product.getIdCategory(),
                    sharePreference.getTableId()
            ));
        }
    }

}
