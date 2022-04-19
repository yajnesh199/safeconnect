package com.example.sample1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import java.util.Objects;

@SuppressLint("CustomSplashScreen")
public class splashscreen extends AppCompatActivity {
TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_splashscreen);
        textView=findViewById(R.id.textview1);

        //handler
        new Handler().postDelayed(() -> {
            Intent intent=new Intent(splashscreen.this,Login_Activity.class);
            startActivity(intent);
            finish();
        },3000);
    }
}