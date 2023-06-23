package com.example.lawsystem;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class userchatTwo extends RecyclerView.Adapter<userchatTwo.MyViewHolder> {
    Context context;
    List<String> list;
    List<ChatModel> list2;

    public userchatTwo(Context context, List<String> list, ArrayList<ChatModel> chatList) {
        this.context = context;
        this.list = list;
        this.list2 = chatList;
    }

    public userchatTwo.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.user_chat_list,parent,false);

        return new MyViewHolder(v);
    }

    public void onBindViewHolder(@NonNull userchatTwo.MyViewHolder holder, int position) {
      //  Glide.with(context ).load("").into(holder.circleImageView);
        holder.fullName.setText(list.get(position));

        int count = 0;
        for(int i=0; i<list2.size(); i++){
            if(!list2.get(i).isRead()){
                count++;
            }
        }

        holder.tvCount.setText(""+ count);

        if(count == 0){
            holder.cardView.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(v -> {
           UserChatListTwo.userInterface.userInterface(list2.get(position).getFromName());
        });

    }


    public int getItemCount() {

        return list.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView fullName, tvCount;
        CircleImageView circleImageView;
        MaterialCardView cardView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            fullName= itemView.findViewById(R.id.username);
            circleImageView=itemView.findViewById(R.id.profile_image);
            tvCount=itemView.findViewById(R.id.tvCount);
            cardView=itemView.findViewById(R.id.cardView);

        }
    }
}