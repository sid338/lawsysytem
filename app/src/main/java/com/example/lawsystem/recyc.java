package com.example.lawsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class
recyc extends AppCompatActivity {

    ArrayList<advocates> Advocatelist;
    DatabaseReference databaseReference;
    SearchView searchView;
    RecyclerView recyclerView;
    AdvocateAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyc);

        recyclerView=(RecyclerView) findViewById(R.id.recyclerview);
        SharedPreferences sh= getSharedPreferences("MySharedPreferences1", MODE_PRIVATE);
        String s1=sh.getString("contactno","");
        recyclerView=findViewById(R.id.recyclerview);
        searchView= findViewById(R.id.searchview_Adv);
        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://lawsystem-49e23-default-rtdb.firebaseio.com/");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Advocatelist=new ArrayList<>();
        myAdapter =new AdvocateAdapter(this,Advocatelist);
        recyclerView.setAdapter(myAdapter);
        databaseReference.child("Lawyer").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Advocatelist.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    advocates advocate = dataSnapshot.getValue(advocates.class);
                    Advocatelist.add(advocate);
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),"Error loading data"+error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        //  SEARCH
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // creating a new array list to filter our data.
                ArrayList<advocates> filteredlist = new ArrayList<>();

                // running a for loop to compare elements.
                for (advocates item : Advocatelist) {
                    // checking if the entered string matched with any item of our recycler view.
                    if (item.getName().toLowerCase().contains(newText.toLowerCase())) {
                        // if the item is matched we are
                        // adding it to our filtered list.
                        filteredlist.add(item);
                    }
                }
                if (filteredlist.isEmpty()) {
                    // if no item is added in filtered list we are
                    // displaying a toast message as no data found.
                    Toast.makeText(getApplicationContext(), "No Data Found..", Toast.LENGTH_SHORT).show();
                } else {
                    // at last we are passing that filtered
                    // list to our adapter class.
                    myAdapter.filterList(filteredlist);
                }
                return false;
            }
        });
    }

}