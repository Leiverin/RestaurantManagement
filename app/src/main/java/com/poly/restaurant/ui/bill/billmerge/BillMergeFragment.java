package com.poly.restaurant.ui.bill.billmerge;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.poly.restaurant.data.models.Bill;
import com.poly.restaurant.databinding.FragmentBillMergeBinding;
import com.poly.restaurant.ui.base.BaseFragment;
import com.poly.restaurant.ui.bill.adapter.BillAdapter;
import com.poly.restaurant.ui.bill.adapter.OnListener;
import com.poly.restaurant.utils.Constants;
import com.poly.restaurant.utils.helps.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class BillMergeFragment extends BaseFragment {
    private FragmentBillMergeBinding binding;
    private BillAdapter adapter;
    private List<Bill> list;
    private BillMergeViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewModelFactory viewModelFactory = new ViewModelFactory(getContext());
        viewModel = new ViewModelProvider(this, viewModelFactory).get(BillMergeViewModel.class);
        binding = FragmentBillMergeBinding.inflate(getLayoutInflater());
        binding.prgLoadBill.setVisibility(View.VISIBLE);
        binding.rvBill.setVisibility(View.GONE);
        list = new ArrayList<>();
        initRec();
        initViewModel();
        LocalBroadcastManager.getInstance(getContext()).registerReceiver((receiver),
                new IntentFilter(Constants.REQUEST_TO_ACTIVITY)
        );
        return binding.getRoot();
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

    private void initRec() {
        adapter = new BillAdapter(getContext(), list, new OnListener() {
            @Override
            public void onClickBill(Bill bill, CardView cardView) {
                Constants.dialogShowDetailBill(bill, requireContext(), cardView);
            }

            @Override
            public void onClickFeedback(Bill bill) {

            }
        });
        binding.rvBill.setAdapter(adapter);
    }

    private void initViewModel() {
        viewModel.mListBillLiveData.observe(getViewLifecycleOwner(), new Observer<List<Bill>>() {
            @Override
            public void onChanged(List<Bill> bills) {
                if (bills.isEmpty()) {
                    binding.empty.setVisibility(View.VISIBLE);
                    binding.prgLoadBill.setVisibility(View.GONE);
                } else {
                    for (Bill bill : bills) {
                        if (bill.getStatus() == 4) {
                            list = bills;
                            adapter.setList(list);
                            binding.rvBill.setVisibility(View.VISIBLE);
                            binding.prgLoadBill.setVisibility(View.GONE);
                            binding.empty.setVisibility(View.GONE);
                        }
                    }

                }

            }
        });
    }

    @Override
    public void onStop() {
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(receiver);
        super.onStop();
    }

    @Override
    public void onResume() {
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver((receiver),
                new IntentFilter(Constants.REQUEST_TO_ACTIVITY)
        );
        viewModel.getBill(Constants.staff.getId(), Constants.staff.getFloor().getNumberFloor());
        super.onResume();
    }
}