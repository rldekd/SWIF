package com.example.swip;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class PostConfirmActivity extends AppCompatActivity {
    private  ImageView ivBack, ivImage;
    private  TextView tvTitle, tvContent;
    Button btnHome;
    ProgressBar mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ivBack = findViewById(R.id.ivBack);
        ivImage = findViewById(R.id.cImage);
        tvTitle = findViewById(R.id.cTitle);
        tvContent = findViewById(R.id.cContent);
        btnHome = findViewById(R.id.btnBackHome);
        mProgress = findViewById(R.id.progressBar);


        personalProductsIntents(); // 게시글 가져오는 함수

        btnHome.setOnClickListener(new View.OnClickListener() { // 뒤로가기 버튼 클릭 시
            @Override
            public void onClick(View v) {
                Intent logIntent = new Intent(PostConfirmActivity.this, InformationBoardActivity.class); // 게시판 카테고리로 이동
                startActivity(logIntent);
                return;
            }
        });
    }

    private void personalProductsIntents() {
        String mpostTitle, mpostContent, mpostImage;
        if (getIntent().hasExtra("postTitle1") // 제목 여부 확인
                && getIntent().hasExtra("postContent1") // 내용 여부 확인
                && getIntent().hasExtra("imageUri1") // 사진 여부 확인
        ) {

            mpostTitle = getIntent().getStringExtra("postTitle1"); // 제목 전달
            mpostContent = getIntent().getStringExtra("postContent1"); // 내용 전달
            mpostImage = getIntent().getStringExtra("imageUri1");; // 이미지 전달


            tvTitle.setText(mpostTitle); // 제목 가져오기
            tvContent .setText(mpostContent); // 내용 가져오기
            Picasso.get().load(mpostImage).fit().placeholder(R.drawable.placeholder).into(ivImage); // 이미지 설정 후 가져오기

        }

    }
}
