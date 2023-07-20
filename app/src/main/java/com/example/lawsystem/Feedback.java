package com.example.lawsystem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lawsystem.databinding.ActivityFeedbackBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Feedback extends AppCompatActivity {
    DatabaseReference databaseReference;
    String feedbackStr, key, s1, activity;
    SharedPreferences sh;
    private ActivityFeedbackBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFeedbackBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sh = getSharedPreferences("MySharedPreferences", MODE_PRIVATE); // to store data for temp time
        s1 = sh.getString("UserName", "");

        activity = getIntent().getStringExtra("activity");
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://lawsystem-49e23-default-rtdb.firebaseio.com/");
        key = databaseReference.push().getKey();
        binding.btnFeedback.setOnClickListener(view -> {
            feedbackStr = binding.feedback.getText().toString();
            if (feedbackStr.isEmpty()) {
                Toast.makeText(this, "Please enter all details", Toast.LENGTH_SHORT).show();
            } else {
                databaseReference.child("FEEDBACK").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference.child("FEEDBACK").child(key).child("Feedback").setValue(feedbackStr);
                        databaseReference.child("FEEDBACK").child(key).child("Username").setValue(s1);
                        if (activity.equals("advocate")) {
                            Intent i = new Intent(getApplicationContext(), advocate.class);
                            startActivity(i);
                        } else {
                            Intent i = new Intent(getApplicationContext(), user_Home_Page.class);
                            startActivity(i);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });


    }
}