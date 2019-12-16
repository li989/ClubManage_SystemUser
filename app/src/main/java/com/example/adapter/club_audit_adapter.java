package com.example.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tst1.R;
import com.example.club.model.Beanclub;
import com.example.tst1.club_add.club_audit_list;

import java.util.List;

import any.BitmapToRound_Util;

public class club_audit_adapter  extends RecyclerView.Adapter<club_audit_adapter.ViewHolder> {

    private List<Beanclub> mBeanclublist;

    static class ViewHolder extends RecyclerView.ViewHolder{
        //勾选框
        private LinearLayout club_item;
        TextView club_name;
        ImageView club_pic;
        TextView clubpro_name;
        TextView club_time;
        public ViewHolder(View view){
            super(view);
           club_name=(TextView)view.findViewById(R.id.club_name);
           club_pic=(ImageView) view.findViewById(R.id.club_imgtx);
           clubpro_name=(TextView)view.findViewById(R.id.clubpro_name);
            club_time=(TextView)view.findViewById(R.id.club_time);
        }
    }

    public club_audit_adapter(List<Beanclub> clubs){
        mBeanclublist=clubs;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.club_audit_item,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }


    public void onBindViewHolder(final ViewHolder holder,final int position) {

        Beanclub club=mBeanclublist.get(position);

        holder.club_name.setText(club.getClub_name());

        if(club.getClub_picture()!=null) {
            Bitmap bitmap1 = BitmapFactory.decodeByteArray(club.getClub_picture(), 0, club.getClub_picture().length);
            bitmap1=(new BitmapToRound_Util()).toRoundBitmap(bitmap1);
            holder.club_pic.setImageBitmap(bitmap1);
        }else
            holder.club_pic.setImageResource(R.drawable.head);

        //holder.clubroname.setText(""+club.getClub_createtime());

        holder.club_time.setText(""+club.getClub_createtime());

        //点击item进入具体公告
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Beanclub club=mBeanclublist.get(position);
               club_audit_list.actionStart(holder.itemView.getContext(),club);
            }
        });

    }

    public int getItemCount() {
        return mBeanclublist.size();
    }
}
