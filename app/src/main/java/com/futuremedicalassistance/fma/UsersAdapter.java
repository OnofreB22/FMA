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

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersViewHolder> {

    private Context context;
    private List<Users> list;

    public UsersAdapter(Context context, List<Users> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_users,parent,false);
        return new UsersViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersAdapter.UsersViewHolder holder, int position) {
        Users users = list.get(position);
        holder.nameTextView.setText(users.getName());
        holder.lastNameTextView.setText(users.getLastName());

        if(users.getImageURL().equals("default")){
            holder.imageView.setImageResource(R.mipmap.ic_launcher);
        }else{
            Glide.with(context).load(users.getImageURL()).into(holder.imageView);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, MessageActivity.class);
                i.putExtra("userId", users.getId());
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class UsersViewHolder extends RecyclerView.ViewHolder{

        TextView nameTextView, lastNameTextView;
        ImageView imageView;

        public UsersViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTextView = (TextView)itemView.findViewById(R.id.nameTextView);
            lastNameTextView = (TextView)itemView.findViewById(R.id.lastNameTextView);
            imageView = (ImageView)itemView.findViewById(R.id.imageView);
        }
    }
}