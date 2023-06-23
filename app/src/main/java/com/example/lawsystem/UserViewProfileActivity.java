package com.example.lawsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.lawsystem.databinding.ActivityUserViewProfileBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserViewProfileActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    String name = "";
    ActivityUserViewProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserViewProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        name = getIntent().getStringExtra("name");

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://lawsystem-49e23-default-rtdb.firebaseio.com/");
        databaseReference.child("Profile").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(name)){
                    binding.name.setText(snapshot.child(name).child("Username").getValue(String.class));
                    binding.email.setText(snapshot.child(name).child("Email_Id").getValue(String.class));
                    binding.number.setText(snapshot.child(name).child("ContactNo").getValue(String.class));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}