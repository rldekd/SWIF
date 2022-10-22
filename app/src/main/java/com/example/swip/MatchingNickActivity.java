package com.example.swip;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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

        /* 하단바 */

        /* 하단바 - 공지사항 */
        ImageButton nav_menu = (ImageButton) findViewById(R.id.nav_menu);
        nav_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NoticeActivity.class);
                startActivity(intent);
            }
        });


        /* 하단바 - 타이머 */
        ImageButton nav_timer = (ImageButton) findViewById(R.id.nav_timer);
        nav_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TimerActivity.class);
                startActivity(intent);
            }
        });


        /* 하단바 - 홈 */
        ImageButton nav_home = (ImageButton) findViewById(R.id.nav_home);
        nav_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });


        /* 하단바 - 게시판 */
        ImageButton nav_post = (ImageButton) findViewById(R.id.nav_post);
        nav_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), InformationBoardActivity.class);
                startActivity(intent);
            }
        });



        /* 하단바 - 마이페이지 */
        ImageButton nav_friend = (ImageButton) findViewById(R.id.nav_friend);
        nav_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyPageActivity.class);
                startActivity(intent);
            }
        });
    }
}
