package com.poly.restaurant.ui.activities.merge;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.poly.restaurant.R;
import com.poly.restaurant.data.models.Bill;
import com.poly.restaurant.data.models.Product;
import com.poly.restaurant.data.models.Table;
import com.poly.restaurant.data.models.TableParent;
import com.poly.restaurant.databinding.ActivityMergeTableBinding;
import com.poly.restaurant.ui.activities.merge.adapter.OnListenerMerge;
import com.poly.restaurant.ui.activities.merge.adapter.TableManageMergeAdapter;
import com.poly.restaurant.ui.base.BaseActivity;
import com.poly.restaurant.utils.Constants;
import com.poly.restaurant.utils.helps.ViewModelFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class MergeTableActivity extends BaseActivity {
    private ActivityMergeTableBinding binding;
    private MergeTableViewModel viewModel;
    private List<Table> listLiveTable;
    private List<Table> listEmptyTable;
    private List<Table> tableList;
    private List<TableParent> mListTableMain;
    private List<Product> mListProduct;
    private TableManageMergeAdapter adapter;
    private boolean isShowing = false;
    private final String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Calendar.getInstance().getTime());
    private final String time = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Calendar.getInstance().getTime());
    private double total = 0;
    private Table tableIntent;
    private Bill mBill;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMergeTableBinding.inflate(getLayoutInflater());
        ViewModelFactory factory = new ViewModelFactory(this);
        viewModel = new ViewModelProvider(this, factory).get(MergeTableViewModel.class);
        setContentView(binding.getRoot());
        binding.prgLoadBill.setVisibility(View.VISIBLE);
        binding.rvMerge.setVisibility(View.GONE);
        listLiveTable = new ArrayList<>();
        listEmptyTable = new ArrayList<>();
        mListTableMain = new ArrayList<>();
        mListProduct = new ArrayList<>();
        tableList = new ArrayList<>();
        tableIntent = getIntent().getParcelableExtra(Constants.EXTRA_TABLE_TO_MERGE);
        mBill = getIntent().getParcelableExtra(Constants.EXTRA_BILL_TO_MERGE);
        initRec();
        initViewModel();
        initActions();
        eventScrollRecycleView();
        LocalBroadcastManager.getInstance(this).registerReceiver((receiver),
                new IntentFilter(Constants.REQUEST_TO_ACTIVITY)
        );
    }

    private void initActions() {
        binding.imgMerge.setOnClickListener(view -> {
            viewModel.checkBillByIdTable(tableIntent.getId());
//            createBillMerge();
        });
    }

    private void eventScrollRecycleView() {
        binding.rvMerge.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == 0) {
                    visibleBottomSheet();
                } else {
                    hideBottomSheet();
                }
            }
        });
    }

    private void visibleBottomSheet() {
        binding.viewBottomSheet.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_bottom_to_top));
        binding.viewBottomSheet.setVisibility(View.VISIBLE);
    }

    private void hideBottomSheet() {
        binding.viewBottomSheet.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_top_to_bottom));
        binding.viewBottomSheet.setVisibility(View.GONE);
    }

    private void initRec() {
        adapter = new TableManageMergeAdapter(mListTableMain, this, new OnListenerMerge() {
            @Override
            public void onAddTable(Table table) {
                handleAddTable(table);
                viewModel.checkBillAlreadyExists(table.getId());
            }

            @Override
            public void onDeleteTable(Table table) {
                viewModel.deleteTable(table.getId());
            }
        });
        binding.rvMerge.setAdapter(adapter);
    }

    private void initViewModel() {
        viewModel.getTableLiveData().observe(this, new Observer<List<Table>>() {
            @Override
            public void onChanged(List<Table> tables) {
                if (tables != null && tables.size() != 0) {
                    if (!isShowing) {
                        visibleBottomSheet();
                    }
                    isShowing = true;
                    StringBuilder names = new StringBuilder();
                    for (Table table : tables) {
                        names.append(table.getName()).append(", ");
                    }
                    tables.add(tableIntent);
                    tableList = tables;
                    binding.tvNameTable.setText(names);
                } else {
                    binding.tvNameTable.setText("Bàn ...");
                    hideBottomSheet();
                    isShowing = false;
                }
                binding.tvTotalTable.setText("Số lượng :" + tables.size() + " bàn");
                showOrHideView(tableList);
            }
        });
        // lấy product từ các bàn đã chọn
        viewModel.getProductByIdTable.observe(MergeTableActivity.this, new Observer<List<Bill>>() {
            @Override
            public void onChanged(List<Bill> bills) {
                if (bills != null && bills.size() != 0) {
                    for (Bill bill : bills) {
                        mListProduct.addAll(bill.getProducts());
                        for (Product product : bill.getProducts()) {
                            viewModel.updateProductMerge(tableIntent.getId(), product.getId());
                        }
                    }
                }
            }
        });

        viewModel.getListProductByIdTableLive(tableIntent.getId()).observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                if (products != null && products.size() != 0) {
                    if (!isShowing) {
                        visibleBottomSheet();
                        isShowing = true;
                    }
                    total = 0;
                    for (Product product : products) {
                        total += Double.parseDouble((product.getPrice() * product.getAmount()) + "");
                    }
                    mListProduct = products;
                } else {
                    isShowing = false;
                    hideBottomSheet();
                }
            }
        });
        viewModel.mListEmptyTableLiveData.observe(this, new Observer<List<Table>>() {
            @Override
            public void onChanged(List<Table> tables) {
                if (tables != null) {
                    listEmptyTable = tables;
                    mListTableMain = getListTableMain();
                    binding.rvMerge.setVisibility(View.VISIBLE);
                    binding.prgLoadBill.setVisibility(View.GONE);
                    adapter.setList(mListTableMain);
                }
            }
        });
        viewModel.mListLiveTableLiveData.observe(this, new Observer<List<Table>>() {
            @Override
            public void onChanged(List<Table> tables) {
                if (tables != null) {
                    listLiveTable = tables;
                    mListTableMain = getListTableMain();
                    binding.rvMerge.setVisibility(View.VISIBLE);
                    binding.prgLoadBill.setVisibility(View.GONE);
                    adapter.setList(mListTableMain);
                }
            }
        });

        viewModel.getBillByIdTable.observe(MergeTableActivity.this, new Observer<List<Bill>>() {
            @Override
            public void onChanged(List<Bill> bills) {
                for (Table table : tableList) {
                    viewModel.deleteTable(table.getId());
                    if (!Objects.equals(table.getId(), tableIntent.getId())) {
                        Table tableMerge = new Table(table.getId(), table.getName(), Constants.staff.getFloor().getNumberFloor(), table.getCapacity(), 2, tableIntent.getName());
                        viewModel.updateTable(table.getId(), tableMerge);
                    } else {
                        Table tableUpdate = new Table(tableIntent.getId(), tableIntent.getName(), Constants.staff.getFloor().getNumberFloor(), tableIntent.getCapacity(), tableIntent.getStatus());
                        viewModel.updateTable(tableIntent.getId(), tableUpdate);
                    }
                }
                if (bills != null && bills.size() == 0) {
                    Bill billUpdate = new Bill(bills.get(0).getId(), date, time, total, 0, 4, mListProduct, tableIntent, tableList, null, Constants.staff, null);
                    viewModel.callToUpdateBill(bills.get(0).getId(), billUpdate, Constants.TYPE_UPDATE);
                } else {
                    viewModel.callToCreateBill(new Bill(null, date, time, total, 0, 4, mListProduct, tableIntent, tableList, null, Constants.staff, null));
                }
                finish();
            }
        });

        viewModel.wasBillCreated.observe(this, new Observer<Bill>() {
            @Override
            public void onChanged(Bill bill) {
                viewModel.deleteBill(mBill.getId());
            }
        });

    }

    private void showOrHideView(List<Table> listTable) {
        if (listTable == null || listTable.size() == 0) {
            binding.viewBottomSheet.setVisibility(View.GONE);
        } else {
            binding.viewBottomSheet.setVisibility(View.VISIBLE);
        }
    }

    private List<TableParent> getListTableMain() {
        List<TableParent> mTableMain = new ArrayList<>();
        if (listEmptyTable.size() != 0) {
            listEmptyTable.sort(new Comparator<Table>() {
                @Override
                public int compare(Table t1, Table t2) {
                    return t1.getName().compareTo(t2.getName());
                }
            });
            mTableMain.add(new TableParent("Bàn còn trống", listEmptyTable));
        }
        if (listLiveTable.size() != 0) {
            listLiveTable.sort(new Comparator<Table>() {
                @Override
                public int compare(Table t1, Table t2) {
                    return t1.getName().compareTo(t2.getName());
                }
            });
            mTableMain.add(new TableParent("Bàn đang sử dụng", listLiveTable));
        }
        return mTableMain;
    }

    private void handleAddTable(Table table) {
        viewModel.insertTable(new Table(null,
                table.getId(),
                table.getName(),
                table.getFloor(),
                table.getCapacity(),
                2,
                tableIntent.getName()
        ));
    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                viewModel.callToGetTableEmpty(Constants.staff.getFloor().getNumberFloor(), Constants.TABLE_EMPTY_STATUS);
                viewModel.callToGetTableLive(Constants.staff.getFloor().getNumberFloor(), Constants.TABLE_LIVE_STATUS);
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

    @Override
    protected void onResume() {
        LocalBroadcastManager.getInstance(this).registerReceiver((receiver),
                new IntentFilter(Constants.REQUEST_TO_ACTIVITY)
        );
        viewModel.callToGetTableEmpty(Constants.staff.getFloor().getNumberFloor(), Constants.TABLE_EMPTY_STATUS);
        viewModel.callToGetTableLive(Constants.staff.getFloor().getNumberFloor(), Constants.TABLE_LIVE_STATUS);
        super.onResume();
    }

}
