package com.example.swip;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MyPageActivity extends AppCompatActivity {

    ImageView load;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage);

        ImageButton pro_modify = (ImageButton) findViewById(R.id.my_pro);
        pro_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProfileModify.class);
                startActivity(intent);
            }
        });


        ImageButton pro_inq = (ImageButton) findViewById(R.id.my_inq);
        pro_inq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), InqActivity.class);
                startActivity(intent);
            }
        });

        load=(ImageView)findViewById(R.id.profileimg);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        StorageReference pathReference = storageReference.child("photo");
        if (pathReference == null) {
            Toast.makeText(MyPageActivity.this, "저장소에 사진이 없습니다." ,Toast.LENGTH_SHORT).show();
        } else {
            StorageReference submitProfile = storageReference.child("photo/1.png");
            submitProfile.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(MyPageActivity.this).load(uri).into(load);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

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
}