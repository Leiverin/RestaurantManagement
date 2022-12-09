package com.poly.restaurant.ui.notification;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.poly.restaurant.R;
import com.poly.restaurant.data.models.Notification;
import com.poly.restaurant.databinding.ActivityNotificationBinding;
import com.poly.restaurant.ui.activities.product.FoodActivity;
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
//        binding.prgLoadBill.setVisibility(View.VISIBLE);
//        binding.rvNotification.setVisibility(View.GONE);
        list = new ArrayList<>();
        initRec();
//        initViewModel();
    }

    private void initRec() {
        for (int i = 0; i < 5; i++) {
            list.add(new Notification("Tuấn Anh","Test notification","9h30"));
        }
        adapter = new NotificationAdapter(this, list, new OnListenerNotification() {
            @Override
            public void onClickShowDetailNotification(Notification notification) {

            }
        });
        binding.rvNotification.setAdapter(adapter);
    }

    private void initViewModel() {
        viewModel.mListNotiLiveData.observe(this, new Observer<List<Notification>>() {
            @Override
            public void onChanged(List<Notification> notifications) {
                list = notifications;
                adapter.setList(list);
                binding.rvNotification.setVisibility(View.VISIBLE);
                binding.prgLoadBill.setVisibility(View.GONE);
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
                return true;
            }
        });
    }
}