package com.example.tst1.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

//import com.example.club.control.example.activityManager;
import com.example.club.control.example.ActivityManager;
import com.example.club.model.Beanactivity_club;
import com.example.club.util.BaseException;
import com.example.tst1.R;

import any.BitmapToRound_Util;

import static com.mysql.jdbc.AbandonedConnectionCleanupThread.shutdown;

public class activity_info extends AppCompatActivity {
    private Handler handler=new Handler(){
        public void handleMessage(Message msg){
            Beanactivity_club activity=(Beanactivity_club) msg.obj;
            ImageView info_h_head=(ImageView)findViewById(R.id.activity_picture);
            TextView per_t1=(TextView)findViewById(R.id.textView7);
            TextView per_t2=(TextView)findViewById(R.id.textView8);
            TextView per_t3=(TextView)findViewById(R.id.textView9);
            TextView per_t4=(TextView)findViewById(R.id.textView10);
            TextView per_t5=(TextView)findViewById(R.id.textView11);

            TextView per_t7=(TextView)findViewById(R.id.textView13);
            per_t1.setText(activity.getActivity_name());
            per_t2.setText(activity.getClub_name());
            per_t3.setText(activity.getPlace_name());
            per_t4.setText(""+new java.sql.Date(activity.getActivity_start_time().getTime()));
            per_t5.setText(""+new java.sql.Date(activity.getActivity_finish_time().getTime()));
            per_t7.setText(activity.getActivity_centent());
            if(activity.getActivity_picture()!=null){
                Bitmap bitmap1 = BitmapFactory.decodeByteArray(activity.getActivity_picture(), 0, activity.getActivity_picture().length);
                bitmap1=(new BitmapToRound_Util()).toRoundBitmap(bitmap1);
                info_h_head.setImageBitmap(bitmap1);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        Intent it=getIntent();
        final int activity_id=it.getIntExtra("id",0);
        Log.e("nnkk",""+activity_id);
        final EditText per_t6=(EditText)findViewById(R.id.edit_userid12);
        Button btnexit=(Button)findViewById(R.id.back3);
        btnexit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(activity_info.this, activity.class);
                startActivity(intent);
            }
        });
        new Thread(){
            @Override
            public void run() {
                try {
                    Beanactivity_club activity=new Beanactivity_club();
                    ActivityManager uc=new ActivityManager();
                    activity=uc.selectactivitybyid(activity_id);
                    Message message=new Message();
                    message.obj=activity;
                    handler.sendMessage(message);
                    shutdown();
                } catch (BaseException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        Button button1=(Button)findViewById(R.id.Agree2);
        Button button2=(Button)findViewById(R.id.button10);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            ActivityManager activity = new ActivityManager();
                            activity.modifyactivity1(Integer.parseInt(per_t6.getText().toString()),activity_id,"已通过");
                            finish();
                            shutdown();
                        } catch (BaseException | InterruptedException e) {
                            e.printStackTrace();
                        }
                        finish();
                    }
                }.start();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            ActivityManager activity = new ActivityManager();
                            activity.modifyactivity1(0,activity_id,"未通过");
                            finish();
                            shutdown();
                        } catch (BaseException | InterruptedException e) {
                            e.printStackTrace();
                        }
                        finish();
                    }
                }.start();
            }
        });
    }
}
