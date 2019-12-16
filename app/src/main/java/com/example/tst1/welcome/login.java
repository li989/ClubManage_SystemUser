package com.example.tst1.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tst1.MainActivity;
import com.example.tst1.R;
import com.example.club.control.example.UserManager;
import com.example.club.model.Beanuser;


public class login extends AppCompatActivity {
    Beanuser loginuser;
    private Handler handler=new Handler(){
        public void handleMessage(Message msg){
            Beanuser user=(Beanuser)msg.obj;
            if(msg.getData().getBoolean("bool")){
                Intent intent=new Intent();
                intent.setClass(login.this, MainActivity.class);
                startActivity(intent);
            }else {
                Toast.makeText(login.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);



//        Button button1=(Button)findViewById(R.id.login_register);
//        button1.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v){
//               Intent intent=new Intent(login.this, register.class);
//               startActivity(intent);
//            }
//        });

        ImageView btn2=(ImageView)findViewById(R.id.login_enter);
        btn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                new Thread(){
                    @Override
                    public void run() {
                        UserManager user=new UserManager();
                        EditText edzh=(EditText)findViewById(R.id.welcome_EditText1);
                        EditText edpwd=(EditText)findViewById(R.id.welcome_EditText2);
                        loginuser=user.login(edzh.getText().toString(),edpwd.getText().toString());
                        Message message=new Message();
                        message.obj=loginuser;
                        Bundle bundle = new Bundle();
                        Beanuser.currentLoginUser=loginuser;
                        if(Beanuser.currentLoginUser==null)
                            bundle.putBoolean("bool",false );
                        else
                            bundle.putBoolean("bool",true );
                        message.setData(bundle);
                        handler.sendMessage(message);
                    }
                }.start();

            }
        });

    }
}
