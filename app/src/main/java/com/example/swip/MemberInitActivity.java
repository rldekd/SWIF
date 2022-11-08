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
        setContentView(R.layout.activity_member_init);

        findViewById(R.id.checkButton).setOnClickListener(onClickListener);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.checkButton:
                    profileUpdate();
                    break;
            }
        }
    };

    private void profileUpdate() {
        String nick = ((EditText)findViewById(R.id.nameEditText)).getText().toString();
        String birthDay = ((EditText)findViewById(R.id.birthDayEditText)).getText().toString();

        if(nick.length() > 0 && birthDay.length() > 5){
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            MemberInfo memberInfo = new MemberInfo(nick, birthDay);
            if(user != null){
                db.collection("users").document(user.getUid()).set(memberInfo)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                startToast("회원 정보 등록을 성공하였습니다.");
                                startMainActivity();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                startToast("회원 정보 등록에 실패하였습니다.");
                            }
                        });
            }

        }else {
            startToast("회원정보를 입력해주세요.");
        }
    }

    private void startToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent); // 메인 페이지로 넘어가기 정의
    }




    public class CheckActivity implements View.OnClickListener {
        protected void onCreate(Bundle savedInstanceState) {
            //버튼 선언
            Button button01=(Button)findViewById(R.id.check01);
            Button button02=(Button)findViewById(R.id.check02);

            button01.setOnClickListener(this);
            button02.setOnClickListener(this);

            // option1 체크박스가 눌렸을 때
            findViewById(R.id.check01).setOnClickListener(new Button.OnClickListener() {
                public void onClick(View v) {

                    CheckBox checkbox = (CheckBox) findViewById(R.id.check01);
                    checkbox.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getApplicationContext(), UserTermsActivity.class);
                            startActivity(intent);
                        }
                    });

                    Checked(v); // 체크되었을 때 동작코드
                }
            });

            // option2 체크박스가 눌렸을 때
            findViewById(R.id.check02).setOnClickListener(new Button.OnClickListener() {
                public void onClick(View v) {

                    CheckBox checkbox = (CheckBox) findViewById(R.id.check02);
                    checkbox.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getApplicationContext(), UserTermsActivity.class);
                            startActivity(intent);
                        }
                    });


                    Checked(v); // 체크되었을 때 동작코드
                }


            });

        }


        public String Checked(View v) { // 체크되었을 때 동작할 메소드 구현
            // TODO Auto-generated method stub
            CheckBox option1 = (CheckBox) findViewById(R.id.check01); // option1체크박스
            // 선언
            CheckBox option2 = (CheckBox) findViewById(R.id.check02); // option1체크박스
            // 선언

            String resultText = ""; // 체크되었을 때 값을 저장할 스트링 값
            if (option1.isChecked()) { // option1 이 체크되었다면
                resultText = "이용 약관을 확인하셨습니다.";
            }
            if (option2.isChecked()) {
                resultText = "개인정보 처리방침을 확인하셨습니다."; // option2 이 체크되었다면
            }

            return resultText; // 체크된 값 리턴
        }

        @Override
        public void onClick(View view) {

        }
    }


}