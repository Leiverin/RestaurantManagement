package com.poly.restaurant.ui.bill;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.poly.restaurant.data.models.Bill;
import com.poly.restaurant.databinding.ActivityBillBinding;
import com.poly.restaurant.ui.base.BaseActivity;
import com.poly.restaurant.ui.bill.adapter.BillAdapter;
import com.poly.restaurant.ui.bill.adapter.OnListener;
import com.poly.restaurant.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class BillActivity extends BaseActivity {
    private ActivityBillBinding binding;
    private BillViewModel viewModel;
    private List<Bill> list;
    private BillAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(BillViewModel.class);
        binding = ActivityBillBinding.inflate(getLayoutInflater());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(binding.getRoot());
        binding.prgLoadBill.setVisibility(View.VISIBLE);
        binding.rvBill.setVisibility(View.GONE);
        list = new ArrayList<>();
        initRec();
        initViewModel();
        LocalBroadcastManager.getInstance(this).registerReceiver((receiver),
                new IntentFilter(Constants.REQUEST_TO_ACTIVITY)
        );
    }

    private void initRec() {
        adapter = new BillAdapter(this, list, new OnListener() {
            @Override
            public void onClickBill(Bill bill, CardView cardView) {
                Constants.dialogShowDetailBill(bill, BillActivity.this, cardView);
            }

            @Override
            public void onClickFeedback(Bill bill) {

            }
        });
        binding.rvBill.setAdapter(adapter);

    }

    private void initViewModel() {
        viewModel.mListBillLiveData.observe(this, new Observer<List<Bill>>() {
            @Override
            public void onChanged(List<Bill> bills) {
                if (bills.isEmpty()) {
                    binding.empty.setVisibility(View.VISIBLE);
                    binding.prgLoadBill.setVisibility(View.GONE);
                } else {
                    list = bills;
                    adapter.setList(list);
                    binding.rvBill.setVisibility(View.VISIBLE);
                    binding.prgLoadBill.setVisibility(View.GONE);
                    binding.empty.setVisibility(View.GONE);
                }

            }
        });
    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                viewModel.getBill(Constants.staff.getId(), Constants.staff.getFloor().getNumberFloor());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public void onStop() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        super.onStop();
    }

    @Override
    public void onResume() {
        LocalBroadcastManager.getInstance(this).registerReceiver((receiver),
                new IntentFilter(Constants.REQUEST_TO_ACTIVITY)
        );
        viewModel.getBill(Constants.staff.getId(), Constants.staff.getFloor().getNumberFloor());
        super.onResume();
    }

}
