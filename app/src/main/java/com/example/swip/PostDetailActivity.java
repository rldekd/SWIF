package com.example.swip;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.squareup.picasso.Picasso;

public class PostDetailActivity extends AppCompatActivity { // 모든 게시글 상세보기 코드 동일함

    private static final int CALL_PERMISSION = 30;

    ImageView ivpostImage;
    TextView tvpostTitle, tvpostContent;

    String mpostTitle,
            mpostContent, mpostImage;




    private void initializeWidgets(){
        ivpostImage        = findViewById(R.id.ivPostImage); // 사진
        tvpostTitle     = findViewById(R.id.tvTitle); // 제목
        tvpostContent        = findViewById(R.id.tvContent); // 내용
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        initializeWidgets(); // 내용 연결
        recieveIntents(); // 내용 출력

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Post_Detail.class); // 댓글 페이지로 이동
                startActivity(intent);
            }
        });


    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(PostDetailActivity.this, new String[]{Manifest.permission.CALL_PHONE}, CALL_PERMISSION);
    }

    private boolean checkedPermission() {
        int callPermission = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE);

        return callPermission == PackageManager.PERMISSION_GRANTED;
    }




    private void recieveIntents() {
        if (getIntent().hasExtra("postTitle1")
                && getIntent().hasExtra("postContent1")
                && getIntent().hasExtra("imageUri1")) {

            mpostTitle = getIntent().getStringExtra("postTitle1");
            mpostContent = getIntent().getStringExtra("postContent1");
            mpostImage = getIntent().getStringExtra("imageUri1");

            provision(mpostTitle,mpostContent,
                    mpostImage);
        }
    }

    private void provision(String mTitle, String mContents, String mpostImage) {

        tvpostTitle.setText(mTitle); ;
        tvpostContent .setText(mContents);



        Picasso.get().load(mpostImage).fit().placeholder(R.drawable.placeholder).into(ivpostImage);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CALL_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission For Calling has been Accepted...", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission For Calling has been Denied...", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
}