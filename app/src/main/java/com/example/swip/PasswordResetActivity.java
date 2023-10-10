package com.example.swip;

import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class PasswordResetActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);

        mAuth = FirebaseAuth.getInstance(); // Firebase Auth 선언

        findViewById(R.id.sendButton).setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.sendButton:
                    send(); // 이메일 보내기
                    break;
            }
        }
    };

    private void send() {
        String email = ((EditText)findViewById(R.id.emailEditText)).getText().toString();

        if(email.length() > 0){ // 이메일 길이가 0보다 크면
            mAuth.sendPasswordResetEmail(email) // 이메일에 비밀번호 재설정 보내기
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                startToast("이메일을 보냈습니다."); // 이메일 전송 완료 토스트
                                finish(); // 종료
                            }
                        }
                    });
        }else {
            startToast("이메일을 입력해 주세요."); // 이메일 값이 없다면 토스트 출력
        }
    }

    private void startToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show(); // 토스트 출력 함수
    }
}
