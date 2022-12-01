package com.poly.myapplication.ui.notification;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.poly.myapplication.data.models.Bill;
import com.poly.myapplication.data.models.MyResponse;
import com.poly.myapplication.data.models.NotificationSender;
import com.poly.myapplication.data.models.Table;
import com.poly.myapplication.data.models.Token;
import com.poly.myapplication.data.retrofit.Client;
import com.poly.myapplication.data.retrofit.ServiceAPI;
import com.poly.myapplication.databinding.ActivityNotificationBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationActivity extends AppCompatActivity {
    private ActivityNotificationBinding binding;
    private ServiceAPI apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        apiService = Client.getClient("https://fcm.googleapis.com/").create(ServiceAPI.class);
        binding.test.setOnClickListener(view -> {
            FirebaseDatabase.getInstance().getReference().child("Tokens").child("pLREH83BpBOhCpiZzXtqFjhgUsC3").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String usertoken = dataSnapshot.getValue(String.class);
                    sendNotifications(usertoken, binding.title.getText().toString().trim(), "idBill");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        });
        UpdateToken();
    }

    private void UpdateToken() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String refreshToken = FirebaseInstanceId.getInstance().getToken();
        Token token = new Token(refreshToken);
        FirebaseDatabase.getInstance().getReference("Tokens").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(token);
    }

    public void sendNotifications(String usertoken, String title, String message) {
        Bill data = new Bill(title, message);
        NotificationSender sender = new NotificationSender(data, usertoken);
        apiService.sendNotifcation(sender).enqueue(new Callback<MyResponse>() {
            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                if (response.code() == 200) {
                    if (response.body().success != 1) {
                        Toast.makeText(NotificationActivity.this, "Failed ", Toast.LENGTH_LONG);
                    }
                }
            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {

            }
        });
    }

}