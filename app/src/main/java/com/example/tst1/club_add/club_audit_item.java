package com.example.tst1.club_add;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tst1.R;
import com.example.club.control.example.ClubManager;
import com.example.club.model.Beanclub;
import com.example.club.util.BaseException;

public class club_audit_item extends AppCompatActivity {

    private Handler handler=new Handler(){
        public void handleMessage(Message msg){

            Beanclub club=(Beanclub) msg.obj;
            //公告图片
            ImageView club_image=(ImageView)findViewById(R.id.club_digital_picture);
            //社团名称
            TextView club_name=(TextView)findViewById(R.id.club_digital_name);
            //社长编号

            //申请时间
            TextView time=(TextView)findViewById(R.id.club_digital_time);
            //社团简介
            TextView content=(TextView)findViewById(R.id.club_digital_content);
            //set
            club_name.setText(club.getClub_name());
            time.setText(club.getClub_createtime().toString().substring(0,10));

            content.setText(club.getClub_remark());

            Bitmap bitmap1 = BitmapFactory.decodeByteArray(club.getClub_picture(), 0, club.getClub_picture().length);
            club_image.setImageBitmap(bitmap1);

            /*//另一个Handler传入
            Handler handler1=new Handler(){
                public void handleMessage(Message msg){

                    String name = (String)msg.obj;
                    clubpro_name.setText(name);

                }};*/
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        Intent inte=getIntent();
        final int clubid=inte.getIntExtra("id",-1);

        setContentView(R.layout.club_digital);

        //返回列表
        Button btnback=(Button)findViewById(R.id.club_digital_back);
        btnback.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(club_audit_item.this, club_audit_list.class);
                startActivity(intent);
                club_audit_item.this.finish();
            }
        });
        //同意
        Button agree=(Button)findViewById(R.id.club_agree);
        agree.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new Thread(){
                    @Override
                    public void run() {
                        ClubManager a=new ClubManager();
                        a.agreeClub(clubid);
                    }
                }.start();
                Intent intent = new Intent(club_audit_item.this, club_audit_list.class);
                startActivity(intent);
                club_audit_item.this.finish();
                finish();
            }
        });
        //不同意
        Button disagree=(Button)findViewById(R.id.club_disagree);
        disagree.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            new Thread(){
                @Override
                public void run() {
                    ClubManager a=new ClubManager();
                    a.disagreeClub(clubid);
                }
            }.start();
            Intent intent = new Intent(club_audit_item.this, club_audit_list.class);
            startActivity(intent);
        }
        });

        //返回社团信息
        new Thread(){
            @Override
            public void run() {

                try {
                    ClubManager a=new ClubManager();
                    Beanclub nt = null;
                    nt = a.selectClubid(clubid);
                    Message message=new Message();
                    message.obj=nt;
                    Bundle bundle = new Bundle();
                    bundle.putString("name","1");  //往Bundle中存放数据
                    message.setData(bundle);
                    handler.sendMessage(message);
                } catch (BaseException e) {
                    e.printStackTrace();
                }

            }
        }.start();
        //返回社长名称
       /*new Thread(){
            @Override
            public void run() {
                ClubManager a=new ClubManager();
                Beanclub nt = a.selectClub(clubid);
                Message message=new Message();
                message.obj=nt;
                Bundle bundle = new Bundle();
                bundle.putString("name","1");  //往Bundle中存放数据
                message.setData(bundle);
                handler.handler1.sendMessage(message);
            }
        }.start();*/

    }
}
