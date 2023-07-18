package com.example.lawsystem;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.lawsystem.databinding.ActivityChatBinding;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ChatActivityTwo extends AppCompatActivity {

    DatabaseReference databaseReference;
    SharedPreferences sh;
    Uri imageuri = null;
    String name;
    String namestr;
    String isUser;
    private ActivityChatBinding binding;
    private ChatAdaptertwo adapter;
    private final ArrayList<ChatModel> chatModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sh = getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE); // to store data for temp time
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://lawsystem-49e23-default-rtdb.firebaseio.com//");

        Intent intent = getIntent();
        namestr = intent.getStringExtra("name");
        String contactstr = intent.getStringExtra("contact");
        isUser = intent.getStringExtra("user");
        binding.chat.setLayoutManager(new LinearLayoutManager(this));


        String fromNumber = sh.getString("number", "");
        adapter = new ChatAdaptertwo(this, chatModels);
        binding.chat.setAdapter(adapter);

       /* binding.constraint.setVisibility(View.VISIBLE);
        binding.constraint.setOnClickListener(v -> {
            Intent intent1 = new Intent(this, UserViewProfileActivity.class);
            intent1.putExtra("name", binding.tvProfileName.getText().toString());
            startActivity(intent1);
        });*/


        name = sh.getString("UserName", "");
        databaseReference.child("Chat").child(namestr).child(name).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatModels.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    ChatModel model = snapshot1.getValue(ChatModel.class);
                    model.setRead(true);
                    chatModels.add(model);
                }
                binding.chat.scrollToPosition(binding.chat.getAdapter().getItemCount()-1);
                adapter.notifyDataSetChanged();
                if (chatModels.size() > 0) {
                  //  binding.tvProfileName.setText(chatModels.get(0).getFromName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });


        adapter.notifyDataSetChanged();

        binding.imagebutton.setOnClickListener(v -> {
            String message = binding.text.getText().toString();

            if (!TextUtils.isEmpty(message)) {
                sendToUser(message, name, namestr, isUser, false, false);
            } else {
                Toast.makeText(this, "Enter a message", Toast.LENGTH_SHORT).show();
            }
        });

        binding.ivAttachment.setOnClickListener(v -> {
            showBottomSheetDialog(name, namestr, isUser);
        });
    }

    private void showBottomSheetDialog(String name, String namestr, String isUser) {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog);

        TextView tvPayNow = bottomSheetDialog.findViewById(R.id.tvPayNow);
        TextView tvDoc = bottomSheetDialog.findViewById(R.id.tvDoc);
        EditText etPay = bottomSheetDialog.findViewById(R.id.etPay);
        Button btnPay = bottomSheetDialog.findViewById(R.id.btnPay);
        tvPayNow.setOnClickListener(v -> {

            etPay.setVisibility(View.VISIBLE);
            btnPay.setVisibility(View.VISIBLE);

        });

        etPay.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                etPay.setError(null);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                etPay.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
                etPay.setError(null);
            }
        });

        btnPay.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(etPay.getText().toString())) {
                sendToUser(etPay.getText().toString(), name, namestr, isUser, true, false);
                bottomSheetDialog.dismiss();
            } else {
                etPay.setError("Required");
            }
        });

        tvDoc.setOnClickListener(v -> {
            Intent galleryIntent = new Intent();
            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);


            galleryIntent.setType("application/pdf");
            startActivityForResult(galleryIntent, 1);
        });


        bottomSheetDialog.show();
    }

    private void sendToUser(String message, String fromName, String namestr, String isUser, boolean isPayment, boolean isFile) {
        ChatModel chatModel = new ChatModel();
        chatModel.setMessage(message);
        chatModel.setFromName(fromName);
        chatModel.setToName(namestr);
        chatModel.setUser(false);
        chatModel.setRead(false);
        chatModel.setPayment(isPayment);
        chatModel.setFile(isFile);
        String pushKey;
        pushKey = databaseReference.push().getKey();
        chatModel.setKey(pushKey);
        databaseReference.child("Chat").child(namestr).child(fromName).child(pushKey).setValue(chatModel);
        binding.text.setText("");
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
            Toast.makeText(ChatActivityTwo.this, imageuri.toString(), Toast.LENGTH_SHORT).show();
            // Here we are uploading the pdf in firebase storage with the name of current time
            final StorageReference filepath = storageReference.child(messagePushID + "." + "pdf");
            Toast.makeText(ChatActivityTwo.this, filepath.getName(), Toast.LENGTH_SHORT).show();
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
                    Log.e("TAG", "onComplete: " + myurl);
                    Toast.makeText(ChatActivityTwo.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                    uploadFile(myurl);
                } else {
                    dialog.dismiss();
                    Toast.makeText(ChatActivityTwo.this, "UploadedFailed", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void uploadFile(String myUrl) {
        sendToUser(myUrl, name, namestr, isUser, false, true);
    }


}