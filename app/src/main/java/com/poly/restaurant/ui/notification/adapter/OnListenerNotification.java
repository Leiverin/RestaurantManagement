package com.poly.restaurant.ui.notification.adapter;

import androidx.cardview.widget.CardView;

import com.poly.restaurant.data.models.Notification;

public interface OnListenerNotification {
    void onClickShowDetailNotification(Notification notification, CardView cardView);
}
