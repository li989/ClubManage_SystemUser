package com.example.tst1.award;

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

import com.example.club.control.example.AwardsManager;
import com.example.club.model.Beanawards_club;
import com.example.club.util.BaseException;
import com.example.tst1.MainActivity;
import com.example.tst1.R;

import any.BitmapToRound_Util;

import static com.mysql.jdbc.AbandonedConnectionCleanupThread.shutdown;

public class award_info extends AppCompatActivity {

    private Handler handler=new Handler(){

        public void handleMessage(Message msg){
            try {
            Beanawards_club award=(Beanawards_club) msg.obj;
            ImageView info_h_head=(ImageView)findViewById(R.id.awards_picture);
            TextView per_t1=(TextView)findViewById(R.id.textView4);
            TextView per_t2=(TextView)findViewById(R.id.textView5);
            TextView per_t3=(TextView)findViewById(R.id.textView6);
            EditText per_t4=(EditText)findViewById(R.id.edit_userid13);
            per_t1.setText(award.getAwards_name());
            per_t2.setText(award.getClub_name());
            per_t3.setText(""+new java.sql.Date(award.getAwards_time().getTime()));

            if(award.getAwards_picture()!=null){
                Bitmap bitmap2 = BitmapFactory.decodeByteArray(award.getAwards_picture(), 0, award.getAwards_picture().length);
                bitmap2=(new BitmapToRound_Util()).toRoundBitmap(bitmap2);
                info_h_head.setImageBitmap(bitmap2);
            }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.award_info);
        Intent it=getIntent();
        final int award_id=it.getIntExtra("aid",0);
        Log.e("nnkk",""+award_id);
        final EditText per_t4=(EditText)findViewById(R.id.edit_userid13);
        Button btnexit=(Button)findViewById(R.id.back4);
        btnexit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(award_info.this, award.class);
                startActivity(intent);
            }
        });
        new Thread(){
            @Override
            public void run() {
                try {
                    Beanawards_club award=new Beanawards_club();
                    AwardsManager uc=new AwardsManager();
                    award=uc.selectawardsbyid(award_id);
                    Message message=new Message();
                    message.obj=award;
                    handler.sendMessage(message);
                    shutdown();
                } catch (BaseException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        Button button1=(Button)findViewById(R.id.Agree3);
        Button button2=(Button)findViewById(R.id.button15);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            AwardsManager award = new AwardsManager();
                            award.modifyawards1(Integer.parseInt(per_t4.getText().toString()),award_id,"已通过");
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
                            AwardsManager award = new AwardsManager();
                            award.modifyawards1(0,award_id,"未通过");
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