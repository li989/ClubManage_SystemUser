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
import com.example.club.model.Beanschoolnotice;
import com.example.tst1.announce_over.School_Notice_List;

import java.util.List;

import any.BitmapToRound_Util;

public class school_noticeAdapter extends RecyclerView.Adapter<school_noticeAdapter.ViewHolder> {

    private List<Beanschoolnotice> mBeanschoolnoticelist;
    /**
     * 控制是否显示Checkbox
     */
    private boolean showCheckBox;

    static class ViewHolder extends RecyclerView.ViewHolder{
        //勾选框
        private LinearLayout notice_item;
        TextView school_notice_title;
        ImageView school_notice_imgtx;
        TextView school_notice_time1;
        TextView school_notice_time2;
        public ViewHolder(View view){
            super(view);
            school_notice_title=(TextView)view.findViewById(R.id.school_notice_title);
            school_notice_imgtx=(ImageView) view.findViewById(R.id.school_notice_imgtx);
            school_notice_time1=(TextView)view.findViewById(R.id.school_notice_time1);
            school_notice_time2=(TextView)view.findViewById(R.id.school_notice_time2);
        }
    }

    public school_noticeAdapter(List<Beanschoolnotice> school_notice){
        mBeanschoolnoticelist=school_notice;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.school_notice_item,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder,final int position) {

        Beanschoolnotice notice=mBeanschoolnoticelist.get(position);

        holder.school_notice_title.setText(notice.getSchoolnotice_title());

        if(notice.getSchoolnotice_picture()!=null) {
            Bitmap bitmap1 = BitmapFactory.decodeByteArray(notice.getSchoolnotice_picture(), 0, notice.getSchoolnotice_picture().length);
            bitmap1=(new BitmapToRound_Util()).toRoundBitmap(bitmap1);
            holder.school_notice_imgtx.setImageBitmap(bitmap1);
        }else
            holder.school_notice_imgtx.setImageResource(R.drawable.head);

        holder.school_notice_time1.setText("发布时间");
        holder.school_notice_time2.setText(""+notice.getSchoolnotice_start_time());

        //点击item进入具体公告
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Beanschoolnotice notice=mBeanschoolnoticelist.get(position);
                School_Notice_List.actionStart(holder.itemView.getContext(),notice);
            }
        });

}

    public int getItemCount() {
        return mBeanschoolnoticelist.size();
    }
}
