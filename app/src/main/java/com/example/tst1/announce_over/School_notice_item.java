package com.example.tst1.announce_over;

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
import com.example.club.control.example.SchoolNoticeManager;
import com.example.club.model.Beanschoolnotice;

public class School_notice_item extends AppCompatActivity {

    private Handler handler=new Handler(){
        public void handleMessage(Message msg){

                Beanschoolnotice schoolnotice=(Beanschoolnotice) msg.obj;
                //公告图片
                ImageView notice_image=(ImageView)findViewById(R.id.image_notice_digital);
                //标题
                TextView notice_tittle=(TextView)findViewById(R.id.notice_title);
                //内容
                TextView notice_content=(TextView)findViewById(R.id.TextView_notice_digital_content);
                //发布时间
                TextView time=(TextView)findViewById(R.id.TextView_create_time2);

                //set
                notice_tittle.setText(schoolnotice.getSchoolnotice_title());
                notice_content.setText(schoolnotice.getSchoolnotice_content());
                time.setText(schoolnotice.getSchoolnotice_start_time().toString().substring(0,19));

                Bitmap bitmap1 = BitmapFactory.decodeByteArray(schoolnotice.getSchoolnotice_picture(), 0, schoolnotice.getSchoolnotice_picture().length);
                notice_image.setImageBitmap(bitmap1);

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Intent inte=getIntent();
        final int noticeid=inte.getIntExtra("id",-1);

        setContentView(R.layout.notice_digital);

        Button btnback=(Button)findViewById(R.id.button_notice_digital_exit);
        btnback.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(School_notice_item.this, School_Notice_List.class);
                startActivity(intent);
                School_notice_item.this.finish();
            }
        });
        Button delete=(Button)findViewById(R.id.notice_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                new Thread(){
                    @Override
                    public void run() {

                        SchoolNoticeManager a=new SchoolNoticeManager();
                        a.deleteNotice(noticeid);
                    }
                }.start();
                Intent intent = new Intent(School_notice_item.this, School_Notice_List.class);
                startActivity(intent);
                School_notice_item.this.finish();
                finish();
            }
        });

        new Thread(){
            @Override
            public void run() {
                SchoolNoticeManager a=new SchoolNoticeManager();
                Beanschoolnotice nt = a.selectNotice(noticeid);
                Message message=new Message();
                message.obj=nt;
                Bundle bundle = new Bundle();
                bundle.putString("name","1");  //往Bundle中存放数据
                message.setData(bundle);
                handler.sendMessage(message);
            }
        }.start();

    }
}
