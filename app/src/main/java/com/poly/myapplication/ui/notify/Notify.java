package com.poly.myapplication.ui.notify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.poly.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class Notify extends AppCompatActivity {
    private RecyclerView rcv;
    private NotifyAdapter adt ;
    EditText edtSearch;

    @SuppressLint("MissingInflatedId")
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
        edtSearch =findViewById(R.id.edtsearch);
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    adt.getFilter().filter(charSequence);
                   rcv.setLayoutManager(linearLayoutManager);
                adt.setData(getListNotify());
                rcv.setAdapter(adt);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    private List<NotifyObj> getListNotify(){
        List<NotifyObj> list = new ArrayList<>();
        list.add(new NotifyObj("asd","asdas","5 ngay truoc"));
        list.add(new NotifyObj("asd","asdas","5 ngay truoc"));
        list.add(new NotifyObj("asd","asdas","5 ngay truoc"));
        list.add(new NotifyObj("nghia","bcsbb","5 ngay truoc"));
        list.add(new NotifyObj("nghia","asdas","5 ngay truoc"));
        list.add(new NotifyObj("nghia","asdas","5 ngay truoc"));
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