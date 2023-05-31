package com.example.lawsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class policerecyc extends AppCompatActivity {

    List<advocates> Policelist;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policerecyc);

        recyclerView=(RecyclerView) findViewById(R.id.policerecyclerview);
        // recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        Policelist=new ArrayList<>();

        /*Policelist.add(
                new advocates(
                        1,
                        "Hithesh K Bimal",
                        "7 years",
                        4.3,
                        5000,
                        R.drawable.hkb));*/

        HomeRecyclerViewAdapter homeRecyclerViewAdapter=new HomeRecyclerViewAdapter(this,Policelist);
        recyclerView.setAdapter(homeRecyclerViewAdapter);
    }
}