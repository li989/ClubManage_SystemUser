package com.example.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tst1.R;
import com.example.tst1.activity.activity;
import com.example.club.model.Beanactivity_club;

import java.util.List;

import any.BitmapToRound_Util;

public class ActivityAdapter  extends RecyclerView.Adapter<ActivityAdapter.ViewHolder>{
    private List<Beanactivity_club> mBeanactivity;
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView personname;
        ImageView personImage;
        TextView persongrade;
        public ViewHolder(View view){
            super(view);
            personname=(TextView)view.findViewById(R.id.act_name);
            personImage=(ImageView) view.findViewById(R.id.act_image);
            persongrade=(TextView)view.findViewById(R.id.act_club);
        }
    }
    public ActivityAdapter(List<Beanactivity_club> activityList){
        mBeanactivity=activityList;

    }
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Beanactivity_club activity1=mBeanactivity.get(position);
        holder.personname.setText(activity1.getActivity_name());
        Bitmap bitmap1 = BitmapFactory.decodeByteArray(activity1.getActivity_picture(), 0, activity1.getActivity_picture().length);
        bitmap1=(new BitmapToRound_Util()).toRoundBitmap(bitmap1);
        holder.personImage.setImageBitmap(bitmap1);
        holder.persongrade.setText(activity1.getClub_name());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Beanactivity_club activity1=mBeanactivity.get(position);
                activity.actionStart(holder.itemView.getContext(),activity1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBeanactivity.size();
    }
}
