package com.example.swip;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class MemberInitActivity extends AppCompatActivity {
    private static final String TAG = "MemberInitActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_init); // activity_member_init 호출

        findViewById(R.id.checkButton).setOnClickListener(onClickListener); // 회원정보 등록을 위한 체크 버튼 아이디 & 클릭 이벤트 리스너 호출
    }

    @Override
    public void onBackPressed() { // 뒤로 가기 버튼 누를 시
        super.onBackPressed(); // 한 단계 뒤로 가고
        finish(); // 메소드를 끝냄
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) { // 아이디 등록 시
            switch (v.getId()){ // 아이디 정보를 가져 옴
                case R.id.checkButton: // 리소스에서 checkButton 아이디 찾음
                    profileUpdate(); // 프로필에 정보를 업로드 함
                    break; // switch 끝
            }
        }
    }; // 종료

    private void profileUpdate() { // 프로필 업로드
        String nick = ((EditText)findViewById(R.id.nameEditText)).getText().toString(); // 사용자의 닉네임을 받아오는 코드
        String birthDay = ((EditText)findViewById(R.id.birthDayEditText)).getText().toString(); // 사용자의 생일을 받아오는 코드

        if(nick.length() > 0 && birthDay.length() > 5){ // 닉네임의 길이는 0글자 이상이어야 하고, 생일의 길이는 5글자 이상이어야 함.
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser(); // 파이어베이스에 정보 업로드
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            MemberInfo memberInfo = new MemberInfo(nick, birthDay); // 닉네임과 생일을 memberInfo에 저장
            if(user != null){ // 만약 user에 값이 없지 않다면
                db.collection("users").document(user.getUid()).set(memberInfo)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                startToast("회원 정보 등록을 성공하였습니다."); // "회원 정보 등록을 성공하였습니다"의 토스트 메시지 화면에 출력
                                startMainActivity();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                startToast("회원 정보 등록에 실패하였습니다."); // "회원 정보 등록에 실패하였습니다"의 토스트 메시지 화면에 출력
                            }
                        }); // 종료
            }
        }else { // user 값이 없다면
            startToast("회원정보를 입력해주세요."); // "회원정보를 입력해주세요"의 토스트 메시지 화면에 출력
        }
    }

    private void startToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show(); // 토스트 메시지 업로드
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class); // 인텐트를 사용해 MainActivity.class를 불러 옴.
        startActivity(intent); // 메인 페이지로 넘어가기 정의
    }
}
