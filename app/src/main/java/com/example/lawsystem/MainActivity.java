package com.example.lawsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.addisonelliott.segmentedbutton.SegmentedButtonGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    Button login;
    TextInputEditText username, password;
    String usernameStr, passwordStr;
    DatabaseReference databaseReference;
    TextView notregistered,govtreg;
    SegmentedButtonGroup segmentedButtonGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = (Button) findViewById(R.id.button);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        notregistered = (TextView) findViewById(R.id.signin);
        govtreg=(TextView) findViewById(R.id.govtreg);
        segmentedButtonGroup= (SegmentedButtonGroup) findViewById(R.id.segmentedbutton);
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://lawsystem-49e23-default-rtdb.firebaseio.com/");
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                usernameStr = username.getText().toString();
                passwordStr = password.getText().toString();

                if (passwordStr.isEmpty() || usernameStr.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please Enter All Details", Toast.LENGTH_SHORT).show();
                } else {
                    if(segmentedButtonGroup.getPosition()==0){
                        databaseReference.child("Profile").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.hasChild(usernameStr)) {
                                    final String getPassword = snapshot.child(usernameStr).child("Password").getValue(String.class);
                                    final String getUsername = snapshot.child(usernameStr).child("Username").getValue(String.class);
                                    //Toast.makeText(MainActivity.this, snapshot.toString(), Toast.LENGTH_SHORT).show();
                                    if (getPassword.equals(passwordStr)) {
                                        SharedPreferences sh = getSharedPreferences("MySharedPreferences", MODE_PRIVATE);
                                        SharedPreferences.Editor editor=sh.edit();
                                        editor.putString("name",usernameStr);
                                        editor.putString("UserName",getUsername);
                                        editor.apply();
                                        Intent i = new Intent(getApplicationContext(), user_nav.class);
                                        i.putExtra("username",usernameStr);
                                        startActivity(i);
                                        Toast.makeText(MainActivity.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(MainActivity.this, "Wrong Password ", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(MainActivity.this, "Wrong credentials ", Toast.LENGTH_SHORT).show();
                                }
                            }


                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(MainActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else {
                        databaseReference.child("Lawyer").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                                    if(dataSnapshot.child("Username").getValue(String.class).equals(usernameStr)){
                                        String userName = dataSnapshot.child("Name").getValue(String.class);

                                        SharedPreferences sh = getSharedPreferences("MySharedPreferences", MODE_PRIVATE);
                                        SharedPreferences.Editor editor=sh.edit();
                                        editor.putString("name",usernameStr);
                                        editor.putString("UserName", userName);
                                        editor.apply();
                                        Intent i = new Intent(getApplicationContext(), advocate.class);
                                        startActivity(i);
                                        // startActivity(new Intent(this,advocate.class));
                                        Toast.makeText(MainActivity.this,"login sucess",Toast.LENGTH_SHORT);
                                    }
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }

            }
        });
        notregistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), page2.class);
                startActivity(i);

            }
        });
        govtreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),govtregistration.class);
                startActivity(i);
            }
        });
        segmentedButtonGroup.setOnPositionChangedListener(new SegmentedButtonGroup.OnPositionChangedListener() {
            @Override
            public void onPositionChanged(final int position) {
              if (position ==1){
                  username.setText("ADV");


              }
              else {
                  username.setText(" ");
              }
            }
        });
    }
    public void onResume() {
        super.onResume();
        SharedPreferences sh = getSharedPreferences("MySharedPreferences", MODE_PRIVATE);  // saves username
        String s1 = sh.getString("Username", "");
    }
}