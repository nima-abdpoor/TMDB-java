package com.chinachino.mvvm.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chinachino.mvvm.R

 class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
    }
}