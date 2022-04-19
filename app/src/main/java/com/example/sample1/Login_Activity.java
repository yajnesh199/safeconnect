package com.example.sample1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.airbnb.lottie.LottieAnimationView;

public class Login_Activity extends AppCompatActivity {
    LottieAnimationView loginAnimationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginAnimationView=findViewById(R.id.login_Animation);
        loginAnimationView.playAnimation();
        set_up_action_and_status_bar();
    }
    public void set_up_action_and_status_bar() {
        //hiding the action bar
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();
        //to get transparent status bar, try changing the themes
        Window window = getWindow();
        window.setStatusBarColor(Color.WHITE);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }
}