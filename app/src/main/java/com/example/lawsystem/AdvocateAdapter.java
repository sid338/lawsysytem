package com.example.lawsystem;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdvocateAdapter extends RecyclerView.Adapter<AdvocateAdapter.MyViewHolder> {
    Context context;
    ArrayList<advocates> list;
    DatabaseReference databaseReference;

    public AdvocateAdapter(Context context, ArrayList<advocates> list) {
        this.context = context;
        this.list = list;
    }

    public AdvocateAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.activity_product_layout,parent,false);

        return new MyViewHolder(v);
    }

    public void onBindViewHolder(@NonNull AdvocateAdapter.MyViewHolder holder, int position) {
        SharedPreferences sh= context.getSharedPreferences("MySharedPreferences1", MODE_PRIVATE);
        String s1=sh.getString("Username","");


        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://lawsystem-49e23-default-rtdb.firebaseio.com//");

        advocates mechanic=list.get(position);
        holder.fullName.setText(mechanic.getName());
        holder.emailid.setText(mechanic.getEmail_Id());
        holder.contactno.setText(mechanic.getContactNo());
    }
    public void filterList(ArrayList<advocates> filteredlist) {
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
        TextView fullName,emailid,contactno;
        Button btn_del;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            fullName= itemView.findViewById(R.id.advName);
            emailid=itemView.findViewById(R.id.advEmail);
            contactno=itemView.findViewById(R.id.advContactno);
        }
    }
}