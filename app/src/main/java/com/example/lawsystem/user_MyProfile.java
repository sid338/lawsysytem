package com.example.lawsystem;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link user_MyProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class user_MyProfile extends Fragment {

    TextView name,email,number;
//    ImageView imgview;
    String nameStr,emailStr,numberStr;
    DatabaseReference databaseReference;
    SharedPreferences sh;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public user_MyProfile() {

        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment user_MyProfile.
     */
    // TODO: Rename and change types and number of parameters
    public static user_MyProfile newInstance(String param1, String param2) {
        user_MyProfile fragment = new user_MyProfile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_user__my_profile, container, false);


        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://lawsystem-49e23-default-rtdb.firebaseio.com/");

        sh = requireContext().getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE); // to store data for temp time
        String s1 = sh.getString("Username", "");


        name=v.findViewById(R.id.name);
        email=v.findViewById(R.id.email);
        number=v.findViewById(R.id.number);
        databaseReference.child("Profile").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(s1)){
                    nameStr=snapshot.child(mParam2).child("Username").getValue(String.class);
                    emailStr=snapshot.child(mParam2).child("Email_Id").getValue(String.class);
                    numberStr=snapshot.child(mParam2).child("ContactNo").getValue(String.class);
                    name.setText(nameStr);
                    email.setText(emailStr);
                    number.setText(numberStr);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        updatepasswd = v.findViewById(R.id.updatePwd);
//        help = v.findViewById(R.id.Help);
//
//        userprofilename = v.findViewById(R.id.userprofileName);
//        userprofilename.setText(s1);

return v;
    }
}