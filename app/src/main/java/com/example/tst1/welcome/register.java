package com.example.tst1.welcome;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tst1.R;

public class register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        Button btnexit=(Button)findViewById(R.id.register_exit);

        btnexit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();

            }
        });
        final EditText editText1=(EditText)findViewById(R.id.register_mima2);
        final EditText editText2=(EditText)findViewById(R.id.register_yuanmima2);
        final TextView error=(TextView)findViewById(R.id.register_error);
        editText2.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editText1.getText().toString().equals(editable.toString())){
                    error.setText("输入密码不一致");
                }else {
                    error.setText("");
                }
            }
        });

    }
}
