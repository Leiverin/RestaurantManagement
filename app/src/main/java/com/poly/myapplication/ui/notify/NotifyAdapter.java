package com.poly.myapplication.ui.notify;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.poly.restaurant.R;

import java.util.ArrayList;
import java.util.List;

public class NotifyAdapter extends RecyclerView.Adapter<NotifyAdapter.NotifyViewHolder> implements Filterable {


    private Context mContext;
    private List<NotifyObj> mList;
    private List<NotifyObj> mListOld;

    public NotifyAdapter(Context context) {
        this.mContext = context;
    }
    public void setData(List<NotifyObj> list){
        this.mList =list;
        this.mListOld=mList;

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

    @Override
    //search
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String stringSearch = charSequence.toString();
                if (stringSearch.isEmpty()){
                    mList = mListOld;
                }else{
                    List<NotifyObj> list = new ArrayList<>();
                    for (NotifyObj notify : mListOld){
                        if (notify.getTitle().toLowerCase().contains(stringSearch.toLowerCase())){
                            list.add(notify);
                        }
                    }
                    mList = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mList = (List<NotifyObj>) filterResults.values;
                notifyDataSetChanged();
            }
        };
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
