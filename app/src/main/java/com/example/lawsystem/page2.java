package com.example.lawsystem;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class page2 extends AppCompatActivity {
    String fullname, emailid, contactno, username, password, confirmpassword;

    TextInputEditText textInputEditText1, textInputEditText2, textInputEditText3, textInputEditText4, textInputEditText5, textInputEditText6;

    Button signUp, login;

    DatabaseReference databaseReference;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page2);

        signUp = (Button) findViewById(R.id.signup);
        login = (Button) findViewById(R.id.login);
        textInputEditText1 = (TextInputEditText) findViewById(R.id.name);
        textInputEditText2 = (TextInputEditText) findViewById(R.id.email);
        textInputEditText3 = (TextInputEditText) findViewById(R.id.mob);
        textInputEditText4 = (TextInputEditText) findViewById(R.id.username);
        textInputEditText5 = (TextInputEditText) findViewById(R.id.password);
        textInputEditText6 = (TextInputEditText) findViewById(R.id.cpass);

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://lawsystem-49e23-default-rtdb.firebaseio.com/");
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                fullname = textInputEditText1.getText().toString();
                emailid = textInputEditText2.getText().toString();
                contactno = textInputEditText3.getText().toString();
                username = textInputEditText4.getText().toString();
                password = textInputEditText5.getText().toString();
                confirmpassword = textInputEditText6.getText().toString();

                if (TextUtils.isEmpty(fullname) || emailid.isEmpty() || contactno.isEmpty() || username.isEmpty() || password.isEmpty() || confirmpassword.isEmpty()) {

                    Toast.makeText(page2.this, "Please enter all details", Toast.LENGTH_SHORT).show();
                } else if (password.equals(confirmpassword)) {
                    if (Patterns.EMAIL_ADDRESS.matcher(emailid).matches()) {
                        if (password.length() < 8 || confirmpassword.length() < 8) {
                            Toast.makeText(page2.this, "Minimum length of password is 8", Toast.LENGTH_SHORT).show();
                        } else {
                            if (contactno.length() < 10) {
                                Toast.makeText(page2.this, "Enter a valid mobile number", Toast.LENGTH_SHORT).show();
                            } else {
                                databaseReference.child("Profile").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.hasChild(username)) {
                                            Toast.makeText(page2.this, "Already existing User", Toast.LENGTH_SHORT).show();
                                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(i);
                                        } else {
                                            databaseReference.child("Profile").child(username).child("Username").setValue(username);
                                            databaseReference.child("Profile").child(username).child("Name").setValue(fullname);
                                            databaseReference.child("Profile").child(username).child("ContactNo").setValue(contactno);
                                            databaseReference.child("Profile").child(username).child("Email_Id").setValue(emailid);
                                            databaseReference.child("Profile").child(username).child("Password").setValue(password);
                                            Toast.makeText(page2.this, "User Successfully Registered", Toast.LENGTH_SHORT).show();
                                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(i);
                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Toast.makeText(page2.this, "error" + error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        }
                    }
                } else {
                    Toast.makeText(page2.this, "Password doesn't match", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
