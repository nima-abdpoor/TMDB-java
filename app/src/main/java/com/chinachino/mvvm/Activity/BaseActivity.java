package com.chinachino.mvvm.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.chinachino.mvvm.R;

public class BaseActivity extends AppCompatActivity {
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }
}
