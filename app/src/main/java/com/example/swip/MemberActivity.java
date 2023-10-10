package com.example.swip;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.net.HttpCookie;
import java.util.regex.Pattern;


public class MemberActivity extends AppCompatActivity {
    Button go;
    EditText id1 , pw1 , pw2;
    private FirebaseAuth mAuth; // 계정등록을 위한 FireBase 객체
    private Object OnClickListener;
    private CheckBox Check01; // 이용약관 체크 변수 01
    private CheckBox Check02; // 이용약관 체크 변수 02


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member); // member 레이아웃으로 이동



        Button check_01 = (Button) findViewById(R.id.check_ok_01); // check_ok_01 버튼을 누르면
        check_01.setOnClickListener(new OnClickListener() { // 이용약관 동의 check_01 버튼을 눌렀을 경우
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UserTermsActivity.class); // UserTermsActivity.class로 이동
                startActivity(intent);
            }
        });

        Button check_02 = (Button) findViewById(R.id.check_ok_02); // check_ok_02 버튼을 누르면
        check_02.setOnClickListener(new OnClickListener() { // 이용약관 동의 check_02 버튼을 눌렀을 경우
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UserInfoTermsActivity.class); // UserTermsActivity.class로 이동
                startActivity(intent);
            }
        });


        id1 = (EditText)findViewById(R.id.mem_id); // 이메일 입력공간
        pw1 = (EditText)findViewById(R.id.mem_pass); // 첫번째 비밀번호 입력공간
        pw2 = (EditText)findViewById(R.id.mem_pass2); // 비밀번호 확인 입력공간
        Check01 = (CheckBox)findViewById(R.id.check01); // 이용약관 동의 버튼
        Check02 = (CheckBox)findViewById(R.id.check02); // 개인정보 처리방침 동의 버

        mAuth = FirebaseAuth.getInstance(); // 인스턴스 초기화튼
    }



    public void mem_login(View view){
        String id1 = this.id1.getText().toString(); // 이메일의 값을 문자열로 받아 옴
        String pw1 = this.pw1.getText().toString(); // 비밀번호의 값을 문자열로 받아 옴
        String pw2  = this.pw2.getText().toString(); // 비밀번호 재확인 값을 문자열로 받아 옴
        boolean Check01 = this.Check01.isChecked(); // 첫 번째 이용약관에 동의 했는지 True or False 값으로 받아 옴
        boolean Check02 = this.Check02.isChecked(); // 두 번째 이용약관에 동의 했는지 True or False 값으로 받아 옴


        String e_mailPattern = "^[a-zA-Z0-9]+@[a-zA-Z0-9.]+$"; // 이메일 형식 패턴
        if(!Pattern.matches(e_mailPattern , id1)){ // 이메일 패턴에 들어있는 값을 id1 변수에 할당
            Toast.makeText(MemberActivity.this , "이메일 형식을 확인해주세요." , Toast.LENGTH_LONG).show(); // 이메일 형식에 옳지 않을 경우 "이메일 형식을 확인해주세요"의 토스트 메시지 화면에 발생
            return;
        }


        StringBuffer strbuP1 = new StringBuffer(pw1); // pw1에 들어있는 값을 strbuP1 변수에 값을 할당함
        StringBuffer strbuP2 = new StringBuffer(pw2); // pw2에 들어있는 값을 strbuP2 변수에 값을 할당함



        if(strbuP1.length() < 8 || strbuP2 .length() < 8){ // 최소 비밀번호 사이즈를 맞추기 위해서
            Toast.makeText(this, "비밀번호는 최소 8자리 이상입니다.", Toast.LENGTH_SHORT).show(); // 비밀번호의 최소 사이즈는 8이다. 비밀번호가 7자리 이하면 화면에 "비밀번호는 최소 8자리 이상입니다"라는 토스트 메시지 발생
            return;
        }

        if(pw1.equals("") || pw2.equals("")){ // 첫 번째 비밀번호 입력 값과 두 번째 비밀번호 입력 값이 같아야 함
            Toast.makeText(MemberActivity.this , "비밀번호를 입력해주세요." , Toast.LENGTH_LONG).show(); // 비밀번호 재확인을 하지 않았을 경우 화면에 "비밀번호를 입력해주세요"라는 토스트 메시지 발생
            return;
        }



        if((pw1.equals(pw2) && Check01==true && Check02==true)){ // 만약 비밀번호 입력 값과 비밀번호 재입력 값이 같고, 이용약관 두 개에 다 체크가 되었을 경우 다음으로 이동 가능

            mAuth.createUserWithEmailAndPassword(id1, pw1) // 이메일 값을 가지고 있는 변수 id1, 비밀번호 값을 가지고 있는 변수 pw1 두 개의 값을 mAuth 변수에 할당
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) { // 위 if 식이 성립할 경우
                            if (task.isSuccessful()) {
                                Toast.makeText(MemberActivity.this, "회원가입을 성공했습니다.", Toast.LENGTH_SHORT).show(); // "회원가입을 성공했습니다" 메시지 화면에 출력
                                FirebaseUser user = mAuth.getCurrentUser(); // mAuth에 들어 있는 아이디와 비밀번호 값을 firebase에 저장한다.
                                startMemberInitActivity(); // 모든 회원가입 과정이 끝나 애플리케이션을 시작한다.
                            } else {
                                Toast.makeText(MemberActivity.this, "회원가입을 실패하였습니다.", Toast.LENGTH_SHORT).show(); // "회원가입을 실패하였습니다" 메시지 화면에 출력
                            }
                        }
                    });
        }else{ // if 식이 잃치하지 않을 경우 발생한다, pw1와 pw2에 입력 되어진 값이 서로 다르거나, 두 개의 이용약관 중 하나라도 체크하지 않을 경우에 발생한다.
            Toast.makeText(this, "비밀번호가 서로 다르거나, 이용약관에 동의하지 않으셨습니다.", Toast.LENGTH_SHORT).show(); // "비밀번호가 서로 다르거나, 이용약관에 동의하지 않으셨습니다." 메시지 화면에 출력
        }
        hide_keyboard(view); // 키보드 올리기 or 숨기기 기능 실행

    }

    public void hide_keyboard(View view){ // 키보드 올리기 or 숨기기 기능 실행
        InputMethodManager imm = (InputMethodManager)getSystemService(this.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(id1.getWindowToken(), 0); // hideSoftInputFromWindow 코드를 사용해 키보드를 숨김
    }


    @Override
    protected void onDestroy() { // 회원 정보 기입 페이지로 넘어가기 위해 액티비티 객체를 파괴시킨다.
        finish();
        super.onDestroy(); // onDestroy()를 상속
    }
    private void startMemberInitActivity() {
        Intent intent = new Intent(this, MemberInitActivity.class);
        startActivity(intent); // 회원 정보 기입 페이지로 넘어가기 정의
    }





    public void ButtonClick1(View v) { // SWIF 이용약관 동의 여부 확인

        CheckBox checkBox = (CheckBox) findViewById(R.id.check01) ; // check01에 동의 여부 값 저장
        if (checkBox.isChecked()) { // 만약 동의했으면
            Toast.makeText(getApplicationContext(), "SWIF 이용 약관에 동의하셨습니다.", Toast.LENGTH_SHORT).show(); // "SWIF 이용 약관에 동의하셨습니다"의 토스트 메시지 출력
        } else { // 동의하지 않았으면
            Toast.makeText(getApplicationContext(),"SWIF 이용 약관에 동의해주세요.", Toast.LENGTH_SHORT).show(); // "SWIF 이용 약관에 동의해주세요"의 토스트 메시지 출력
        }

    }


    public void ButtonClick2(View v) { // 개인정보 처리방침 동의 여부 확인

        CheckBox checkBox = (CheckBox) findViewById(R.id.check02) ; // check02에 동의 여부 값 저장
        if (checkBox.isChecked()) { // 만약 동의했으면
            Toast.makeText(getApplicationContext(), "개인정보 처리방침에 동의하셨습니다.", Toast.LENGTH_SHORT).show(); // "개인정보 처리방침에 동의하셨습니다"의 토스트 메시지 출력
        } else { // 동의하지 않았으면
            Toast.makeText(getApplicationContext(),"개인정보 처리방침에 동의해주세요.", Toast.LENGTH_SHORT).show(); //"개인정보 처리방침에 동의해주세요"의 토스트 메시지 출력
        }

    }
}
