package com.example.tst1.announce_over;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adapter.school_noticeAdapter;
import com.example.tst1.MainActivity;
import com.example.tst1.R;
import com.example.club.control.example.SchoolNoticeManager;
import com.example.club.model.Beanschoolnotice;
import com.example.club.util.BaseException;

import java.util.ArrayList;
import java.util.List;

public class School_Notice_List extends AppCompatActivity {

    int showCheckBox = 1;
    private Button back;


    private List<Beanschoolnotice> mnotices=new ArrayList<Beanschoolnotice>();
    //
    public static void actionStart(Context context,Beanschoolnotice notice) {
        Intent intent = new Intent();
        intent.setClass(context, School_notice_item.class);
        intent.putExtra("id",notice.getSchoolnotice_ID());
        context.startActivity(intent);
    }

    private Handler handler=new Handler(){
        public void handleMessage(Message msg){
            List<Beanschoolnotice> notices=(ArrayList<Beanschoolnotice>)msg.obj;

            RecyclerView recyclerView=(RecyclerView)findViewById(R.id.school_notice_recyclerview);
            LinearLayoutManager layoutManager=new LinearLayoutManager(School_Notice_List.this);
            recyclerView.setLayoutManager(layoutManager);
            school_noticeAdapter adapter=new school_noticeAdapter(notices);
            recyclerView.setAdapter(adapter);

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.school_notice_list);
//        List<Beanschoolnotice> notices = new ArrayList<Beanschoolnotice>();
//
//        for (int i = 0; i < 4; i++) {
//            Beanschoolnotice no = new Beanschoolnotice();
//            no.setClub_ID(i);
//            no.setSchoolnotice_title("足球大赛" + i);
//            no.setSchoolnotice_start_time(new java.sql.Timestamp(System.currentTimeMillis()));
//            notices.add(no);
//        }
//        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.school_notice_recyclerview);
//
//       // checkBox = (CheckBox) findViewById(R.id.cb_item);
//
       back = (Button) this.findViewById(R.id.school_notice_over_back);
//
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
//        school_noticeAdapter adapter = new school_noticeAdapter(notices);
//        recyclerView.setAdapter(adapter);

        new Thread(){
            @Override
            public void run() {
                try {
                    SchoolNoticeManager a=new SchoolNoticeManager();
                    mnotices=a.getallnotice();
                    Message message=new Message();
                    message.obj=mnotices;
                    Bundle bundle = new Bundle();
                    bundle.putString("name","1");  //往Bundle中存放数据
                    message.setData(bundle);
                    handler.sendMessage(message);
                } catch (BaseException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        /*
        Button btnsearch=(Button)findViewById(R.id.school_notice_search);
        btnsearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(school_notice.this, noticesearch.class);
                startActivity(intent);
            }
        });
        Button btnsearch1=(Button)findViewById(R.id.TextView_school_notice_release2);
        btnsearch1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(school_notice.this, noticesearch.class);
                startActivity(intent);
            }
        });
*/

        back.setOnClickListener(new BackListener());


    }
        //返回主页面
        class BackListener implements View.OnClickListener {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(School_Notice_List.this, MainActivity.class);
                startActivity(intent);
                School_Notice_List.this.finish();
            }
        }


}