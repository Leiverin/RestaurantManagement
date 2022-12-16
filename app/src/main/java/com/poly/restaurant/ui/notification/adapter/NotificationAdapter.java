package com.poly.restaurant.ui.notification.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.poly.restaurant.data.models.Notification;
import com.poly.restaurant.databinding.ItemNotiBinding;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {
    private Context context;
    private List<Notification> list;
    private OnListenerNotification listenerNotification;

    public NotificationAdapter(Context context, List<Notification> list, OnListenerNotification listenerNotification) {
        this.context = context;
        this.list = list;
        this.listenerNotification = listenerNotification;
    }

    public void setList(List<Notification> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NotificationAdapter.NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotificationAdapter.NotificationViewHolder(ItemNotiBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.NotificationViewHolder holder, int position) {
        Notification notification = list.get(position);
        if (notification != null) {
            holder.binding.title.setText(notification.getTitle());
            holder.binding.content.setText(notification.getContent());
            holder.binding.timeAgo.setText(notification.getTime());
            holder.binding.getRoot().setOnClickListener(view -> {
                listenerNotification.onClickShowDetailNotification(notification);
            });
        }
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {
        private ItemNotiBinding binding;

        public NotificationViewHolder(ItemNotiBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
