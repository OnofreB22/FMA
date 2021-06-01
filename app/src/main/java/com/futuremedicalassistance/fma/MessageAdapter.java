package com.futuremedicalassistance.fma;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    Context context;
    ArrayList<Message> list;

    FirebaseUser firebaseUser;

    public static final int messageTypeLeft = 0;
    public static final int messageTypeRight = 1;

    public MessageAdapter(Context context, ArrayList<Message> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == messageTypeRight){
            View view = LayoutInflater.from(context).inflate(R.layout.item_chat_right, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }else{
            View view = LayoutInflater.from(context).inflate(R.layout.item_chat_left, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {
        final Message message = list.get(position);
        holder.messageTextView.setText(message.getMessage());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position){
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (list.get(position).getSender().equals(firebaseUser.getUid())){
            return messageTypeRight;
        }else{
            return messageTypeLeft;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView messageTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            messageTextView = (TextView)itemView.findViewById(R.id.messageTextView);
        }
    }
}