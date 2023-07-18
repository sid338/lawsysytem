package com.example.lawsystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class AdvocateReportAdapter extends RecyclerView.Adapter<AdvocateReportAdapter.MyViewHolder> {
    Context context;
    ArrayList<ChatModel> list;
    DatabaseReference databaseReference;
     boolean isUser;

    public AdvocateReportAdapter(Context context, ArrayList<ChatModel> list, boolean isUser) {
        this.context = context;
        this.list = list;
        this.isUser = isUser;
    }

    public AdvocateReportAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.reportadv,parent,false);

        return new MyViewHolder(v);
    }

    public void onBindViewHolder(@NonNull AdvocateReportAdapter.MyViewHolder holder, int position) {

       if(isUser){
           holder.fullName.setText(list.get(position).getMessage() + list.get(position).getFromName());
       } else {
           holder.fullName.setText(list.get(position).getMessage() + list.get(position).getToName());
       }
    }


    public int getItemCount() {

        return list.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView fullName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            fullName= itemView.findViewById(R.id.report);


        }
    }
}