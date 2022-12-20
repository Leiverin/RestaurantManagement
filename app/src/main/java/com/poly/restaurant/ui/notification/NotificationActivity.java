package com.poly.restaurant.ui.notification;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;
import com.poly.restaurant.data.models.Bill;
import com.poly.restaurant.data.models.Notification;
import com.poly.restaurant.databinding.ActivityNotificationBinding;
import com.poly.restaurant.ui.base.BaseActivity;
import com.poly.restaurant.ui.notification.adapter.NotificationAdapter;
import com.poly.restaurant.ui.notification.adapter.OnListenerNotification;
import com.poly.restaurant.utils.Constants;

import java.util.ArrayList;
import java.util.List;


public class NotificationActivity extends BaseActivity {
    private ActivityNotificationBinding binding;
    private List<Notification> list;
    private NotificationAdapter adapter;
    private NotificationViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(NotificationViewModel.class);
        binding = ActivityNotificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.prgLoadBill.setVisibility(View.VISIBLE);
        binding.rvNotification.setVisibility(View.GONE);
        list = new ArrayList<>();
        initRec();
        initViewModel();
//        initListener();
    }

    private void initRec() {
        adapter = new NotificationAdapter(this, list, new OnListenerNotification() {
            @Override
            public void onClickShowDetailNotification(Notification notification, CardView cardView) {
                Constants.dialogShowDetailNoti(notification.getIdBill(),NotificationActivity.this,cardView);
            }
        });
        binding.rvNotification.setAdapter(adapter);
    }

    private void initViewModel() {
        viewModel.mListNotiLiveData.observe(this, new Observer<List<Notification>>() {
            @Override
            public void onChanged(List<Notification> notifications) {
                if (notifications.isEmpty()) {
                    binding.empty.setVisibility(View.VISIBLE);
                    binding.prgLoadBill.setVisibility(View.GONE);
                } else {
                    list = notifications;
                    adapter.setList(list);
                    binding.rvNotification.setVisibility(View.VISIBLE);
                    binding.prgLoadBill.setVisibility(View.GONE);
                    binding.empty.setVisibility(View.GONE);
                }

            }
        });
        viewModel.getNotification(Constants.staff.getId());
    }

    private void initListener() {
        binding.svNoti.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
                    filterNoti(newText);
                }
                return true;
            }
        });
    }

    private void filterNoti(String text) {
        List<Notification> notificationList = new ArrayList<>();
        for (Notification notification : list) {
            if (notification.getTitle().contains(text) || notification.getContent().contains(text)) {
                notificationList.add(notification);
            }
            if (notificationList.isEmpty()) {
                binding.empty.setVisibility(View.VISIBLE);
            }
        }
        adapter.setList(notificationList);
    }

//    private final BroadcastReceiver receiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            try {
//                viewModel.getNotification(Constants.staff.getId());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    };
//
//    @Override
//    protected void onStop() {
//        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
//        super.onStop();
//    }
//
//    @Override
//    protected void onResume() {
//        LocalBroadcastManager.getInstance(this).registerReceiver((receiver),
//                new IntentFilter(Constants.REQUEST_TO_ACTIVITY)
//        );
//        viewModel.getNotification(Constants.staff.getId());
//        super.onResume();
//    }
}