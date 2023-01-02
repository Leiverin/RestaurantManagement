package com.poly.restaurant.ui.activities.table;

import androidx.annotation.NonNull;
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
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.poly.restaurant.R;
import com.poly.restaurant.data.models.Bill;
import com.poly.restaurant.data.models.Notification;
import com.poly.restaurant.data.models.Product;
import com.poly.restaurant.data.models.Staff;
import com.poly.restaurant.data.models.Table;
import com.poly.restaurant.databinding.ActivityTableDetailBinding;
import com.poly.restaurant.preference.AppSharePreference;
import com.poly.restaurant.ui.activities.account.AccountActivity;
import com.poly.restaurant.ui.activities.manage.TableManageActivity;
import com.poly.restaurant.ui.dialog.DialogAnnounce;
import com.poly.restaurant.ui.activities.product.FoodActivity;
import com.poly.restaurant.ui.activities.table.adapter.IOnItemProductTableListener;
import com.poly.restaurant.ui.activities.table.adapter.ProductTableAdapter;
import com.poly.restaurant.ui.base.BaseActivity;
import com.poly.restaurant.ui.dialog.DialogRequest;
import com.poly.restaurant.ui.dialog.interfaces.IOnEventDialogListener;
import com.poly.restaurant.ui.history.HistoryActivity;
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
    private List<Staff> mListAdmin;
    private List<Staff> mListChef;
    private List<Staff> mListCashier;
    private ProductTableAdapter adapter;
    private AppSharePreference sharePreference;
    private Table table;
    private boolean isShowing = false;
    private final int type = 0;
    private double total = 0;
    private int count = 0;
    private int countCreate = 0;
    private final String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Calendar.getInstance().getTime());
    private final String time = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Calendar.getInstance().getTime());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewModelFactory factory = new ViewModelFactory(this);
        viewModel = new ViewModelProvider(this, factory).get(TableDetailViewModel.class);
        binding = ActivityTableDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mListAdmin = getIntent().getParcelableArrayListExtra(Constants.EXTRA_ADMIN_TO_DETAIL);
        mListChef = getIntent().getParcelableArrayListExtra(Constants.EXTRA_CHEF_TO_DETAIL);
        mListCashier = getIntent().getParcelableArrayListExtra(Constants.EXTRA_CASHIER_TO_DETAIL);
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
                int quantity = Integer.parseInt(tvQuantity.getText().toString().trim());
                if (quantity > 0){
                    quantity--;
                    Constants.handleDecrease(tvQuantity, Constants.TYPE_IN_TABLE);
                    handleDecreaseProduct(product, quantity);
                    tvQuantity.setText(quantity + "");
                    adapter.getMListProduct().get(position).setAmount(quantity);
                }
            }

            @Override
            public void onClickIncrease(@NonNull Product product, @NonNull TextView tvQuantity, int position) {
                int quantity = Integer.parseInt(tvQuantity.getText().toString().trim());
                if (quantity < product.getTotal()){
                    quantity++;
                    handleAddProduct(product, quantity);
                    tvQuantity.setText(quantity + "");
                    adapter.getMListProduct().get(position).setAmount(quantity);
                }else{
                    Toast.makeText(TableDetailActivity.this, "Không được vượt quá sản lượng", Toast.LENGTH_SHORT).show();
                }
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
                    binding.tvTotalDishes.setText("Tổng món: "+products.size()+" món");
                    binding.tvTotalPrice.setText("Tổng giá: "+(int) (total*23000)+"vnđ");
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
                    String title = "Thông báo";
                    String content = "Bill bàn "+bill.getTable().getName()+" đã được tạo. Hành động thôi nào :))";
                    for (Staff s: mListChef){
                        countCreate++;
                        viewModel.callToPushNotification(
                                s.getTokenFCM(),
                                title,
                                content,
                                bill.getId(),
                                Constants.staff.getId()
                        );
                        viewModel.createNotification(new Notification(
                                null, title, content, date, time, Constants.staff, s, bill.getId()
                        ));
                    };
                    for (Staff s: mListAdmin){
                        viewModel.callToPushNotification(
                                s.getTokenFCM(),
                                title,
                                content,
                                bill.getId(),
                                Constants.staff.getId()
                        );
                        viewModel.createNotification(new Notification(
                                null, title, content, date, time, Constants.staff, s, bill.getId()
                        ));
                    }
                    for (Staff s: mListCashier){
                        viewModel.callToPushNotification(
                                s.getTokenFCM(),
                                title,
                                content,
                                bill.getId(),
                                Constants.staff.getId()
                        );
                        viewModel.createNotification(new Notification(
                                null, title, content, date, time, Constants.staff, s, bill.getId()
                        ));
                    }
                    Table tableUpdate = new Table(table.getId(), table.getName(), table.getFloor(), table.getCapacity(), 1);
                    viewModel.updateTable(table.getId(), tableUpdate);
                    binding.btnOrder.setBackgroundResource(R.drawable.bg_btn_order_black);
                    binding.tvStatus.setText("Đang giao cho nhà bếp xử lý");
                    DialogAnnounce.getInstance("Tạo hóa đơn thành công").show(getSupportFragmentManager(), new DialogAnnounce().getTag());
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
                        String title = "Thông báo bổ sung món";
                        String content = "Bill bàn "+bill.get(0).getTable().getName()+" vừa bổ sung thêm món";
                        for (Staff s: mListChef){
                            count++;
                            viewModel.callToPushNotification(
                                    s.getTokenFCM(),
                                    title,
                                    content,
                                    bill.get(0).getId(),
                                    Constants.staff.getId()
                            );
                            viewModel.createNotification(new Notification(
                                    null, title, content, date, time, Constants.staff, s, bill.get(0).getId()
                            ));
                        }
                        if (count == mListChef.size()){
                            DialogAnnounce.getInstance("Đã gửi thực đơn cho nhà bếp").show(getSupportFragmentManager(), new DialogAnnounce().getTag());
                            count = 0;
                        }
                        binding.tvStatus.setText("Đang giao cho nhà bếp xử lý");
                    }else{
                        Toast.makeText(TableDetailActivity.this, "Chưa có sản phẩm nào", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    /**
                    * Create bill
                    * */
                    Table tableUpdate = new Table(table.getId(), table.getName(), table.getFloor(), table.getCapacity(), 1);
                    viewModel.callToCreateBill(new Bill(null, date, time, total, 0, 0, mListProduct, tableUpdate, null, Constants.staff));
                    binding.btnOrder.setBackgroundResource(R.drawable.bg_btn_order);
                    binding.btnOrder.setText("Lên đơn");
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
//                    Toast.makeText(TableDetailActivity.this, "Updated", Toast.LENGTH_SHORT).show();
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
                                product.getId(), product.getName(), product.getUrlImage(), product.getPrice(),
                                product.getDescription(),
                                product.getTotal(), product.getAmount(),
                                product.getType(),
                                product.getIdCategory(),
                                sharePreference.getTableId(),
                                product.getStatus()
                        ));
                    }
                }
            }
        });

        viewModel.mBillByIdLiveData.observe(this, new Observer<Bill>() {
            @Override
            public void onChanged(Bill bill) {
                if (bill != null){
                    setStatusProduct(bill);
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
                binding.imgDone.setEnabled(true);
                if (bills != null && bills.size() != 0){
                    Table tableUpdate = new Table(table.getId(), table.getName(), table.getFloor(), table.getCapacity(), 1);
                    viewModel.callToUpdateBill(bills.get(0).getId(), new Bill(bills.get(0).getId(), date, time, total, 0, 2, mListProduct,
                            tableUpdate,
                            null, Constants.staff), Constants.TYPE_PAY);
                    String title = "Thông báo xác nhận hóa đơn";
                    String content = "Bàn "+ bills.get(0).getTable().getName()+ " đang chờ xác nhận thanh toán";
                    for (Staff s: mListAdmin){
                        viewModel.callToPushNotification(
                                s.getTokenFCM(),
                                title,
                                content,
                                bills.get(0).getId(),
                                Constants.staff.getId()
                        );
                        viewModel.createNotification(new Notification(
                                null, title, content, date, time, Constants.staff, s, bills.get(0).getId()
                        ));
                    }
                    for (Staff s: mListCashier){
                        viewModel.callToPushNotification(
                                s.getTokenFCM(),
                                title,
                                content,
                                bills.get(0).getId(),
                                Constants.staff.getId()
                        );
                        viewModel.createNotification(new Notification(
                                null, title, content, date, time, Constants.staff, s, bills.get(0).getId()
                        ));
                    }
                    DialogAnnounce.getInstance("Đã gửi xác nhận cho quản lý").show(getSupportFragmentManager(), new DialogAnnounce().getTag());
                    binding.tvStatus.setText("Đang chờ tiến hành thanh toán");
                }
            }
        });

        viewModel.statusBillExistLiveData.observe(this, new Observer<List<Bill>>() {
            @Override
            public void onChanged(List<Bill> bills) {
                if (bills != null && bills.size() != 0){
                    setStatusTable(bills.get(0));
                }else{
                    for (Product product: viewModel.getListProductByIdTable(sharePreference.getTableId())){
                        viewModel.deleteProduct(product);
                    }
                }
            }
        });
    }

    private void setStatusProduct(Bill bill) {
        mListProduct = bill.getProducts();
        for (Product product: bill.getProducts()){
            viewModel.updateStatusProductInBill(product.getStatus(), product.getId(), product.getIdTable());
        }
        adapter.setList(mListProduct);
    }

    private void initEvent() {
        binding.btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.callToGetBillExist(sharePreference.getTableId(), Constants.TYPE_CLICK);
                binding.btnOrder.setEnabled(false);
            }
        });
        binding.imgDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DialogRequest(new IOnEventDialogListener() {
                    @Override
                    public void onClickPositive() {
                        viewModel.callToGetBillExist(sharePreference.getTableId(), Constants.TYPE_PAY_BILL);
                        binding.imgDone.setEnabled(false);
                    }
                }).show(getSupportFragmentManager(), new DialogAnnounce().getTag());
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
        binding.imgMenuTableDetail.setOnClickListener(view -> {
            showPopupMenu();
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
                    product.getId(), product.getName(), product.getUrlImage(), product.getPrice(),
                    product.getDescription(),
                    product.getTotal(), quantity,
                    product.getType(),
                    product.getIdCategory(),
                    sharePreference.getTableId(),
                    product.getStatus()
            ));
        }
    }

    private void handleAddProduct(Product product, int quantity) {
        if (viewModel.getProductById(product.getId(), sharePreference.getTableId()) == null){
            viewModel.insertProduct(new Product(
                    null,
                    product.getId(), product.getName(), product.getUrlImage(), product.getPrice(),
                    product.getDescription(),
                    product.getTotal(), quantity,
                    product.getType(),
                    product.getIdCategory(),
                    sharePreference.getTableId(),
                    product.getStatus()
            ));
        }else{
            viewModel.updateProduct(new Product(
                    product.getIdProduct(),
                    product.getId(), product.getName(), product.getUrlImage(), product.getPrice(),
                    product.getDescription(),
                    product.getTotal(), quantity,
                    product.getType(),
                    product.getIdCategory(),
                    sharePreference.getTableId(),
                    product.getStatus()
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
                binding.btnOrder.setText("Cập nhật");
                binding.tvStatus.setText("Đang giao cho nhà bếp xử lý");
                binding.imgDone.setVisibility(View.GONE);
            }else if(bill.getStatus() == 1){
                binding.btnOrder.setBackgroundResource(R.drawable.bg_btn_order_black);
                binding.btnOrder.setText("Cập nhật");
                binding.tvStatus.setText("Đồ ăn đã hoàn thành. Bàn đang hoạt động.");
                binding.imgDone.setVisibility(View.VISIBLE);
            }else if (bill.getStatus() == 2){
                binding.btnOrder.setBackgroundResource(R.drawable.bg_btn_order_black);
                binding.btnOrder.setText("Cập nhật");
                binding.btnOrder.setEnabled(false);
                binding.tvStatus.setText("Đang chờ tiến hành thanh toán");
                binding.imgDone.setVisibility(View.VISIBLE);
            }else if(bill.getStatus() == 3){
                binding.tvStatus.setText("Thanh toán thành công");
                for (Product product: viewModel.getListProductByIdTable(bill.getTable().getId())){
                    viewModel.deleteProduct(product);
                }
                DialogAnnounce.getInstance("Hóa đơn đã được thanh toán!!").show(getSupportFragmentManager(), new DialogAnnounce().getTag());
                binding.btnOrder.setBackgroundResource(R.drawable.bg_btn_order);
                binding.btnOrder.setText("Lên đơn");
                binding.imgDone.setVisibility(View.GONE);
            }
        }
    }
    private void showPopupMenu() {
        PopupMenu popupMenu = new PopupMenu(TableDetailActivity.this, binding.imgMenuTableDetail);
        popupMenu.getMenuInflater().inflate(R.menu.menu_table_detail, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.action_add_food) {
                    startActivity(new Intent(TableDetailActivity.this, FoodActivity.class));
                } else if (menuItem.getItemId() == R.id.action_merge) {
//                    startActivity(new Intent(TableDetailActivity.this, HistoryActivity.class));
                }
                return true;
            }
        });
        popupMenu.show();
    }
}