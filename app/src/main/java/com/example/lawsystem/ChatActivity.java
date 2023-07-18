package com.example.lawsystem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lawsystem.databinding.ActivityChatBinding;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity implements userChatInterface {

    private ActivityChatBinding binding;
    private ChatAdapter adapter;
    DatabaseReference databaseReference;
    SharedPreferences sh;
    private ArrayList<ChatModel> chatModels = new ArrayList<>();
    Uri imageuri = null;
    String name;
    String namestr;
    String  isUser;
    String  fromName;
    static userChatInterface userChatInterface;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sh = getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE); // to store data for temp time
        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://lawsystem-49e23-default-rtdb.firebaseio.com//");

        userChatInterface = this;
        Intent intent=getIntent();
        namestr=intent.getStringExtra("name");
        String contactstr=intent.getStringExtra("contact");
        isUser=intent.getStringExtra("user");
        binding.chat.setLayoutManager(new LinearLayoutManager(this));


        String fromNumber = sh.getString("number","");
        adapter = new ChatAdapter(this, chatModels);
        binding.chat.setAdapter(adapter);

         fromName = sh.getString("name","");


        name = sh.getString("name", "");
        databaseReference.child("Chat").child(name).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatModels.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    if(snapshot1.getKey().equals(namestr)){
                        for (DataSnapshot snapshot2 : snapshot1.getChildren()){
                            ChatModel model =  snapshot2.getValue(ChatModel.class);
                            model.setRead(true);
                            chatModels.add(model);


                        }
                    }
                }

                for(int i=0; i<chatModels.size(); i++){
                    chatModels.get(i).setRead(true);
                    databaseReference.child("Chat").child(name).child(namestr).child(chatModels.get(i).getKey()).setValue(chatModels.get(i));
                }
                binding.chat.scrollToPosition(binding.chat.getAdapter().getItemCount()-1);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });

        adapter.notifyDataSetChanged();






        binding.imagebutton.setOnClickListener(v -> {
            String message=binding.text.getText().toString();

            if(!TextUtils.isEmpty(message)){

                if(isUser.equals("1")){
                    sendToAdv(message, fromName, namestr, isUser, false);
                } else  {
                    sendToUser(message, fromName, namestr, isUser, false);
                }
            }
            else {
                Toast.makeText(this, "Enter a message", Toast.LENGTH_SHORT).show();
            }
        });

        binding.ivAttachment.setOnClickListener(v -> {
            showBottomSheetDialog(name, namestr, isUser);
        });

    }

    private void sendToUser(String message, String fromName, String namestr, String isUser, boolean isFile) {
        ChatModel chatModel=new ChatModel();
        chatModel.setMessage(message);
        chatModel.setFromName(fromName.replace("ADV/",""));
        chatModel.setToName(namestr);
        chatModel.setUser(isUser.equals("1"));
        chatModel.setFile(isFile);
        if(isUser.equals("3")){
            chatModel.setUser(true);
        }
        chatModel.setRead(false);
        String pushKey;
        pushKey = databaseReference.push().getKey();
        chatModel.setKey(pushKey);
        databaseReference.child("Chat").child(chatModel.getFromName()).child(namestr).child(pushKey).setValue(chatModel);
        binding.text.setText("");
    }

    private void sendToAdv(String message,String fromName, String namestr, String isUser, boolean isFile) {
        ChatModel chatModel=new ChatModel();
        chatModel.setMessage(message);
        chatModel.setFromName(fromName);
        chatModel.setToName(namestr);
        chatModel.setRead(false);
        chatModel.setFile(isFile);
        chatModel.setUser(isUser.equals("1"));
        String pushKey;
        pushKey = databaseReference.push().getKey();
        chatModel.setKey(pushKey);
        databaseReference.child("Chat").child(fromName).child(namestr).child(pushKey).setValue(chatModel);
        if(isUser.equals("1")){
            databaseReference.child("Chat-List").child(namestr).child(fromName).setValue(chatModel);
        }
        binding.text.setText("");
    }

    private void showBottomSheetDialog(String name, String namestr, String isUser) {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog);

        TextView tvPayNow = bottomSheetDialog.findViewById(R.id.tvPayNow);
        TextView tvDoc = bottomSheetDialog.findViewById(R.id.tvDoc);
       tvPayNow.setVisibility(View.GONE);

        tvDoc.setOnClickListener(v -> {
            bottomSheetDialog.dismiss();
            Intent galleryIntent = new Intent();
            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);


            galleryIntent.setType("application/pdf");
            startActivityForResult(galleryIntent, 1);
        });


        bottomSheetDialog.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            ProgressDialog dialog;

            // Here we are initialising the progress dialog box
            dialog = new ProgressDialog(this);
            dialog.setMessage("Uploading");

            // this will show message uploading
            // while pdf is uploading
            dialog.show();
            imageuri = data.getData();
            final String timestamp = "" + System.currentTimeMillis();
            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
            final String messagePushID = timestamp;
            // Here we are uploading the pdf in firebase storage with the name of current time
            final StorageReference filepath = storageReference.child(messagePushID + "." + "pdf");
            filepath.putFile(imageuri).continueWithTask((Continuation) task -> {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return filepath.getDownloadUrl();
            }).addOnCompleteListener((OnCompleteListener<Uri>) task -> {
                if (task.isSuccessful()) {
                    // After uploading is done it progress
                    // dialog box will be dismissed
                    dialog.dismiss();
                    Uri uri = task.getResult();
                    String myurl;
                    myurl = uri.toString();
                    Log.e("TAG", "onComplete: "+myurl );
                    Toast.makeText(ChatActivity.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                    uploadFile(myurl);

                } else {
                    dialog.dismiss();
                    Toast.makeText(ChatActivity.this, "UploadedFailed", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void uploadFile(String myUrl) {
        if(isUser.equals("1")){
            sendToAdv(myUrl, fromName, namestr, isUser, true);
        } else  {
            sendToUser(myUrl, fromName, namestr, isUser, true);
        }
    }


    @Override
    public void userInterface(String name) {
        Intent intent = new Intent(this, RazorPay.class);
        intent.putExtra("amount", name);
        startActivity(intent);
    }

    @Override
    public void userIgnoreInterface(String name) {
        sendToAdv(name + " payment is cancelled by user", fromName, namestr, isUser, false);
    }

    @Override
    public void userPaymentSuccessInterface(String name) {
        sendToAdv("Amount of " + name + " paid successfully   ", fromName, namestr, isUser, false);
    }
}