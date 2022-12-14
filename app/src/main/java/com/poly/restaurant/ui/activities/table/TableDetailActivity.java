package com.poly.restaurant.ui.activities.table;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.poly.restaurant.R;
import com.poly.restaurant.data.models.Bill;
import com.poly.restaurant.data.models.Product;
import com.poly.restaurant.data.models.Table;
import com.poly.restaurant.databinding.ActivityTableDetailBinding;
import com.poly.restaurant.preference.AppSharePreference;
import com.poly.restaurant.ui.activities.MainActivity;
import com.poly.restaurant.ui.activities.product.FoodActivity;
import com.poly.restaurant.ui.activities.table.adapter.IOnItemProductTableListener;
import com.poly.restaurant.ui.activities.table.adapter.ProductTableAdapter;
import com.poly.restaurant.ui.base.BaseActivity;
import com.poly.restaurant.ui.bill.BillActivity;
import com.poly.restaurant.utils.Constants;
import com.poly.restaurant.utils.helps.ViewModelFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

@SuppressLint("SetTextI18n")
public class TableDetailActivity extends BaseActivity {
    private ActivityTableDetailBinding binding;
    private TableDetailViewModel viewModel;
    private List<Product> mListProduct;
    private ProductTableAdapter adapter;
    private AppSharePreference sharePreference;
    private Table table;
    private boolean isShowing = false;
    private int type = 0;
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
        table = getIntent().getParcelableExtra(Constants.EXTRA_TABLE_TO_DETAIL);
        sharePreference.setTableId(table.getId());
        binding.tvNameTable.setText(table.getName());
        binding.imgDone.setVisibility(View.GONE);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.rvFood.setLayoutManager(layoutManager);

        adapter = new ProductTableAdapter(mListProduct, new IOnItemProductTableListener() {
            @Override
            public void onClickDelete(@NonNull Product product) {
                viewModel.deleteProduct(product);
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

        eventScrollRecycleView();
        initEvent();
        initEventViewModel();

        viewModel.checkBillAlreadyExists(table.getId());

        if (!sharePreference.getTableId().equals(sharePreference.getBeforeTableId()) && viewModel.getListProductByIdTable(table.getId()).size() == 0){
            viewModel.callToGetBillExist(sharePreference.getTableId(), Constants.TYPE_NON_CLICK);
            sharePreference.setBeforeTableId(table.getId());
        }

        LocalBroadcastManager.getInstance(this).registerReceiver((receiver),
                new IntentFilter(Constants.REQUEST_TO_ACTIVITY)
        );

    }

    @Override
    protected void onResume() {
        LocalBroadcastManager.getInstance(this).registerReceiver((receiver),
                new IntentFilter(Constants.REQUEST_TO_ACTIVITY)
        );
        super.onResume();
    }

    private void initEventViewModel() {
        viewModel.getListProductByIdTableLive(sharePreference.getTableId()).observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                if (products != null && products.size() != 0){
                    if (!isShowing){
                        visibleBottomSheet();
                        isShowing = true;
                    }
                    total = 0;
                    for (Product product : products) {
                        total += Double.parseDouble((product.getPrice() * product.getAmount())+"");
                    }
                    binding.tvTotalDishes.setText("Total dishes: "+products.size()+" dishes");
                    binding.tvTotalPrice.setText("Total price: "+total+"$");
                    mListProduct = products;
                    adapter.setList(products);
                }else{
                    isShowing = false;
                    hideBottomSheet();
                }
                showOrHideView(products);
            }
        });

