package com.futuremedicalassistance.fma;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GuideAdapter extends RecyclerView.Adapter<GuideAdapter.GuideViewHolder> {

    Context context;
    ArrayList<Guide> list;

    public GuideAdapter(Context context, ArrayList<Guide> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public GuideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_guide,parent,false);
        return new GuideViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull GuideAdapter.GuideViewHolder holder, int position) {
        Guide guide = list.get(position);
        holder.guideNameTextView.setText(guide.getGuideName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, GuideViewActivity.class);
                i.putExtra("guideId", guide.getGuideId());
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class GuideViewHolder extends RecyclerView.ViewHolder{

        TextView guideNameTextView;

        public GuideViewHolder(@NonNull View itemView) {
            super(itemView);

            guideNameTextView = (TextView)itemView.findViewById(R.id.guideNameTextView);
        }
    }
}