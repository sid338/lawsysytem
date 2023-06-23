package com.example.lawsystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class userchat extends RecyclerView.Adapter<userchat.MyViewHolder> {
    Context context;
    List<ChatModel> list;

    public userchat(Context context, List<ChatModel> list) {
        this.context = context;
        this.list = list;
    }

    public userchat.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.user_chat_list,parent,false);

        return new MyViewHolder(v);
    }

    public void onBindViewHolder(@NonNull userchat.MyViewHolder holder, int position) {
      //  Glide.with(context ).load("").into(holder.circleImageView);
        holder.fullName.setText(list.get(position).getFromName());

        holder.cardView.setVisibility(View.GONE);

        holder.itemView.setOnClickListener(v -> {
           UserChatList.userChatInterface.userInterface(list.get(position).getFromName());
        });

    }


    public int getItemCount() {

        return list.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView fullName;
        CircleImageView circleImageView;
        MaterialCardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            fullName= itemView.findViewById(R.id.username);
            circleImageView=itemView.findViewById(R.id.profile_image);
            cardView=itemView.findViewById(R.id.cardView);

        }
    }
}