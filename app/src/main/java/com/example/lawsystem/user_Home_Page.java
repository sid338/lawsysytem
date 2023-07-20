package com.example.lawsystem;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link user_Home_Page#newInstance} factory method to
 * create an instance of this fragment.
 */
public class user_Home_Page extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ImageView adv, police, file, crime;
    CardView card1, card2, card3, card4, card5;
    ImageSlider imageSlider;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public user_Home_Page() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment user_Home_Page.
     */
    // TODO: Rename and change types and number of parameters
    public static user_Home_Page newInstance(String param1, String param2) {
        user_Home_Page fragment = new user_Home_Page();
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

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_user__home__page, container, false);

        imageSlider = v.findViewById(R.id.imageSlider);
        ArrayList<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.banner, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.banner, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.banner, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.banner, ScaleTypes.FIT));

        imageSlider.setImageList(slideModels, ScaleTypes.FIT);

        adv = v.findViewById(R.id.image_view1);
        police = v.findViewById(R.id.image_view2);
        file = v.findViewById(R.id.image_view3);
        // crime=v.findViewById(R.id.image_view4);
        card1 = v.findViewById(R.id.card1);
        card2 = v.findViewById(R.id.card2);
        card3 = v.findViewById(R.id.card3);
        card4 = v.findViewById(R.id.card4);
        card5 = v.findViewById(R.id.card5);

        card5.setOnClickListener(v1 -> {
            Intent i = new Intent(getContext(), Feedback.class);
            i.putExtra("activity", "user");
            startActivity(i);
        });
        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), recyc.class);
                startActivity(i);
            }
        });
        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), UserReport.class);
                startActivity(i);
            }
        });


        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), casefile.class);
                startActivity(i);
            }
        });


        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), policedetails.class);
                startActivity(i);
            }
        });
        return v;
    }
}