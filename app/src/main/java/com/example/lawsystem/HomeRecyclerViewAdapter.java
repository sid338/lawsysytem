package com.example.lawsystem;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

import java.util.List;


class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.ViewHolder> {

    // Array of images
    // Adding images from drawable folder
    private final Context ctx;
    private final List<advocates> addresses;

    // Constructor of our ViewPager2Adapter class
    HomeRecyclerViewAdapter(Context ctx, List<advocates> addressesList) {
        this.ctx = ctx;
        this.addresses = addressesList;
    }

    // This method returns our layout
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.activity_product_layout, parent, false);
        return new ViewHolder(view);
    }

    // This method binds the screen with the view
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // This will set the images in imageview
        advocates product = addresses.get(position);

        holder.textViewname.setText(product.getName());
        //holder.textViewexperiance.setText(product.getExperiance());
     //   holder.textViewRating.setText(String.valueOf(product.getRating()));
      //  holder.textViewfee.setText(String.valueOf(product.getFee()));
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(ctx.getApplicationContext(), example.class);
                ctx.startActivity(i);
            }
        });
       // holder.imageView.setImageDrawable(ctx.getResources().getDrawable(product.getImage()));
    }

    // This Method returns the size of the Array
    @Override
    public int getItemCount() {
        return addresses.size();
    }

    // The ViewHolder class holds the view
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewname, textViewexperiance, textViewRating, textViewfee;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
       //     textViewname = itemView.findViewById(R.id.advname);
       //     textViewexperiance = itemView.findViewById(R.id.advexp);
       //     textViewRating = itemView.findViewById(R.id.advrating);
        //    textViewfee = itemView.findViewById(R.id.advfee);
         //   imageView = itemView.findViewById(R.id.imageView);
        }
    }
}