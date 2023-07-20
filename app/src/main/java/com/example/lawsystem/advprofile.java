package com.example.lawsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

public class advprofile extends AppCompatActivity {
    TextView name,email,number;
    String nameStr,emailStr,numberStr;
    DatabaseReference databaseReference;
    SharedPreferences sh;
    ImageView message;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advprofile);
        name=findViewById(R.id.name);

        message=findViewById(R.id.message);


        Intent intent=getIntent();
        String namestr=intent.getStringExtra("name");
        name.setText(namestr);



        String contactstr=intent.getStringExtra("contact");


        message.setOnClickListener(v -> {
//            startActivity(new Intent(this, ChatActivity.class));
            Intent i=new Intent(this, ChatActivity.class);
            i.putExtra("name",namestr);
            i.putExtra("contact",contactstr);
            i.putExtra("user","1");
            startActivity(i);
        });
    }
}