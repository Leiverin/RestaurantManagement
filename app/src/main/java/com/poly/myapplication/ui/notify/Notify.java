package com.poly.myapplication.ui.notify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.poly.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class Notify extends AppCompatActivity {
    private RecyclerView rcv;
    private NotifyAdapter adt ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);
        rcv = findViewById(R.id.rcv_notify);
        adt = new NotifyAdapter(this);
        LinearLayoutManager linearLayoutManager  = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        rcv.setLayoutManager(linearLayoutManager);
        adt.setData(getListNotify());
        rcv.setAdapter(adt);

    }
    private List<NotifyObj> getListNotify(){
        List<NotifyObj> list = new ArrayList<>();
        list.add(new NotifyObj("asd","asdas","5 ngay truoc"));
        list.add(new NotifyObj("asd","asdas","5 ngay truoc"));
        list.add(new NotifyObj("asd","asdas","5 ngay truoc"));
        list.add(new NotifyObj("asd","asdas","5 ngay truoc"));
        list.add(new NotifyObj("asd","asdas","5 ngay truoc"));
        list.add(new NotifyObj("asd","asdas","5 ngay truoc"));
        list.add(new NotifyObj("asd","asdas","5 ngay truoc"));
        list.add(new NotifyObj("asd","asdas","5 ngay truoc"));
        list.add(new NotifyObj("asd","asdas","5 ngay truoc"));
        list.add(new NotifyObj("asd","asdas","5 ngay truoc"));
        list.add(new NotifyObj("asd","asdas","5 ngay truoc"));
        list.add(new NotifyObj("asd","asdas","5 ngay truoc"));
        list.add(new NotifyObj("asd","asdas","5 ngay truoc"));
        list.add(new NotifyObj("asd","asdas","5 ngay truoc"));
        list.add(new NotifyObj("asd","asdas","5 ngay truoc"));
        list.add(new NotifyObj("asd","asdas","5 ngay truoc"));
        list.add(new NotifyObj("asd","asdas","5 ngay truoc"));
        list.add(new NotifyObj("asd","asdas","5 ngay truoc"));
        return list;
    }
}