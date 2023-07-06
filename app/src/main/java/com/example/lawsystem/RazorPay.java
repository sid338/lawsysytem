package com.example.lawsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lawsystem.databinding.ActivityRazorPayBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

public class RazorPay extends Activity implements PaymentResultListener {


    String price = "";
    private ActivityRazorPayBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRazorPayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        price = getIntent().getStringExtra("amount");

        binding.amt.setText(price);

        binding.idBtnPay.setOnClickListener(v -> {
            // rounding off the amount.
            int amount = Math.round(Float.parseFloat(price) * 100);

            // initialize Razorpay account.
            Checkout checkout = new Checkout();

            // set your id as below
            checkout.setKeyID("rzp_test_04poRZd72nQ9eN");

            // set image
            checkout.setImage(R.drawable.lawsys);

            // initialize json object
            JSONObject object = new JSONObject();
            try {
                // to put name
                object.put("name", "LAW SYSTEM");

                // put description
                object.put("description", "Test payment");

                // to set theme color
                object.put("theme.color", "");

                // put the currency
                object.put("currency", "INR");
                // put amount
                object.put("amount", amount);

                // put mobile number
                object.put("prefill.contact", "9072233806");

                // put email
                object.put("prefill.email", "hitheshkbimal2000@gmail.com");

                // open razorpay to checkout activity
                checkout.open(RazorPay.this, object);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    @Override
    public void onPaymentSuccess(String s) {
      //  super.onBackPressed();
        Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
        ChatActivity.userChatInterface.userPaymentSuccessInterface(price);
        super.onBackPressed();

    }

    @Override
    public void onPaymentError(int i, String s) {
        // on payment failed.
        Toast.makeText(this, "Payment Failed due to error : " + s, Toast.LENGTH_SHORT).show();
    }

}
