package com.example.lawsystem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class govtregistration extends AppCompatActivity implements uriInterface{

    String occupation,fullname,emailid,contactno,username,password,confirmpassword;

    TextInputEditText   textInputEditText0,textInputEditText1,textInputEditText2,textInputEditText3,textInputEditText4,textInputEditText5,textInputEditText6;

    Button signUp,verify,data;

    DatabaseReference databaseReference;
    static uriInterface uriInterface;
    private String fileUploadUrl;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_govtregistration);
        uriInterface = this;

        signUp=(Button) findViewById(R.id.signup);
        verify=(Button) findViewById(R.id.verify);
        data=(Button)findViewById(R.id.data);
        textInputEditText0=(TextInputEditText) findViewById(R.id.occupation);
        textInputEditText1=(TextInputEditText) findViewById(R.id.name);
        textInputEditText2=(TextInputEditText)findViewById(R.id.email);
        textInputEditText3=(TextInputEditText)findViewById(R.id.mob);
        textInputEditText4=(TextInputEditText)findViewById(R.id.username);
        textInputEditText5=(TextInputEditText)findViewById(R.id.password);
        textInputEditText6=(TextInputEditText)findViewById(R.id.cpass);

        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://lawsystem-49e23-default-rtdb.firebaseio.com/");

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
            }
        });
        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),adddata.class);
                startActivity(i);
            }
        });

        verify.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                occupation=textInputEditText0.getText().toString();
                fullname = textInputEditText1.getText().toString();
                emailid = textInputEditText2.getText().toString();
                contactno = textInputEditText3.getText().toString();
                username = textInputEditText4.getText().toString();
                password = textInputEditText5.getText().toString();
                confirmpassword = textInputEditText6.getText().toString();


                if ( TextUtils.isEmpty(occupation) || TextUtils.isEmpty(fullname) || emailid.isEmpty() || contactno.isEmpty() || username.isEmpty() || password.isEmpty() || confirmpassword.isEmpty()  ) {

                    Toast.makeText(govtregistration.this, "Please enter all details", Toast.LENGTH_SHORT).show();
                } else if (password.equals(confirmpassword)) {
                    databaseReference.child("request").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(username)) {
                                Toast.makeText(govtregistration.this, "Already existing User", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(i);
                            } else {
                                databaseReference.child("request").child(contactno).child("Occupation").setValue(occupation);
                                databaseReference.child("request").child(contactno).child("Username").setValue(username);
                                databaseReference.child("request").child(contactno).child("Name").setValue(fullname);
                                databaseReference.child("request").child(contactno).child("ContactNo").setValue(contactno);
                                databaseReference.child("request").child(contactno).child("Email_Id").setValue(emailid);
                                databaseReference.child("request").child(contactno).child("Password").setValue(password);
                                databaseReference.child("request").child(contactno).child("document").setValue(fileUploadUrl);
                                Toast.makeText(govtregistration.this, "", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(), notification.class);
                                startActivity(i);
                            }

                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(govtregistration.this, "error" + error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(govtregistration.this, "Password doesn't match", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void getUrl(String url) {
        fileUploadUrl = url;
    }
}
