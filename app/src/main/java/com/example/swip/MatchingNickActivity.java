package com.example.swip;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import javax.annotation.Nullable;

public class MatchingNickActivity extends AppCompatActivity {

    private EditText et_user;
    private Button btn_go;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("닉네임 입력하기");
        setContentView(R.layout.matching_nick);

        et_user = (EditText) findViewById(R.id.et_user);
        btn_go = (Button) findViewById(R.id.btn_go);

        btn_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_name = et_user.getText().toString();

                if("".equals(str_name)) {
                    Toast.makeText(MatchingNickActivity.this, "닉네임을 입력해 주세요", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(MatchingNickActivity.this, MatchingCreateActivity.class);
                    intent.putExtra("name", str_name);
                    startActivity(intent);
                }
            }
        });
    }
}
