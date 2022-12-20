package com.poly.restaurant.ui.bill;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

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
    private BillAdapter adapter;
    private List<Bill> list;
    private BillViewModel viewModel;

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
            public void onClickBill(Bill bill) {
                Constants.dialogShowDetailBill(bill, BillActivity.this);
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
        viewModel.getBill(Constants.staff.getId(), Constants.staff.getFloor().getNumberFloor());
    }


//    private void showDialogComplete(Bill bill) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        DialogAlertCompleteBinding dialogAlertCompleteBinding = DialogAlertCompleteBinding.inflate(LayoutInflater.from(this));
//        builder.setView(dialogAlertCompleteBinding.getRoot());
//        AlertDialog dialog = builder.create();
//        Resources res = getResources();
//        String text = String.format(res.getString(R.string.text_alert), bill.getTable().getName());
//        dialogAlertCompleteBinding.textAlert.setText(text);
//        dialogAlertCompleteBinding.btnNo.setOnClickListener(view -> {
//            dialog.dismiss();
//        });
//        dialogAlertCompleteBinding.btnYes.setOnClickListener(view -> {
//            Constants.setOnStatus(bill);
//            list.remove(bill);
//            adapter.setList(list);
//            adapter.notifyDataSetChanged();
//            Table table = new Table(bill.getTable().getId(), bill.getTable().getName(), bill.getTable().getFloor(), bill.getTable().getCapacity(), 0);
//            viewModel.updateTable(table.getId(), table);
//            dialog.dismiss();
//        });
//
//        dialog.show();
//        dialog.setCancelable(false);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
//        dialog.getWindow().setGravity(Gravity.CENTER);
//    }

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
    protected void onStop() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        super.onStop();
    }

    @Override
    protected void onResume() {
        LocalBroadcastManager.getInstance(this).registerReceiver((receiver),
                new IntentFilter(Constants.REQUEST_TO_ACTIVITY)
        );
        viewModel.getBill(Constants.staff.getId(), Constants.staff.getFloor().getNumberFloor());
        super.onResume();
    }
}
