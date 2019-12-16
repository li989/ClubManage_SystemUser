package com.example.tst1.club_add;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adapter.club_audit_adapter;
import com.example.tst1.MainActivity;
import com.example.tst1.R;
import com.example.club.control.example.ClubManager;
import com.example.club.model.Beanclub;
import com.example.club.util.BaseException;

import java.util.ArrayList;
import java.util.List;

import static com.mysql.jdbc.AbandonedConnectionCleanupThread.shutdown;

public class club_audit_list extends AppCompatActivity {

    private Button back;

    private List<Beanclub> mclubs=new ArrayList<Beanclub>();
    //
    public static void actionStart(Context context, Beanclub club) {
        Intent intent = new Intent();
        intent.setClass(context, club_audit_item.class);
        intent.putExtra("id",club.getClub_ID());
        context.startActivity(intent);
    }

    private Handler handler=new Handler(){
        public void handleMessage(Message msg){
            List<Beanclub> clubs=(ArrayList<Beanclub>)msg.obj;

            RecyclerView recyclerView=(RecyclerView)findViewById(R.id.club_audit_recyclerview);
            LinearLayoutManager layoutManager=new LinearLayoutManager(club_audit_list.this);
            recyclerView.setLayoutManager(layoutManager);
            club_audit_adapter adapter=new club_audit_adapter(clubs);
            recyclerView.setAdapter(adapter);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //隐藏ActionBar
        //getSupportActionBar().hide();
        //设置页面全屏
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.club_audit_list);
//        List<Beanclub> notices = new ArrayList<Beanclub>();
//
//        for (int i = 0; i < 4; i++) {
//            Beanclub cl = new Beanclub();
//            cl.setClub_ID(i);
//            cl.setClub_name("社团" + i);
//            cl.setClub_createtime(new java.sql.Timestamp(System.currentTimeMillis()));
//            notices.add(cl);
//        }
//        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.club_audit_recyclerview);

        // checkBox = (CheckBox) findViewById(R.id.cb_item);

        back = (Button) this.findViewById(R.id.club_audit_list_back);


//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
//        club_audit_adapter adapter = new club_audit_adapter(notices);
//        recyclerView.setAdapter(adapter);

        new Thread(){
            @Override
            public void run() {
                try {
                    ClubManager a=new ClubManager();
                    mclubs=a.loadclubtoaudit();
                    Message message=new Message();
                    message.obj=mclubs;
                    Bundle bundle = new Bundle();
                    bundle.putString("name","1");  //往Bundle中存放数据
                    message.setData(bundle);
                    handler.sendMessage(message);
                    shutdown();
                } catch (BaseException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        back.setOnClickListener(new BackListener());

    }
    //返回主页面
    class BackListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(club_audit_list.this, MainActivity.class);
            startActivity(intent);
            club_audit_list.this.finish();
        }
    }

}
