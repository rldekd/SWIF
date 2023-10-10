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

                if("".equals(str_name)) { //닉네임을 입력하지 않았다면 토스트메세지를 띄운다
                    Toast.makeText(MatchingNickActivity.this, "닉네임을 입력해 주세요", Toast.LENGTH_SHORT).show();
                } else { //닉네임을 옳게 입력했다면 name에 저장한다.
                    Intent intent = new Intent(MatchingNickActivity.this, MatchingCreateActivity.class);
                    intent.putExtra("name", str_name);
                    startActivity(intent);
                }
            }
        });

        /* 하단바 */

        /* 하단바 - 공지사항 */
        ImageButton nav_menu = (ImageButton) findViewById(R.id.nav_menu); // 리소스 아이디 파일에서 nav_menu 아이디를 찾아 이미지 버튼에 할당
        nav_menu.setOnClickListener(new View.OnClickListener() { // nav_menu를 클릭할 시 setOnClickListener 이벤트 발생
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NoticeActivity.class); // intent를 사용해 NoticeActivity.class를 불러옴. getApplicationContext()를 사용해 앱이 종료될 때까지 이 엑티비티는 살아있게 됨.
                startActivity(intent); // intent를 사용해 NoticeActivity를 호출하는 작업을 요청함
            }
        }); // 종료


        /* 하단바 - 타이머 */
        ImageButton nav_timer = (ImageButton) findViewById(R.id.nav_timer); // 리소스 아이디 파일에서 nav_timer 아이디를 찾아 이미지 버튼에 할당
        nav_timer.setOnClickListener(new View.OnClickListener() { // nav_timer를 클릭할 시 setOnClickListener 이벤트 발생
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TimerActivity.class); // intent를 사용해 TimerActivity.class를 불러옴. getApplicationContext()를 사용해 앱이 종료될 때까지 이 엑티비티는 살아있게 됨.
                startActivity(intent); // intent를 사용해 TimerActivity를 호출하는 작업을 요청함
            }
        }); // 종료


        /* 하단바 - 홈 */
        ImageButton nav_home = (ImageButton) findViewById(R.id.nav_home); // 리소스 아이디 파일에서 nav_home 아이디를 찾아 이미지 버튼에 할당
        nav_home.setOnClickListener(new View.OnClickListener() {  // nav_home을 클릭할 시 setOnClickListener 이벤트 발생
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class); // intent를 사용해 MainActivity.class를 불러옴. getApplicationContext()를 사용해 앱이 종료될 때까지 이 엑티비티는 살아있게 됨.
                startActivity(intent); // intent를 사용해 MainActivity를 호출하는 작업을 요청함
            }
        }); // 종료


        /* 하단바 - 게시판 */
        ImageButton nav_post = (ImageButton) findViewById(R.id.nav_post); // 리소스 아이디 파일에서 nav_post 아이디를 찾아 이미지 버튼에 할당
        nav_post.setOnClickListener(new View.OnClickListener() {  // nav_post를 클릭할 시 setOnClickListener 이벤트 발생
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), InformationBoardActivity.class); // intent를 사용해 InformationBoardActivity.class를 불러옴. getApplicationContext()를 사용해 앱이 종료될 때까지 이 엑티비티는 살아있게 됨.
                startActivity(intent); // intent를 사용해 InformationBoardActivity를 호출하는 작업을 요청함
            }
        }); // 종료



        /* 하단바 - 마이페이지 */
        ImageButton nav_friend = (ImageButton) findViewById(R.id.nav_friend); // 리소스 아이디 파일에서 nav_friend 아이디를 찾아 이미지 버튼에 할당
        nav_friend.setOnClickListener(new View.OnClickListener() {  // nav_friend를 클릭할 시 setOnClickListener 이벤트 발생
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyPageActivity.class); // intent를 사용해 MyPageActivity.class를 불러옴. getApplicationContext()를 사용해 앱이 종료될 때까지 이 엑티비티는 살아있게 됨.
                startActivity(intent); // intent를 사용해 MyPageActivity를 호출하는 작업을 요청함
            }
        }); // 종료
    }
}