        /**
         * Bill has been create and push notification to chef and manager
         * */
        viewModel.wasBillCreated.observe(this, new Observer<Bill>() {
            @Override
            public void onChanged(Bill bill) {
                if (bill != null){
                    viewModel.callToPushNotification(
                            "dTKEeNa0QdOK-m0_NROzsl:APA91bHya_ttWelcBJUKidukxlU0ocK-pHbh9eaWJ8mj81BqV6c00A55RVxSr9fuH4itQmwZHYsSoAPXDggDHS9ONs7NcHAoi0ovverLzX26CaKC4aFSMg3KqEZZ8kwCkvUgWXD8vXQ_",
                            "Thông báo",
                            "Bill bàn "+bill.getTable().getName()+" đã được tạo. Hành động thôi nào :))",
                            bill.getId());
                    Table tableUpdate = new Table(table.getId(), table.getName(), table.getFloor(), table.getCapacity(), 1);
                    viewModel.updateTable(table.getId(), tableUpdate);
                    binding.btnOrder.setBackgroundResource(R.drawable.bg_btn_order_black);
                    binding.btnOrder.setText("Update");
                    binding.tvStatus.setText("Đang giao cho nhà bếp xử lý");
                    Toast.makeText(TableDetailActivity.this, "Create bill successfully", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(TableDetailActivity.this, "Failed to create bill successfully", Toast.LENGTH_SHORT).show();
                }
                binding.btnOrder.setEnabled(true);
            }
        });

        /**
         * If bill already exist, update it. If not, create it
         * */
        viewModel.mBillLiveData.observe(this, new Observer<List<Bill>>() {
            @Override
            public void onChanged(List<Bill> bill) {
                if (bill != null && bill.size() != 0){
                    if (mListProduct.size() != 0){
                        /**
                         * Update bill
                         * */
                        Table tableUpdate = new Table(table.getId(), table.getName(), table.getFloor(), table.getCapacity(), 1);
                        viewModel.callToUpdateBill(bill.get(0).getId(), new Bill(bill.get(0).getId(), date, time, total, 0, 0, mListProduct,
                                tableUpdate,
                                null, Constants.staff), Constants.TYPE_UPDATE);
                        viewModel.callToPushNotification(
                                "dTKEeNa0QdOK-m0_NROzsl:APA91bHya_ttWelcBJUKidukxlU0ocK-pHbh9eaWJ8mj81BqV6c00A55RVxSr9fuH4itQmwZHYsSoAPXDggDHS9ONs7NcHAoi0ovverLzX26CaKC4aFSMg3KqEZZ8kwCkvUgWXD8vXQ_",
                                "Thông báo bổ sung món",
                                "Bill bàn "+bill.get(0).getTable().getName()+" vừa bổ sung thêm món",
                                bill.get(0).getId());
                    }else{
                        Toast.makeText(TableDetailActivity.this, "No products", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    /**
                    * Create bill
                    * */
                    Table tableUpdate = new Table(table.getId(), table.getName(), table.getFloor(), table.getCapacity(), 1);
                    viewModel.callToCreateBill(new Bill(null, date, time, total, 0, 0, mListProduct, tableUpdate, null, Constants.staff));
                    binding.btnOrder.setBackgroundResource(R.drawable.bg_btn_order);
                    binding.imgDone.setVisibility(View.VISIBLE);
                    binding.btnOrder.setText("Order");
                }
            }
        });

        /**
        * Was updated bill successfully
        * */
        viewModel.wasUpdated.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean wasUpdated) {
                if (wasUpdated){
                    Toast.makeText(TableDetailActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(TableDetailActivity.this, "Failed to update", Toast.LENGTH_SHORT).show();
                }

                binding.btnOrder.setEnabled(true);
            }
        });

        /**
         * If bill has been created before and now show it to user
         * */
        viewModel.mBilExist.observe(this, new Observer<List<Bill>>() {
            @Override
            public void onChanged(List<Bill> bills) {
                if ((bills != null ? bills.size() : 0) != 0){
                    binding.imgDone.setVisibility(View.VISIBLE);
                    setStatusTable(bills.get(0));
                    for (Product product: bills.get(0).getProducts()){
                        viewModel.insertProduct(new Product(
                                null,
                                product.getId(), product.getName(), product.getUrlImage(), product.getPrice(), product.getTotal(), product.getAmount(),
                                product.getType(),
                                product.getIdCategory(),
                                sharePreference.getTableId()
                        ));
                    }
                }
            }
        });

        viewModel.mBillByIdLiveData.observe(this, new Observer<Bill>() {
            @Override
            public void onChanged(Bill bill) {
                Log.d("TAG", "onChanged: "+ viewModel.getListProduct());
                if (bill != null){
                    setStatusTable(bill);
                }
            }
        });

        /**
         * Live data to pay bill
         * */
        viewModel.payBillLiveData.observe(this, new Observer<List<Bill>>() {
            @Override
            public void onChanged(List<Bill> bills) {
                if (bills != null && bills.size() != 0){
                    Table tableUpdate = new Table(table.getId(), table.getName(), table.getFloor(), table.getCapacity(), 1);
                    viewModel.callToUpdateBill(bills.get(0).getId(), new Bill(bills.get(0).getId(), date, time, total, 0, 2, mListProduct,
                            tableUpdate,
                            null, Constants.staff), Constants.TYPE_PAY);

                    viewModel.callToPushNotification(
                            "cCvkqM_VCyS5V_iMt3XSlw:APA91bEStWrvanpkTGWf0FLVH90ToxShO1hTIOA7S-HMehtoZaLMtzB_CIKcok0-pU_UQLZXV45PkQOsGPfM00krzNBzc-wolTtb7B5xeuV5SxRYGbOouwpvLyZ2PWvNCg4i0Nqk_6gl",
                            "Thông báo xác nhận hóa đơn",
                            "Bàn "+ bills.get(0).getTable().getName()+ " đang chờ xác nhận thanh toán",
                            bills.get(0).getId());

                    binding.tvStatus.setText("Thu ngân đang tiến hành thanh toán");

                }
            }
        });

        viewModel.statusBillExistLiveData.observe(this, new Observer<List<Bill>>() {
            @Override
            public void onChanged(List<Bill> bills) {
                if (bills != null && bills.size() != 0){
                    setStatusTable(bills.get(0));
                }
            }
        });
    }

