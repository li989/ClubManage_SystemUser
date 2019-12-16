package com.example.tst1.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adapter.ActivityAdapter;
import com.example.club.control.example.ActivityManager;
import com.example.club.model.Beanactivity_club;
import com.example.club.util.BaseException;
import com.example.tst1.MainActivity;
import com.example.tst1.R;

import java.util.ArrayList;
import java.util.List;

import static com.mysql.jdbc.AbandonedConnectionCleanupThread.shutdown;

public class activity extends AppCompatActivity {
    int activity_id;
    List<Beanactivity_club> activity=new ArrayList<Beanactivity_club>();
    public static void actionStart(Context context, Beanactivity_club activity1) {
        Intent intent = new Intent();
        intent.putExtra("id",activity1.getActivity_ID());
        intent.setClass(context, activity_info.class);
        context.startActivity(intent);
    }
    private Handler handler=new Handler(){
        public void handleMessage(Message msg){
            activity=(ArrayList<Beanactivity_club>)msg.obj;
            RecyclerView recyclerView=(RecyclerView)findViewById(R.id.act_recyclerview);
            LinearLayoutManager layoutManager=new LinearLayoutManager(activity.this);
            recyclerView.setLayoutManager(layoutManager);
            ActivityAdapter adapter=new ActivityAdapter(activity);
            recyclerView.setAdapter(adapter);}
    };
    private Handler mhandler=new Handler(){
        public void handleMessage(Message msg){
            String name=msg.getData().getString("name");
            if(name!=null){
                List<Beanactivity_club> activity1=new ArrayList<Beanactivity_club>();
                for (int i=0;i<activity.size();i++){
                    if (activity.get(i).getActivity_name().matches(".*?"+name+".*?")){
                        activity1.add(activity.get(i));
                    }
                }
                RecyclerView recyclerView=(RecyclerView)findViewById(R.id.act_recyclerview);
                LinearLayoutManager layoutManager=new LinearLayoutManager(activity.this);
                recyclerView.setLayoutManager(layoutManager);
                ActivityAdapter adapter=new ActivityAdapter(activity1);
                recyclerView.setAdapter(adapter);
            }
        }
    };
    protected void onCreate(Bundle savedInstanceState) {
        Intent it=getIntent();
        activity_id=it.getIntExtra("activity_ID",0);
        new Thread(){
            @Override
            public void run() {
                try {
                    List<Beanactivity_club> user=new ArrayList<Beanactivity_club>();
                    ActivityManager uc=new ActivityManager();
                    user=uc.loadallactivityclub();
                    Message message=new Message();
                    message.obj=user;
                    handler.sendMessage(message);
                    shutdown();
                } catch (BaseException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.acticity_list);
        Button btnexit=(Button)findViewById(R.id.back1);
        btnexit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(activity.this, MainActivity.class);
                startActivity(intent);
                activity.this.finish();
            }
        });
        final EditText editText = (EditText) findViewById(R.id.activity_app_select);
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Message message=new Message();
                Bundle bundle = new Bundle();
                bundle.putString("name",editText.getText().toString());
                message.setData(bundle);
                mhandler.sendMessage(message);
            }
        });
    }
    class BackListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(activity.this, MainActivity.class);
            startActivity(intent);
        }
    }
}
