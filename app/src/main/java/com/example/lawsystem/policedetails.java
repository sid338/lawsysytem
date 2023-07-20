package com.example.lawsystem;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lawsystem.databinding.ActivityPolicedetailsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class policedetails extends AppCompatActivity {
    ArrayList<police_ModelClass> list=new ArrayList<>();
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    Police_Adapter myAdapter;
    private ActivityPolicedetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPolicedetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://lawsystem-49e23-default-rtdb.firebaseio.com/");
        binding.recyclerview.setHasFixedSize(true);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this));

        myAdapter = new Police_Adapter(this, list);
        binding.recyclerview.setAdapter(myAdapter);

        databaseReference.child("Police").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    police_ModelClass advocate = dataSnapshot.getValue(police_ModelClass.class);
                    list.add(advocate);
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),"Error loading data"+error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}