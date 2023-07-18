package com.example.lawsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.lawsystem.databinding.ActivityUserReportBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserReport extends AppCompatActivity {

    private ActivityUserReportBinding binding;
    private AdvocateReportAdapter advreport;
    DatabaseReference databaseReference;
    SharedPreferences sh;
    ArrayList<ChatModel> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserReportBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.advreport.setLayoutManager(new LinearLayoutManager(this));
        advreport=new AdvocateReportAdapter(this, list, true);
        binding.advreport.setAdapter(advreport);
        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://lawsystem-49e23-default-rtdb.firebaseio.com//");

        sh=getApplicationContext().getSharedPreferences("MySharedPreferences", MODE_PRIVATE);
        String username=sh.getString("UserName","");


        databaseReference.child("Chat").child(username).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot snapshot1:snapshot.getChildren()){
                    for (DataSnapshot snapshot2 : snapshot1.getChildren()){
                        ChatModel model = snapshot2.getValue(ChatModel.class);
                        if(model.isPayment()){
                            list.add(model);
                        }
                    }
                }
                advreport.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}