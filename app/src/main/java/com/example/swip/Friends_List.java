package com.example.swip;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class Friends_List extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends_list)
        ;

        Button friend_manage_btn = (Button) findViewById(R.id.friend_manage_btn);
        friend_manage_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Friends_Manage.class);
                startActivity(intent);
            }
        });

        Button friends_list_btn = (Button) findViewById(R.id.friends_list_btn);
        friends_list_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Friends_List.class);
                startActivity(intent);
            }
        });

        Button friends_chat_btn = (Button) findViewById(R.id.friends_chat_btn);
        friends_chat_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FriendChatActivity.class);
                startActivity(intent);
            }
        });

        Button friends_apply_btn = (Button) findViewById(R.id.friends_apply_btn);
        friends_apply_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Friends_Apply.class);
                startActivity(intent);
            }
        });

        /* 하단바 */

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
                Intent intent = new Intent(getApplicationContext(),information_board.class);
                startActivity(intent);
            }
        });

        /* 하단바 - 메뉴 */
        ImageButton nav_menu = (ImageButton) findViewById(R.id.nav_menu);
        nav_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(intent);
            }
        });


        /* 하단바 - 랭킹 */
        ImageButton nav_rank = (ImageButton) findViewById(R.id.nav_rank);
        nav_rank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RankActivity.class);
                startActivity(intent);
            }
        });


        /* 하단바 - 알림 */
        ImageButton nav_alarm = (ImageButton) findViewById(R.id.nav_alarm);
        nav_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NofiActivity.class);
                startActivity(intent);
            }
        });

    }
}