package com.poly.restaurant.ui.bill.adapter;

import androidx.cardview.widget.CardView;

import com.poly.restaurant.data.models.Bill;

public interface OnListener {
    void onClickBill(Bill bill, CardView cardView);
    void onClickFeedback(Bill bill);
}
