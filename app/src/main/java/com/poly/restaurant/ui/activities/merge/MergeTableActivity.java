package com.poly.restaurant.ui.activities.merge;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.poly.restaurant.data.models.Table;
import com.poly.restaurant.data.models.TableParent;
import com.poly.restaurant.databinding.ActivityMergeTableBinding;
import com.poly.restaurant.ui.activities.manage.adapters.IOnClickItemParent;
import com.poly.restaurant.ui.activities.manage.adapters.TableManageAdapter;
import com.poly.restaurant.ui.activities.merge.adapter.OnListenerMerge;
import com.poly.restaurant.ui.activities.merge.adapter.TableManageMergeAdapter;
import com.poly.restaurant.ui.base.BaseActivity;
import com.poly.restaurant.utils.Constants;
import com.poly.restaurant.utils.helps.ViewModelFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MergeTableActivity extends BaseActivity {
    private ActivityMergeTableBinding binding;
    private MergeTableViewModel viewModel;
    private List<Table> listLiveTable;
    private List<Table> listEmptyTable;
    private List<TableParent> mListTableMain;
    private TableManageMergeAdapter adapter;

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
        initRec();
        initViewModel();
        LocalBroadcastManager.getInstance(this).registerReceiver((receiver),
                new IntentFilter(Constants.REQUEST_TO_ACTIVITY)
        );
    }

    private void initRec() {
        adapter = new TableManageMergeAdapter(mListTableMain, this, new OnListenerMerge() {
            @Override
            public void onClick(Table table) {

            }
        });
        binding.rvMerge.setAdapter(adapter);
    }

    private void initViewModel() {
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
