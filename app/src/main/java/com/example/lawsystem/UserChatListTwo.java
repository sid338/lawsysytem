package com.example.lawsystem;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.lawsystem.databinding.ActivityUserChatListBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserChatListTwo extends Fragment implements userChatInterface {


    private ArrayList<String> chatModels = new ArrayList<>();
    private ArrayList<ChatModel> chatList = new ArrayList<>();
    private userchatTwo userchat;
    DatabaseReference databaseReference;
    SharedPreferences sh;
    private ActivityUserChatListBinding binding;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    static userChatInterface userInterface;

    public UserChatListTwo() {
        // Required empty public constructor
    }

    public static UserChatListTwo newInstance(String param1, String param2) {
        UserChatListTwo fragment = new UserChatListTwo();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userInterface = this;
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_user__home__page, container, false);
        binding = ActivityUserChatListBinding.inflate(getLayoutInflater());
        sh = requireActivity().getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE); // to store data for temp time
        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://lawsystem-49e23-default-rtdb.firebaseio.com//");

        binding.chatrecy.setLayoutManager(new LinearLayoutManager(requireContext()));


        String fromName = sh.getString("UserName","");


        databaseReference.child("Chat").child(fromName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatList.clear();
                chatModels.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    chatModels.add(snapshot1.getKey());

                    for (DataSnapshot snapshot2 : snapshot1.getChildren()){
                        ChatModel chatModel = snapshot2.getValue(ChatModel.class);
                        chatList.add(chatModel);
                    }
                }

                try {
                    userchat=new userchatTwo(requireContext(), chatModels, chatList);
                    binding.chatrecy.setAdapter(userchat);
                    userchat.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return binding.getRoot();
    }

    @Override
    public void userInterface(String name) {
        Intent intent = new Intent(requireContext(), ChatActivity.class);
        intent.putExtra("name",name);
        intent.putExtra("contact","");
        intent.putExtra("user","3");
        startActivity(intent);
    }

    @Override
    public void userIgnoreInterface(String name) {

    }

    @Override
    public void userPaymentSuccessInterface(String name) {

    }
}