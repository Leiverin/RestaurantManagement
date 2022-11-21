package com.poly.myapplication.ui.notify;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.poly.myapplication.R;

import java.util.List;

public class NotifyAdapter extends RecyclerView.Adapter<NotifyAdapter.NotifyViewHolder> {


    private Context mContext;
    private List<NotifyObj> mList;

    public NotifyAdapter(Context context) {
        this.mContext = context;
    }
    public void setData(List<NotifyObj> list){
        this.mList =list;
        notifyDataSetChanged();
    }

    public NotifyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notify,parent,false);
        return new NotifyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotifyViewHolder holder, int position) {
        NotifyObj notifyObj = mList.get(position);
        if (notifyObj == null) {
            return;
        }
        holder.tvTitle.setText(notifyObj.getTitle());
        holder.tvContent.setText(notifyObj.getContent());
        holder.tvTime.setText(notifyObj.getTime());
    }

    @Override
    public int getItemCount() {
        if (mList != null){
            return mList.size();
        }
        return 0;
    }

    public class NotifyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private TextView tvContent;
        private TextView tvTime;
        public NotifyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.tvContent);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvTime = itemView.findViewById(R.id.tvTime);
        }
    }
}
