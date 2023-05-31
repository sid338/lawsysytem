package com.example.lawsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class user_nav extends AppCompatActivity {
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_nav);

        //GETTING username
        Bundle extras=getIntent().getExtras();
  username= extras.getString("username");

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new user_Home_Page()).commit();
    }
    private final  BottomNavigationView.OnNavigationItemSelectedListener navListener= item -> {
        Fragment selectedFragment = null;
        int itemId=item.getItemId();
        if(itemId==R.id.profile){
            selectedFragment=new user_MyProfile().newInstance("",username);
        } else if (itemId==R.id.home) {
           selectedFragment = new user_Home_Page().newInstance("",username);
        } else if (itemId==R.id.settings) {
          //  selectedFragment=new user_().newInstance("",username);
        }
        if(selectedFragment!=null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
        }
        return true;
    };

}