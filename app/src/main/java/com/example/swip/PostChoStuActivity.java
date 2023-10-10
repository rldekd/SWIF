package com.example.swip;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class PostChoStuActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerAdapter mAdapter;
    private FirebaseStorage storage;
    private DatabaseReference mDatabase;
    List<Post_Model> mPostModel;
    List<Post_Model> filteredmPostModel;
    LinearLayoutManager layoutManager;
    CardView circleP_bar;
    TextView defaultView;

    Button extended_fab;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_list);

        recyclerView = findViewById(R.id.recyclerVw);
        recyclerView.setHasFixedSize(true); //  지정한 layout 정보를 가져오기
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // 배치 방법
        extended_fab= findViewById(R.id.extended_fab);



        circleP_bar = findViewById(R.id.progressBarCircle);
        mDatabase = FirebaseDatabase.getInstance().getReference("초등학생"); // 데이터 접근

        storage = FirebaseStorage.getInstance(); // 인스턴스 선언

        mPostModel = new ArrayList<>();
        filteredmPostModel = new ArrayList<>();

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter  = new RecyclerAdapter(PostChoStuActivity.this, mPostModel);
        recyclerView.setAdapter(mAdapter);


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


        if (isNetworkConnected()) { // 인터넷이 연결되어 있다면
            mDatabase.addValueEventListener(new ValueEventListener() { // 데이터 저장
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    mPostModel.clear(); // set 객체 지우기
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) { // 저장된 데이터를 하나씩 얻어오기
                        Post_Model model = postSnapshot.getValue(Post_Model.class); // getValue()의 매개변수로 Post_Model.class를 지정하기
                        mPostModel.add(model); // 모델 추가하기
                    }
                    if (mPostModel.isEmpty()) { // post가 없으면
                        Toast.makeText(getApplicationContext(), "등록된 글이 없습니다.", Toast.LENGTH_SHORT).show(); // 토스트 메시지 출력
                        circleP_bar.setVisibility(View.INVISIBLE); // 로딩 안 보이기
                    }
                    mAdapter.notifyDataSetChanged(); // 아이템 변경(데이터가 업데이트 되었지만 위치는 변하지 않았을 때), 구조적 변경(아이템간에 삽입, 삭제, 이동이 일어났을 때)
                    circleP_bar.setVisibility(View.INVISIBLE); // 로딩 안 보이기
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(PostChoStuActivity.this, "Permission Denied...", Toast.LENGTH_SHORT).show(); // 권한 거부 메시지 출력
                    Toast.makeText(PostChoStuActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show(); // 에러 메시지 출력
                    circleP_bar.setVisibility(View.INVISIBLE); // 로딩 안 보이기
                }
            });
        } else {
            circleP_bar.setVisibility(View.INVISIBLE); // 로딩 안 보이기
            defaultView.setVisibility(View.VISIBLE); // NO NETWORK! 출력
            defaultView.setText(R.string.No_network); // defaultView에 텍스트 정의 = NO NETWORK!
        }

        extended_fab.setOnClickListener(new View.OnClickListener() { // 글쓰기 버튼을 누르면
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddPostActivity_cho.class)); // 글쓰기 액티비티로 이동
                return;
            }
        });


    }

    private boolean isNetworkConnected() { // 인터넷 연결 여부 확인
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();

    }

}
