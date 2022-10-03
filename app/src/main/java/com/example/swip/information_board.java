package com.example.swip;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class information_board extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.information_board);

        /* 초딩 */
        TextView a = (TextView) findViewById(R.id.a);
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Post_Cho_Stu.class);
                startActivity(intent);
            }
        });

        /* 중딩 */
        TextView b = (TextView) findViewById(R.id.b);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Post_Jung_Stu.class);
                startActivity(intent);
            }
        });

        /* 고딩 */
        TextView c = (TextView) findViewById(R.id.c);
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Post_Godueng_Stu.class);
                startActivity(intent);
            }
        });

        /* 대딩 */
        TextView d = (TextView) findViewById(R.id.d);
        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Post_Dae_Stu.class);
                startActivity(intent);
            }
        });

        /* 임용 */
        TextView e = (TextView) findViewById(R.id.e);
        e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Post_Imyong.class);
                startActivity(intent);
            }
        });


        /* 국가 */
        TextView f = (TextView) findViewById(R.id.f);
        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Post_Gukga.class);
                startActivity(intent);
            }
        });

        /* 기업 */
        TextView g = (TextView) findViewById(R.id.g);
        g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Post_Giup.class);
                startActivity(intent);
            }
        });

        /* 기타 */
        TextView h = (TextView) findViewById(R.id.h);
        h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Post_Gita.class);
                startActivity(intent);
            }
        });

        /* 자격증 */
        TextView i = (TextView) findViewById(R.id.i);
        i.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Post_Dae_Stu.class);
                startActivity(intent);
            }
        });

        /* 자격증 - 기타 */
        TextView gee = (TextView) findViewById(R.id.gee);
        gee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Post_Gita_Zagi.class);
                startActivity(intent);
            }
        });

        /* 내가 쓴 글 */
        TextView k = (TextView) findViewById(R.id.k);
        k.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Post_Naega.class);
                startActivity(intent);
            }
        });

        /* 스크랩 */
        TextView l = (TextView) findViewById(R.id.l);
        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Scrap.class);
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
        ImageButton btn_arlarm = (ImageButton) findViewById(R.id.nav_alarm);
        btn_arlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NofiActivity.class);
                startActivity(intent);
            }
        });
    }
}