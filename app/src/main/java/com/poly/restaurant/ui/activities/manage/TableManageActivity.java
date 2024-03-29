package com.poly.restaurant.ui.activities.manage;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.PopupMenu;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;
import com.poly.restaurant.R;
import com.poly.restaurant.data.models.Staff;
import com.poly.restaurant.data.models.Table;
import com.poly.restaurant.data.models.TableParent;
import com.poly.restaurant.databinding.ActivityTableManageBinding;
import com.poly.restaurant.ui.activities.account.AccountActivity;
import com.poly.restaurant.ui.activities.manage.adapters.IOnClickItemParent;
import com.poly.restaurant.ui.activities.manage.adapters.TableManageAdapter;
import com.poly.restaurant.ui.activities.table.TableDetailActivity;
import com.poly.restaurant.ui.base.BaseActivity;
import com.poly.restaurant.ui.bill.BillActivity;
import com.poly.restaurant.ui.history.HistoryActivity;
import com.poly.restaurant.ui.notification.NotificationActivity;
import com.poly.restaurant.utils.Constants;
import com.poly.restaurant.utils.helps.ViewModelFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TableManageActivity extends BaseActivity {
    private ActivityTableManageBinding binding;
    private TableManageViewModel viewModel;
    private TableManageAdapter adapter;
    private List<Table> mListTablesEmpty;
    private List<Table> mListTablesLive;
    private List<Table> mListTablesMerge;
    private List<TableParent> mListTableMain;
    private List<Staff> mListAdmin;
    private List<Staff> mListChef;
    private List<Staff> mListCashier;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewModelFactory factory = new ViewModelFactory(this);
        viewModel = new ViewModelProvider(this, factory).get(TableManageViewModel.class);
        binding = ActivityTableManageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Window window = getWindow();

        window.setStatusBarColor(ContextCompat.getColor(this, R.color.status_bar_color));
        binding.prgLoadTable.setVisibility(View.VISIBLE);
        binding.rvTable.setVisibility(View.GONE);

        mListTablesEmpty = new ArrayList<>();
        mListTablesLive = new ArrayList<>();
        mListTablesMerge = new ArrayList<>();
        mListTableMain = new ArrayList<>();
        mListAdmin = new ArrayList<>();
        mListChef = new ArrayList<>();
        mListCashier = new ArrayList<>();

        adapter = new TableManageAdapter(mListTableMain, this, new IOnClickItemParent() {
            @Override
            public void onClick(Table table) {
                Intent intent = new Intent(TableManageActivity.this, TableDetailActivity.class);
                intent.putExtra(Constants.EXTRA_TABLE_TO_DETAIL, table);
                intent.putParcelableArrayListExtra(Constants.EXTRA_ADMIN_TO_DETAIL, (ArrayList<? extends Parcelable>) mListAdmin);
                intent.putParcelableArrayListExtra(Constants.EXTRA_CHEF_TO_DETAIL, (ArrayList<? extends Parcelable>) mListChef);
                intent.putParcelableArrayListExtra(Constants.EXTRA_CASHIER_TO_DETAIL, (ArrayList<? extends Parcelable>) mListCashier);
                startActivity(intent);
            }
        });
        binding.tvNumFloor.setText("Tầng: " + Constants.staff.getFloor().getNumberFloor());
        binding.rvTable.setAdapter(adapter);
        binding.rvTable.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        viewModel.mListEmptyTableLiveData.observe(this, new Observer<List<Table>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(List<Table> tables) {
                if (tables != null) {
                    mListTablesEmpty = tables;
                    binding.tvNumEmptyTable.setText("Chưa sử dụng: " + tables.size());
                    mListTableMain = getListTableMain();
                    adapter.setList(mListTableMain);
                }
            }
        });

        viewModel.mListLiveTableLiveData.observe(this, new Observer<List<Table>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(List<Table> tables) {
                if (tables != null) {
                    mListTablesLive = tables;
                    binding.tvNumLiveTable.setText("Đang sử dụng: " + tables.size());
                    mListTableMain = getListTableMain();
                    adapter.setList(mListTableMain);
                }
            }
        });

        viewModel.mListMergeTableLiveData.observe(this, new Observer<List<Table>>() {
            @Override
            public void onChanged(List<Table> tables) {
                if (tables != null) {
                    mListTablesMerge = tables;
                    mListTableMain = getListTableMain();
                    adapter.setList(mListTableMain);
                }
            }
        });

        viewModel.mListChefLiveData.observe(this, new Observer<List<Staff>>() {
            @Override
            public void onChanged(List<Staff> staff) {
                Log.d("TAG", "onChanged: " + new Gson().toJson(staff));
                binding.prgLoadTable.setVisibility(View.GONE);
                mListChef = staff;
                binding.rvTable.setVisibility(View.VISIBLE);
            }
        });

        viewModel.mListCashierLiveData.observe(this, new Observer<List<Staff>>() {
            @Override
            public void onChanged(List<Staff> staff) {
                mListCashier = staff;
            }
        });

        viewModel.mListAdminLiveData.observe(this, new Observer<List<Staff>>() {
            @Override
            public void onChanged(List<Staff> staff) {
                mListAdmin = staff;
            }
        });

        /** Handle event*/
        binding.imgNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TableManageActivity.this, NotificationActivity.class);
                startActivity(intent);
            }
        });

        binding.imgMenu.setOnClickListener(view -> {
            showPopupMenu();
        });

        binding.imgNotification.setOnClickListener(view -> {
            startActivity(new Intent(this, NotificationActivity.class));
        });

        binding.imgBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TableManageActivity.this, BillActivity.class);
                startActivity(intent);
            }
        });
        LocalBroadcastManager.getInstance(this).registerReceiver((receiver),
                new IntentFilter(Constants.REQUEST_TO_ACTIVITY)
        );
        viewModel.deleteAllProduct();
    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                String idBill = intent.getStringExtra(Constants.EXTRA_ID_BILL_TO_TABLE_DETAIL);
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

    private List<TableParent> getListTableMain() {
        List<TableParent> mTableMain = new ArrayList<>();
        if (mListTablesEmpty.size() != 0) {
            mListTablesEmpty.sort(new Comparator<Table>() {
                @Override
                public int compare(Table t1, Table t2) {
                    return t1.getName().compareTo(t2.getName());
                }
            });
            mTableMain.add(new TableParent("Bàn còn trống", mListTablesEmpty));
        }
        if (mListTablesLive.size() != 0) {
            mListTablesLive.sort(new Comparator<Table>() {
                @Override
                public int compare(Table t1, Table t2) {
                    return t1.getName().compareTo(t2.getName());
                }
            });
            mTableMain.add(new TableParent("Bàn đang sử dụng", mListTablesLive));
        }
        if (mListTablesMerge.size() != 0) {
            mListTablesMerge.sort(new Comparator<Table>() {
                @Override
                public int compare(Table t1, Table t2) {
                    return t1.getName().compareTo(t2.getName());
                }
            });
            mTableMain.add(new TableParent("Bàn đã gộp", mListTablesMerge));
        }
        return mTableMain;
    }

    @Override
    protected void onResume() {
        LocalBroadcastManager.getInstance(this).registerReceiver((receiver),
                new IntentFilter(Constants.REQUEST_TO_ACTIVITY)
        );
        viewModel.callToGetTableEmpty(Constants.staff.getFloor().getNumberFloor(), Constants.TABLE_EMPTY_STATUS);
        viewModel.callToGetTableLive(Constants.staff.getFloor().getNumberFloor(), Constants.TABLE_LIVE_STATUS);
        viewModel.callToGetTableMerge(Constants.staff.getFloor().getNumberFloor(), Constants.TABLE_LIVE_MERGE);
        viewModel.callToGetAdmin();
        viewModel.callToGetChef();
        viewModel.callToGetCashier();
        super.onResume();
    }

    private void showPopupMenu() {
        PopupMenu popupMenu = new PopupMenu(TableManageActivity.this, binding.imgMenu);
        popupMenu.getMenuInflater().inflate(R.menu.menu_main, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.action_account) {
                    startActivity(new Intent(TableManageActivity.this, AccountActivity.class));
                } else if (menuItem.getItemId() == R.id.action_history) {
                    startActivity(new Intent(TableManageActivity.this, HistoryActivity.class));
                }
                return true;
            }
        });
        popupMenu.show();
    }

}