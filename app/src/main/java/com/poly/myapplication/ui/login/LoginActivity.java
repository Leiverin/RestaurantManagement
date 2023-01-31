package com.poly.myapplication.ui.login;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;


import androidx.appcompat.app.AppCompatActivity;

import com.poly.restaurant.R;


public class LoginActivity extends AppCompatActivity {
    private AppSharePreference sharePreference;


    EditText userName;
    EditText passWord;
    boolean passWordVisible;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userName = findViewById(R.id.ed_username);
        passWord = findViewById(R.id.ed_pass);
        sharePreference = new AppSharePreference(this);
        passWord.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int Right=2;
                if (motionEvent.getAction()==MotionEvent.ACTION_UP){
                    if (motionEvent.getRawX()>=passWord.getRight()-passWord.getCompoundDrawables()[Right].getBounds().width()){
                        int selection=passWord.getSelectionEnd();
                        if (passWordVisible){
                            passWord.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.view_hide,0);
                            passWord.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passWordVisible = false;
                        }else{
                            passWord.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.eye_visible,0);
                            passWord.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passWordVisible = true;
                        }
                        passWord.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });
        if (sharePreference.getRemember()) {
            binding.edUsername.setText(sharePreference.getUsername());
            binding.edPass.setText(sharePreference.getPassword());
            binding.cbxRemember.setChecked(true);
        } else {
            binding.edUsername.setText("");
            binding.edPass.setText("");
            binding.cbxRemember.setChecked(false);
        }
    }
}