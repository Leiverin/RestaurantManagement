package com.poly.restaurant.ui.contact;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.material.snackbar.Snackbar;
import com.poly.restaurant.R;
import com.poly.restaurant.data.models.Staff;
import com.poly.restaurant.databinding.ActivityContactBinding;
import com.poly.restaurant.databinding.DialogSendEmailBinding;
import com.poly.restaurant.ui.base.BaseActivity;
import com.poly.restaurant.utils.Constants;

import java.util.List;
import java.util.Objects;

public class ContactActivity extends BaseActivity {
    private ActivityContactBinding binding;
    private ContactViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        binding = ActivityContactBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.prgLoadBill.setVisibility(View.VISIBLE);
        binding.layoutContact.setVisibility(View.GONE);
        initViewModel();
        initAction();
        LocalBroadcastManager.getInstance(this).registerReceiver((receiver),
                new IntentFilter(Constants.REQUEST_TO_ACTIVITY)
        );
    }

    private void initViewModel() {
        viewModel.mContactLiveData.observe(this, new Observer<List<Staff>>() {
            @Override
            public void onChanged(List<Staff> staff) {
                for (int i = 0; i < staff.size(); i++) {
                    if (Objects.equals(staff.get(i).getAccount(), "admin")) {
                        binding.tvName.setText(staff.get(i).getName());
                        binding.prgLoadBill.setVisibility(View.GONE);
                        binding.layoutContact.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    private void initAction() {
        binding.actionCall.setOnClickListener(view -> {
            Intent dialIntent = new Intent(Intent.ACTION_DIAL);
            dialIntent.setData(Uri.parse("tel:" + Constants.CALL_CENTER_NUMBER));
            startActivity(dialIntent);
        });
        binding.actionSkype.setOnClickListener(view -> {
            Constants.skype("live:563fc1cace9806ca", this);
        });
        binding.actionMail.setOnClickListener(view -> {
            dialogSendEmail();
        });

    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                viewModel.getAdminContact();
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
        viewModel.getAdminContact();
        super.onResume();
    }

    private void dialogSendEmail() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        DialogSendEmailBinding sendEmailBinding = DialogSendEmailBinding.inflate(LayoutInflater.from(this));
        builder.setView(sendEmailBinding.getRoot());
        AlertDialog dialog = builder.create();
        sendEmailBinding.btnNo.setOnClickListener(view -> {
            dialog.dismiss();
        });
        sendEmailBinding.btnYes.setOnClickListener(view -> {
            String titleSend = sendEmailBinding.title.getText().toString().trim();
            String contentSend = sendEmailBinding.content.getText().toString().trim();
            if (titleSend.equals("") || contentSend.equals("")) {
                Snackbar.make(sendEmailBinding.getRoot(), "Vui lòng điền đầy đủ thông tin !", Snackbar.LENGTH_LONG).show();
            } else {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{"thinhndph18079@fpt.edu.vn"});
                i.putExtra(Intent.EXTRA_SUBJECT, titleSend);
                i.putExtra(Intent.EXTRA_TEXT, contentSend);
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(ContactActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.CENTER);
    }
}