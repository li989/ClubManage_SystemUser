package com.example.tst1.award;

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

import com.example.adapter.Award_adapter;
import com.example.club.control.example.AwardsManager;
import com.example.club.model.Beanawards_club;
import com.example.club.util.BaseException;
import com.example.tst1.MainActivity;
import com.example.tst1.R;

import java.util.ArrayList;
import java.util.List;

import static com.mysql.jdbc.AbandonedConnectionCleanupThread.shutdown;

//import com.example.adapter.ActivityAdapter;

public class award extends AppCompatActivity {

    int award_id;
    List<Beanawards_club> award=new ArrayList<Beanawards_club>();
    public static void actionStart(Context context, Beanawards_club award1) {
        Intent intent = new Intent();
        intent.putExtra("aid",award1.getAwards_ID());
        intent.setClass(context, award_info.class);
        context.startActivity(intent);
    }
    private Handler handler=new Handler(){
        public void handleMessage(Message msg){
            award=(ArrayList<Beanawards_club>)msg.obj;
            RecyclerView recyclerView=(RecyclerView)findViewById(R.id.award_recyclerview);
            LinearLayoutManager layoutManager=new LinearLayoutManager(award.this);
            recyclerView.setLayoutManager(layoutManager);
            Award_adapter adapter=new Award_adapter(award);
            recyclerView.setAdapter(adapter);}
    };
    private Handler mhandler=new Handler(){
        public void handleMessage(Message msg){
            String name=msg.getData().getString("name");
            if(name!=null){
                List<Beanawards_club> award1=new ArrayList<Beanawards_club>();
                for (int i=0;i<award.size();i++){
                    if (award.get(i).getAwards_name().matches(".*?"+name+".*?")){
                        award1.add(award.get(i));
                    }
                }
                RecyclerView recyclerView=(RecyclerView)findViewById(R.id.award_recyclerview);
                LinearLayoutManager layoutManager=new LinearLayoutManager(award.this);
                recyclerView.setLayoutManager(layoutManager);
                Award_adapter adapter=new Award_adapter(award1);
                recyclerView.setAdapter(adapter);
            }
        }
    };
    protected void onCreate(Bundle savedInstanceState) {
        Intent it=getIntent();
        award_id=it.getIntExtra("award_ID",0);
        new Thread(){
            @Override
            public void run() {
                try {
                    List<Beanawards_club> user=new ArrayList<Beanawards_club>();
                    AwardsManager uc=new AwardsManager();
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
        setContentView(R.layout.award_list);
        Button btnexit=(Button)findViewById(R.id.back3);
        btnexit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(award.this, MainActivity.class);
                startActivity(intent);
                award.this.finish();
            }
        });
        final EditText editText = (EditText) findViewById(R.id.activity_award_select);
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
}