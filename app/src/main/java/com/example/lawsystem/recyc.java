package com.example.lawsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class recyc extends AppCompatActivity {

    List<advocates> Advocatelist;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyc);

        recyclerView=(RecyclerView) findViewById(R.id.recyclerview);
       // recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        Advocatelist=new ArrayList<>();

        Advocatelist.add(
                new advocates(
                        1,
                        "Hithesh K Bimal",
                        "7 years",
                        4.3,
                        5000,
                        R.drawable.hkb));

        Advocatelist.add(
                new advocates(
                        1,
                        "Navin Ross K",
                        "5 years",
                        4,
                        6000,
                        R.drawable.sen));


        Advocatelist.add(
                new advocates(
                        1,
                        "Sruthish joseph",
                        "4.5 years",
                        4.5,
                        5500,
                        R.drawable.sru));


        Advocatelist.add(
                new advocates(
                        1,
                        "Devdars KJ",
                        "5 years",
                        4.5,
                        7000,
                        R.drawable.dev));





       /* advocateAdapter adapter=new advocateAdapter(this,Advocatelist);
        recyclerView.setAdapter(adapter);*/

        HomeRecyclerViewAdapter homeRecyclerViewAdapter=new HomeRecyclerViewAdapter(this,Advocatelist);
    recyclerView.setAdapter(homeRecyclerViewAdapter);
    }
}