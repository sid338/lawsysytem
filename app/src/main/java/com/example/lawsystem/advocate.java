package com.example.lawsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.lawsystem.databinding.ActivityAdvocateBinding;

public class advocate extends AppCompatActivity {

    private ActivityAdvocateBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdvocateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.userRequest.setOnClickListener(v -> {
           Intent i = new Intent(this, UserChatList.class);
           startActivity(i);
        });

        binding.card3.setOnClickListener(v -> {
            Intent i = new Intent(this, AdvocateReport.class);
            startActivity(i);
        });
        binding.card4.setOnClickListener(v -> {
            Intent i = new Intent(this, policedetails.class);
            startActivity(i);
        });
        binding.card5.setOnClickListener(v -> {
            Intent i = new Intent(this, Feedback.class);
            i.putExtra("activity","advocate");
            startActivity(i);
        });
    }

}