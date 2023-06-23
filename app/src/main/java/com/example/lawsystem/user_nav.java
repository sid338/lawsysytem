package com.example.lawsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
        getuserdetails();
    }

    private void getuserdetails() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://lawsystem-49e23-default-rtdb.firebaseio.com/");
        SharedPreferences sh = getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE); // to store data for temp time
        String s1 = sh.getString("name", "");
        databaseReference.child("Profile").child(s1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String nameStr = snapshot.child("Username").getValue(String.class);
                String emailStr = snapshot.child("Email_Id").getValue(String.class);
                String numberStr = snapshot.child("ContactNo").getValue(String.class);
                SharedPreferences.Editor editor=sh.edit();
                editor.putString("name", nameStr);
                editor.putString("email", emailStr);
                editor.putString("number", numberStr);
                editor.apply();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private final  BottomNavigationView.OnNavigationItemSelectedListener navListener= item -> {
        Fragment selectedFragment = null;
        int itemId=item.getItemId();
        if(itemId==R.id.profile){
            selectedFragment=new user_MyProfile().newInstance("",username);
        } else if (itemId==R.id.home) {
           selectedFragment = new user_Home_Page().newInstance("",username);
        } else if (itemId==R.id.settings) {
            selectedFragment=new UserChatListTwo().newInstance("",username);
        }
        if(selectedFragment!=null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
        }
        return true;
    };

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        AlertDialog.Builder.alertDialog=new AlertDialog.Builder(MainActivity.this);
//        alertDialog.setTitle("Exit App");
//        alertDialog.setMessage("Do You Want To Exit ?");
//        alertDialog.setPositiveButton("yes",new DialogInterface.OnClickListener(){
//
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                finishAffinity();
//            }
//        });
//        alertDialog.setNegativeButton("No",new DialogInterface)
//    }
}