    private void initEvent() {
        binding.btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type == 0){
                    viewModel.callToGetBillExist(sharePreference.getTableId(), Constants.TYPE_CLICK);
                    binding.btnOrder.setEnabled(false);
                }
            }
        });
        binding.imgDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.callToGetBillExist(sharePreference.getTableId(), Constants.TYPE_PAY_BILL);
                binding.btnOrder.setEnabled(false);
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

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                String idBill = intent.getStringExtra(Constants.EXTRA_ID_BILL_TO_TABLE_DETAIL);
                viewModel.getBillById(idBill);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onStop() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        super.onStop();
    }

    private void setStatusTable(Bill bill){
        if (bill != null){
            if (bill.getStatus() == 0){
                binding.btnOrder.setBackgroundResource(R.drawable.bg_btn_order_black);
                binding.btnOrder.setText("Update");
                binding.tvStatus.setText("Đang giao cho nhà bếp xử lý");
                binding.imgDone.setVisibility(View.VISIBLE);
            }else if(bill.getStatus() == 1){
                binding.btnOrder.setBackgroundResource(R.drawable.bg_btn_order_black);
                binding.btnOrder.setText("Update");
                binding.tvStatus.setText("Đồ ăn đã hoàn thành. Bàn đang hoạt động.");
                binding.imgDone.setVisibility(View.VISIBLE);
            }else if(bill.getStatus() == 3){
                binding.tvStatus.setText("Thanh toán thành công");
                for (Product product: viewModel.getListProductByIdTable(bill.getTable().getId())){
                    viewModel.deleteProduct(product);
                }
                binding.btnOrder.setBackgroundResource(R.drawable.bg_btn_order);
                binding.btnOrder.setText("Order");
                binding.imgDone.setVisibility(View.GONE);
            }
        }
    }
}