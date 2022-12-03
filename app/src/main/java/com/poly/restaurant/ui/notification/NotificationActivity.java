package com.poly.restaurant.ui.notification;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.poly.restaurant.databinding.ActivityNotificationBinding;


public class NotificationActivity extends AppCompatActivity {
    private ActivityNotificationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.test.setOnClickListener(view -> {
            String title = binding.title.getText().toString().trim();
            String message = binding.message.getText().toString().trim();
            if (!title.equals("") && !message.equals("")) {
                FCMSend.pushNoti(
                        this,
                        "etMN7olfT0igYAuHgb2TdQ:APA91bESUfer70vWrshBV7xWUUSJv-7rfXsQQWl4epwqQzfP4jVz3yiUxBQeiEyDQksiMre0_cnWFz2_y2j_qs5aC\n" +
                                "_Q9ZYZesPIOf-Zi8aUnsI3LqXEI83YTvV7Wel-SzVv_xVapCL0v",
                        "Tuấn Anh",
                        "Hello"
                );
            }
        });
    }
}