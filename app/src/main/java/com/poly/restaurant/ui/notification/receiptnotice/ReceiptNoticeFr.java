package com.poly.restaurant.ui.notification.receiptnotice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.poly.restaurant.R;
import com.poly.restaurant.data.models.Notification;
import com.poly.restaurant.databinding.FragmentReceiptNoticeBinding;
import com.poly.restaurant.ui.base.BaseFragment;
import com.poly.restaurant.ui.notification.NotificationActivity;
import com.poly.restaurant.ui.notification.adapter.NotificationAdapter;
import com.poly.restaurant.ui.notification.adapter.OnListenerNotification;
import com.poly.restaurant.utils.Constants;
import com.poly.restaurant.utils.helps.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class ReceiptNoticeFr extends BaseFragment {
    private FragmentReceiptNoticeBinding binding;
    private ReceiptNoticeViewModel viewModel;
    private List<Notification> list;
    private NotificationAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewModelFactory factory = new ViewModelFactory(getContext());
        viewModel = new ViewModelProvider(this, factory).get(ReceiptNoticeViewModel.class);
        binding = FragmentReceiptNoticeBinding.inflate(getLayoutInflater());
        binding.prgLoadBill.setVisibility(View.VISIBLE);
        binding.rvNotificationReceider.setVisibility(View.GONE);
        list = new ArrayList<>();
        initRec();
        initViewModel();
        initListener();
        LocalBroadcastManager.getInstance(getContext()).registerReceiver((receiver),
                new IntentFilter(Constants.REQUEST_TO_ACTIVITY)
        );
        return binding.getRoot();
    }

    private void initRec() {
        adapter = new NotificationAdapter(getContext(), list, new OnListenerNotification() {
            @Override
            public void onClickShowDetailNotification(Notification notification, CardView cardView) {
                Constants.dialogShowDetailNoti(notification.getIdBill(), getContext(), cardView);
            }
        });
        binding.rvNotificationReceider.setAdapter(adapter);
    }

    private void initViewModel() {
        viewModel.mListNotiLiveData.observe(getViewLifecycleOwner(), new Observer<List<Notification>>() {
            @Override
            public void onChanged(List<Notification> notifications) {
                if (notifications.isEmpty()) {
                    binding.empty.setVisibility(View.VISIBLE);
                    binding.prgLoadBill.setVisibility(View.GONE);
                } else {
                    list = notifications;
                    adapter.setList(list);
                    binding.rvNotificationReceider.setVisibility(View.VISIBLE);
                    binding.prgLoadBill.setVisibility(View.GONE);
                    binding.empty.setVisibility(View.GONE);
                }
            }
        });
    }

    private void initListener() {
        SearchView searchView = ((NotificationActivity) getActivity()).findViewById(R.id.sv_noti);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String search = newText.trim();
                if (search.isEmpty()) {
                    initViewModel();
                } else {
                    Constants.filterNoti(newText, list, adapter);
                }
                return true;
            }
        });
    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                viewModel.getNotificationReceiver(Constants.staff.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public void onStop() {
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(receiver);
        super.onStop();
    }

    @Override
    public void onResume() {
        LocalBroadcastManager.getInstance(getContext()).registerReceiver((receiver),
                new IntentFilter(Constants.REQUEST_TO_ACTIVITY)
        );
        viewModel.getNotificationReceiver(Constants.staff.getId());
        super.onResume();
    }
}