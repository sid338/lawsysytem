package com.example.lawsystem;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Police_Adapter extends RecyclerView.Adapter<Police_Adapter.MyViewHolder> {
    Context context;
    ArrayList<police_ModelClass> list;
    DatabaseReference databaseReference;

    public Police_Adapter(Context context, ArrayList<police_ModelClass> list) {
        this.context = context;
        this.list = list;
    }

    public Police_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.police_layout,parent,false);

        return new MyViewHolder(v);
    }

    public void onBindViewHolder(@NonNull Police_Adapter.MyViewHolder holder, int position) {
        SharedPreferences sh= context.getSharedPreferences("MySharedPreferences1", MODE_PRIVATE);
        String s1=sh.getString("contactno","");


        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://lawsystem-49e23-default-rtdb.firebaseio.com//");

        police_ModelClass police=list.get(position);
        holder.location.setText(police.getStationlocation());
        holder.contactno.setText(police.getPhonenumber());

    }
    public void filterList(ArrayList<police_ModelClass> filteredlist) {
        // below line is to add our filtered
        // list in our course array list.
        list = filteredlist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }

    public int getItemCount() {

        return list.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView location,contactno;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            location= itemView.findViewById(R.id.policeLocation);
            contactno=itemView.findViewById(R.id.policeContactno);
        }
    }
}