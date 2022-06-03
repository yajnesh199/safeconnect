package com.example.sample1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;

public class bottomnavig extends AppCompatActivity {
    SmoothBottomBar smoothBottomBar;
    TextView toolbartitle;
    ImageView toolbarback;
  pairedFragment first=new pairedFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        set_up_action_and_status_bar();
        setContentView(R.layout.activity_bottomnavig);
        // menu=findViewById(R.id.menu);
        smoothBottomBar = findViewById(R.id.bottomBar);
         toolbartitle = findViewById(R.id.toolbar_Fragment_title);
         toolbarback=findViewById(R.id.toolbar_icon);
        toolbarback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(bottomnavig.this, Login_Activity.class);
                startActivity(intent);
            }
        });
        toolbartitle.setText(R.string.Paired);
        //  bottomNavigationView.setSelectedItemId(R.id.Image);
        Log.e("h", "fragment");
          getSupportFragmentManager().beginTransaction().replace(R.id.container, first).commit();
        init();
//        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.home:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.container, firstFragment).commit();
//                        return true;
//                    case R.id.Image:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.container, secondFragment).commit();
//                        return true;
//                    case R.id.Settings:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.container, thirdFragment).commit();
//                        return true;
//                }
//                return false;
//            }
//        });
//        bubbleNavigationLinearView.setNavigationChangeListener(new BubbleNavigationChangeListener() {
//            @Override
//            public void onNavigationChanged(View view, int position) {
//                switch (position) {
//                    case 0:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.container, firstFragment).commit();
//                        break;
//                    case 1:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.container, secondFragment).commit();
//                        break;
//                    case 2:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.container, thirdFragment).commit();
//                        break;
//                }
//
//            }
//        });


//        smoothBottomBar.setOnItemSelectedListener(new OnItemSelectedListener() {
//            @Override
//            public boolean onItemSelect(int i) {
//                switch (i) {
//                    case R.id.item0:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.container, firstFragment).commit();
//                        break;
//                    case R.id.item1:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.container, secondFragment).commit();
//                        break;
//                    case R.id.item2:
//                        getSupportFragmentManager().beginTransaction().replace(R.id.container, thirdFragment).commit();
//                        break;
//                }
//                return false;
//            }
//        });
//    }


    }

    private void replcae(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }

    private void init() {
        smoothBottomBar.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public boolean onItemSelect(int i) {
                switch (i) {
                    case 0:
                        replcae(new pairedFragment());
                        toolbartitle.setText(R.string.Paired);
                        break;
                    case 1:
                        replcae(new chatFragment());
                        toolbartitle.setText(R.string.Chat);
                        break;
                    case 2:
                        replcae(new profileFragment());
                        toolbartitle.setText(R.string.Profile);
                        break;
                }
                return false;
            }
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