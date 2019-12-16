package com.example.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.example.club.model.Beanawards_club;
import com.example.tst1.R;
import com.example.tst1.award.award;

import java.util.List;

import any.BitmapToRound_Util;

public class Award_adapter extends RecyclerView.Adapter<Award_adapter.ViewHolder> {
    private List<Beanawards_club> mBeanawards;
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView personname;
        ImageView personImage;
        TextView persongrade;
        public ViewHolder(View view){
            super(view);
            personname=(TextView)view.findViewById(R.id.award_name);
            personImage=(ImageView) view.findViewById(R.id.award_image);
            persongrade=(TextView)view.findViewById(R.id.award_time);
        }
    }
    public Award_adapter(List<Beanawards_club> awardsList){
        mBeanawards=awardsList;

    }
    public Award_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.award_item,parent,false);
        Award_adapter.ViewHolder holder=new Award_adapter.ViewHolder(view);
        return holder;
    }


    public void onBindViewHolder(final Award_adapter.ViewHolder holder, int position) {
        Beanawards_club awards1=mBeanawards.get(position);
        holder.personname.setText(awards1.getAwards_name());
        Bitmap bitmap1 = BitmapFactory.decodeByteArray(awards1.getAwards_picture(), 0, awards1.getAwards_picture().length);
        bitmap1=(new BitmapToRound_Util()).toRoundBitmap(bitmap1);
        holder.personImage.setImageBitmap(bitmap1);
        holder.persongrade.setText(awards1.getClub_name());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Beanawards_club awards1=mBeanawards.get(position);
                award.actionStart(holder.itemView.getContext(),awards1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBeanawards.size();
    }
}
