package com.example.sample1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ActionMenuView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class bottomnavig extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    ActionMenuView menu;
    pairedFragment firstFragment=new pairedFragment();
    chatFragment secondFragment=new chatFragment();
    profileFragment thirdFragment=new profileFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottomnavig);
        menu=findViewById(R.id.menu);
        bottomNavigationView =findViewById(R.id.bottom_navig);
      //  bottomNavigationView.setSelectedItemId(R.id.Image);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,firstFragment).commit();
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,firstFragment).commit();
                        return true;
                    case R.id.Image:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,secondFragment).commit();
                        return true;
                    case R.id.Settings:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,thirdFragment).commit();
                        return true;
                }
                return false;
            }
        });
    }
}