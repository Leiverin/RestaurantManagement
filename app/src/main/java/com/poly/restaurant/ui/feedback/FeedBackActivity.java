package com.poly.restaurant.ui.feedback;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Toast;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.poly.restaurant.R;
import com.poly.restaurant.data.models.Bill;
import com.poly.restaurant.data.models.Feedback;
import com.poly.restaurant.databinding.ActivityFeedBackBinding;
import com.poly.restaurant.ui.base.BaseActivity;
import com.poly.restaurant.utils.Constants;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class FeedBackActivity extends BaseActivity {
    private ActivityFeedBackBinding binding;
    private Bill bill;
    private FeedbackViewModel viewModel;
    private String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Calendar.getInstance().getTime());
    private String time = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(Calendar.getInstance().getTime());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(FeedbackViewModel.class);
        binding = ActivityFeedBackBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        bill = getIntent().getParcelableExtra(Constants.CREATE_FEEDBACK);
        checkBillFeedback();
        LocalBroadcastManager.getInstance(this).registerReceiver((receiver),
                new IntentFilter(Constants.REQUEST_TO_ACTIVITY)
        );
    }

    private void initViewModel() {
        viewModel.mFeedbackLiveData.observe(this, new Observer<Feedback>() {
            @Override
            public void onChanged(Feedback feedback) {
                if (feedback != null) {
                    Resources res = getResources();
                    String text = String.format(res.getString(R.string.url_feedback), feedback.getId());
                    Toast.makeText(FeedBackActivity.this, "Mời quý khách quét mã QR", Toast.LENGTH_SHORT).show();
                    MultiFormatWriter writer = new MultiFormatWriter();
                    try {
                        BitMatrix matrix = writer.encode(text, BarcodeFormat.QR_CODE, 800, 800);
                        BarcodeEncoder encoder = new BarcodeEncoder();
                        Bitmap bitmap = encoder.createBitmap(matrix);
                        binding.qrCode.setImageBitmap(bitmap);
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(FeedBackActivity.this, "Thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        viewModel.createFeedback(new Feedback(null, 4, 2, "Rất tốt", bill.getTable(), bill.getStaff(), date, time, bill.getId()));
    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                viewModel.getFeedback();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private void checkBillFeedback() {
        viewModel.mListFeedLiveData.observe(this, new Observer<List<Feedback>>() {
            @Override
            public void onChanged(List<Feedback> feedbacks) {
                for (int i = 0; i < feedbacks.size(); i++) {
                    if (Objects.equals(bill.getId(), feedbacks.get(i).getIdBill())) {

                    } else {
                        initViewModel();
                    }
                }
            }
        });
        viewModel.getFeedback();
    }

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
        viewModel.getFeedback();
        super.onResume();
    }
}