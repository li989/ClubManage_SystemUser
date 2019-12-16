package com.example.tst1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.tst1.activity.activity;
import com.example.tst1.announce_over.School_Notice_List;
import com.example.tst1.award.award;
import com.example.tst1.club_add.club_audit_list;
import com.example.tst1.announce.announce;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personer);

        Button btn1=(Button)findViewById(R.id.per_btn1);
        Button btn2=(Button)findViewById(R.id.per_btn2);
        Button btn3=(Button)findViewById(R.id.per_btn3);
        Button btn4=(Button)findViewById(R.id.per_btn4);
        Button btn5=(Button)findViewById(R.id.per_btn5);

        btn1.setOnClickListener(new btn1Listener());
        btn2.setOnClickListener(new btn2Listener());
        btn3.setOnClickListener(new btn3Listener());
        btn4.setOnClickListener(new btn4Listener());
        btn5.setOnClickListener(new btn5Listener());


    }
    class btn1Listener implements View.OnClickListener{
        @Override
        public void onClick(View v){
            Intent intent = new Intent(MainActivity.this, announce.class);
            startActivity(intent);
        }
    }
    class btn2Listener implements View.OnClickListener{
        @Override
        public void onClick(View v){
            Intent intent = new Intent(MainActivity.this, activity.class);
            startActivity(intent);
        }
    }
    class btn3Listener implements View.OnClickListener{
        @Override
        public void onClick(View v){
            Intent intent = new Intent(MainActivity.this, award.class);
            startActivity(intent);
        }
    }
    class btn4Listener implements View.OnClickListener{
        @Override
        public void onClick(View v){
            Intent intent = new Intent(MainActivity.this, School_Notice_List.class);
            startActivity(intent);
        }
    }
    class btn5Listener implements View.OnClickListener{
        @Override
        public void onClick(View v){
            Intent intent = new Intent(MainActivity.this, club_audit_list.class);
            startActivity(intent);
        }
    }


}
