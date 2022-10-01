package com.example.swip;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class mypage extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage);

        TextView changeBtn = (TextView) findViewById(R.id.mynick);
        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Profile_modify.class);
                startActivity(intent);
            }
        });

        TextView changeBtn2 = (TextView) findViewById(R.id.mypoint);
        changeBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),PointActivity.class);
                startActivity(intent);
            }
        });

        TextView changeBtn3 = (TextView) findViewById(R.id.myrank);
        changeBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),RankActivity.class);
                startActivity(intent);
            }
        });

        TextView changeBtn4 = (TextView) findViewById(R.id.myscrap);
        changeBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Scrap.class);
                startActivity(intent);
            }
        });

        TextView changeBtn5 = (TextView) findViewById(R.id.mytema);
        changeBtn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ThemeActivity.class);
                startActivity(intent);
            }
        });

        TextView changeBtn6 = (TextView) findViewById(R.id.myinq);
        changeBtn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),InqActivity.class);
                startActivity(intent);
            }
        });

        ImageButton noti = (ImageButton) findViewById(R.id.btn_noti);
        noti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NofiActivity.class);
                startActivity(intent);
            }
        });

        ImageButton rank = (ImageButton) findViewById(R.id.btn_rank);
        rank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RankActivity.class);
                startActivity(intent);
            }
        });

        ImageButton menu = (ImageButton) findViewById(R.id.btn_menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(intent);
            }
        });

        ImageButton home = (ImageButton) findViewById(R.id.btn_home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        ImageButton post = (ImageButton) findViewById(R.id.btn_write);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), information_board.class);
                startActivity(intent);
            }
        });


        Button membership = (Button) findViewById(R.id.mymem);
        membership.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), membership.class);
                startActivity(intent);
            }
        });


    }
}