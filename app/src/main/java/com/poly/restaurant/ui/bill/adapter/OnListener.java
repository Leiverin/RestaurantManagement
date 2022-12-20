package com.poly.restaurant.ui.bill.adapter;

import com.poly.restaurant.data.models.Bill;

public interface OnListener {
    void onClickBill(Bill bill);
    void onClickFeedback(Bill bill);
}
