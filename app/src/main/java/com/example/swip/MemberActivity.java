package com.example.swip;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class MemberActivity extends AppCompatActivity {
    Button go;
    EditText id1 , pw1 , pw2;
    private FirebaseAuth mAuth; // 계정등록을 위한 FireBase 객체



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member);



        id1 = (EditText)findViewById(R.id.mem_id); // 이메일 입력공간
        pw1 = (EditText)findViewById(R.id.mem_pass); // 첫번째 비밀번호 입력공간
        pw2 = (EditText)findViewById(R.id.mem_pass2); // 비밀번호 확인 입력공간

        mAuth = FirebaseAuth.getInstance(); // 인스턴스 초기화

    }
    public void mem_login(View view){
        String id1 = this.id1.getText().toString();
        String pw1 = this.pw1.getText().toString();
        String pw2  = this.pw2.getText().toString();

        String e_mailPattern = "^[a-zA-Z0-9]+@[a-zA-Z0-9.]+$"; // 이메일 형식 패턴
        if(!Pattern.matches(e_mailPattern , id1)){
            Toast.makeText(MemberActivity.this , "이메일 형식을 확인해주세요." , Toast.LENGTH_LONG).show();
            return;
        }



        StringBuffer strbuP1 = new StringBuffer(pw1);
        StringBuffer strbuP2 = new StringBuffer(pw2);



        if(strbuP1.length() < 8 || strbuP2 .length() < 8){ // 최소 비밀번호 사이즈를 맞추기 위해서
            Toast.makeText(this, "비밀번호는 최소 8자리 이상입니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        if(pw1.equals("") || pw2.equals("")){
            Toast.makeText(MemberActivity.this , "비밀번호를 입력해주세요." , Toast.LENGTH_LONG).show();
            return;
        }

        if(pw1.equals(pw2)){

            mAuth.createUserWithEmailAndPassword(id1, pw1)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(MemberActivity.this, "회원가입을 성공했습니다.", Toast.LENGTH_SHORT).show();
                                FirebaseUser user = mAuth.getCurrentUser();
                                startMemberInitActivity();
                            } else {
                                Toast.makeText(MemberActivity.this, "회원가입을 실패하였습니다.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }else{
            Toast.makeText(this, "비밀번호가 서로 다릅니다.", Toast.LENGTH_SHORT).show();
        }
        hide_keyboard(view);

    }

    public void hide_keyboard(View view){
        InputMethodManager imm = (InputMethodManager)getSystemService(this.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(id1.getWindowToken(), 0);
    }

    //액티비티 객체 파괴될때
    @Override
    protected void onDestroy() {
        finish();
        super.onDestroy();
    }
    private void startMemberInitActivity() {
        Intent intent = new Intent(this, MemberInitActivity.class);
        startActivity(intent); // 회원 정보 기입 페이지로 넘어가기 정의
    }

}


