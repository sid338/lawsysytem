package com.example.lawsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.lawsystem.databinding.ActivityCasefileBinding;

import java.util.ArrayList;
import java.util.List;

public class casefile extends AppCompatActivity {
    private ActivityCasefileBinding binding;


    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCasefileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnupload.setOnClickListener(v -> {
            Intent i=new Intent(getApplicationContext(),adddata.class);
            startActivity(i);
        });
    }
}