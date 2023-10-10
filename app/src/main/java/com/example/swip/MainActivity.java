package com.example.swip;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private static final String Tag = "MainActivity";
    CalendarView calendarView;
    TextView today;
    Fragment mainFragment;
    public static NoteDatabaseActivity noteDatabase = null;
    private Context context;

    public void openDatabase() { // 데이터베이스 생성

        if (noteDatabase != null) { // 데이터베이스가 있다면 데이터베이스를 종료하고 데이터베이스 값을 null로 설정
            noteDatabase.close();
            noteDatabase = null;
        }

        noteDatabase = NoteDatabaseActivity.getInstance(this); // NoteDatabaseActivity 불러오기
        boolean isOpen = noteDatabase.open(); //
        if (isOpen) {
            Log.d(TAG, "Note database is open."); // 투두리스트 데이터베이스가 열리면 Log 창에 Note database is open. 출력
        } else {
            Log.d(TAG, "Note database is not open."); // 투두리스트 데이터베이스가 열리지 않으면 Log 창에 Note database is not open. 출력
        }
    }
    @Override
    protected void onDestroy() { // 데이터베이스 종료
        super.onDestroy();

        if (noteDatabase != null) { // 데이터베이스가 있다면 데이터베이스를 종료하고 데이터베이스 값을 null로 설정
            noteDatabase.close();
            noteDatabase = null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openDatabase(); // openDatabase 실행

        mainFragment = new MainFragmentActivity();
        getSupportFragmentManager().beginTransaction().replace(R.id.container,mainFragment).commit(); // fragment를 객체로 할당 후 FrameLayout에 fragment_main.xml이 추가되도록 설정

        /* 일정 추가하기 */
        ImageButton saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener(){ // saveButton 클릭 시
            @Override
            public void onClick(View view){
                saveToDo(); // saveTodo 실행
                Toast.makeText(getApplicationContext(),"추가되었습니다.",Toast.LENGTH_SHORT).show(); // 추가되었다는 토스트 메시지를 출력
            }
        });

        /* 매칭하러 가기 */
        Button add_plan = (Button) findViewById(R.id.add_plan);
        add_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MatchingNickActivity.class);
                startActivity(intent);
            }
        });


        /* 캘린더 뷰 */
        today=findViewById(R.id.today);
        calendarView=findViewById(R.id.calendarView);

        DateFormat formatter = new SimpleDateFormat("yyyy년 MM월 dd일"); // 데이터 형식을 ****년 **월 **일로 설정
        Date date = new Date(calendarView.getDate());


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() { //캘린더에서 선택된 년도날짜일수 표시하기
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String day;
                day = year + "년" +(month+1) + "월" + dayOfMonth + "일"; // 선택한 날짜를 day에 저장
                today.setText(day); // 선택한 날짜 표시
            }});


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
    }

    private void saveToDo(){
        EditText inputToDo = findViewById(R.id.inputToDo);
        String todo = inputToDo.getText().toString();
        String sqlSave = "insert into " + NoteDatabaseActivity.TABLE_NOTE + " (TODO) values (" +
                "'" + todo + "')"; // 테이블에 EditText에 적힌 값을 가져와 테이블에 값을 추가
        NoteDatabaseActivity database = NoteDatabaseActivity.getInstance(context);
        database.execSQL(sqlSave); // 데이터베이스 저장
        inputToDo.setText("");
    }
}
