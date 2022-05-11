package com.example.sample1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class Login_Activity extends AppCompatActivity {
    LottieAnimationView loginAnimationView;
    String userstr, passstr;
    SharedPreferences sharedPreferences;
    TextInputEditText textInputEditTextUsername, textInputEditTextPassword;
    Button btnlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        set_up_action_and_status_bar();
        loginAnimationView = findViewById(R.id.login_Animation);
        loginAnimationView.playAnimation();
        sharedPreferences = getSharedPreferences("Details", Context.MODE_PRIVATE);
        textInputEditTextUsername = findViewById(R.id.edusername);
        textInputEditTextPassword = findViewById(R.id.edpassword);
        btnlogin = findViewById(R.id.btn_login);

        btnlogin.setOnClickListener(view -> {
            userstr = (textInputEditTextUsername.getText()).toString();
            passstr = (textInputEditTextPassword.getText()).toString();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("user", userstr);
            editor.putString("pass", passstr);
            editor.apply();
            editor.commit();
            Intent intent = new Intent(Login_Activity.this,pairing_list.class);
            startActivity(intent);
            Toast.makeText(Login_Activity.this, "Info saved Welcome", Toast.LENGTH_SHORT).show();
        });
    }

    private void set_up_action_and_status_bar() {
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();
        //to get transparent status bar, try changing the themes
        Window window = getWindow();
        window.setStatusBarColor(Color.WHITE);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }
}