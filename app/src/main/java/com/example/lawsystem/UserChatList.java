package com.example.lawsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.lawsystem.databinding.ActivityUserChatListBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserChatList extends AppCompatActivity implements userChatInterface {
    private ActivityUserChatListBinding binding;
    private userchat userchat;

    DatabaseReference databaseReference;
    SharedPreferences sh;

    private ArrayList<ChatModel> chatModels = new ArrayList<>();
    private List<ChatModel> finalModel = new ArrayList<>();
    static userChatInterface userChatInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityUserChatListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        userChatInterface = this;

        sh = getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE); // to store data for temp time
        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://lawsystem-49e23-default-rtdb.firebaseio.com//");

        binding.chatrecy.setLayoutManager(new LinearLayoutManager(this));


        String fromName = sh.getString("UserName","");

        databaseReference.child("Chat-List").child(fromName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatModels.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    ChatModel model =  snapshot1.getValue(ChatModel.class);
                    if(model.getToName().equals(fromName)){
                        chatModels.add(model);
                    }
                }



                userchat=new userchat(UserChatList.this, chatModels);
                binding.chatrecy.setAdapter(userchat);
                userchat.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private ArrayList<ChatModel> getData (){
        ArrayList<ChatModel> newList = new ArrayList<ChatModel>();

        // Traverse through the first list
        for (ChatModel element : chatModels) {

            // If this element is not present in newList
            // then add it
            if (!newList.contains(element)) {

                newList.add(element);
            }
        }
        return newList;
    }

    @Override
    public void userInterface(String name) {
        Intent intent = new Intent(this, ChatActivityTwo.class);
        intent.putExtra("name",name);
        intent.putExtra("contact","");
        intent.putExtra("user","2");
        startActivity(intent);
    }

    @Override
    public void userIgnoreInterface(String name) {

    }

    @Override
    public void userPaymentSuccessInterface(String name) {

    }
}