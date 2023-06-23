package com.example.lawsystem;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;


public class ChatAdaptertwo extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int LAYOUT_ONE = 0;
    private static final int LAYOUT_TWO = 1;
    private static final int LAYOUT_THREE = 2;
    Context context;
    ArrayList<ChatModel> list;

    public ChatAdaptertwo(Context context, ArrayList<ChatModel> list) {
        this.context = context;
        this.list = list;
    }
    @Override
    public int getItemViewType(int position)
    {
        if (list.get(position).isPayment()){
            return  LAYOUT_THREE;
        }
        else if (list.get(position).isUser()){
            return  LAYOUT_TWO;
        }
        else {
            return LAYOUT_ONE;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;
        RecyclerView.ViewHolder viewHolder = null;

        if(viewType==LAYOUT_ONE)
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one,parent,false);
            viewHolder = new ViewHolderOne(view);
        } else  if(viewType == LAYOUT_TWO){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.two,parent,false);
            viewHolder= new ViewHolderTwo(view);
        }
        else
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.three,parent,false);
            viewHolder= new ViewHolderThree(view);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if(holder.getItemViewType() == LAYOUT_ONE)
        {
            ViewHolderOne one = (ViewHolderOne) holder;
            one.name.setText(list.get(position).getMessage());
            if(list.get(position).isFile()){
                SpannableString content = new SpannableString(list.get(position).getMessage());
                content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                one.name.setText(content);

                one.name.setOnClickListener(v -> {
                    CustomTabsIntent.Builder customIntent = new CustomTabsIntent.Builder();

                    customIntent.setToolbarColor(ContextCompat.getColor(context, R.color.black));

                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(list.get(position).getMessage())));
                });
            }
        }
        else if (holder.getItemViewType() == LAYOUT_TWO){
            ViewHolderTwo one = (ViewHolderTwo) holder;
            one.name.setText(list.get(position).getMessage());
            if(list.get(position).isFile()){
                SpannableString content = new SpannableString(list.get(position).getMessage());
                content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                one.name.setText(content);

                one.name.setOnClickListener(v -> {
                    CustomTabsIntent.Builder customIntent = new CustomTabsIntent.Builder();

                    customIntent.setToolbarColor(ContextCompat.getColor(context, R.color.black));

                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(list.get(position).getMessage())));
                });
            }
        } else {
            ViewHolderThree three = (ViewHolderThree) holder;

           three.name.setText(list.get(position).getMessage());
            three.tv_student_title.setText("Request from You");
        }
    }

    //****************  VIEW HOLDER 1 ******************//

    public class ViewHolderOne extends RecyclerView.ViewHolder {

        public MaterialTextView name;

        public ViewHolderOne(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_student_msg);

        }
    }


    //****************  VIEW HOLDER 2 ******************//

    public class ViewHolderTwo extends RecyclerView.ViewHolder {

        public MaterialTextView name;
        public ViewHolderTwo(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvAgentNameChat);

        }
    }

    public class ViewHolderThree extends RecyclerView.ViewHolder {

        public MaterialTextView name, tv_student_title;
        public ViewHolderThree(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_student_msg);
            tv_student_title = itemView.findViewById(R.id.tv_student_title);

        }
    }
}



