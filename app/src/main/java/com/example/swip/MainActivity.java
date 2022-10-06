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
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//import devs.mulham.horizontalcalendar.HorizontalCalendar;
//import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class MainActivity extends AppCompatActivity {
    private static final String Tag = "MainActivity";
    CalendarView calendarView;
    TextView today;
    //    TextView calendarTextView;
//    int year, month, day;
    Fragment mainFragment;
    public static NoteDatabase noteDatabase = null;
    private Context context;

    public void openDatabase() { // 데이터베이스 생성

        if (noteDatabase != null) {
            noteDatabase.close();
            noteDatabase = null;
        }

        noteDatabase = NoteDatabase.getInstance(this);
        boolean isOpen = noteDatabase.open();
        if (isOpen) {
            Log.d(TAG, "Note database is open.");
        } else {
            Log.d(TAG, "Note database is not open.");
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (noteDatabase != null) {
            noteDatabase.close();
            noteDatabase = null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openDatabase();

        // fragment를 객체로 할당 후 FrameLayout에 fragment_main.xml이 추가되도록 설정
        mainFragment = new MainFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container,mainFragment).commit();

        /* 일정 추가하기 */
        // saveButton 클릭 시 EditText에 입력한 글들이 저장되고 추가되었다는 Toast 메시지 출력
        ImageButton saveButton = findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                saveToDo();
                Toast.makeText(getApplicationContext(),"추가되었습니다.",Toast.LENGTH_SHORT).show();
            }
        });
        //캘린더 뷰
        today=findViewById(R.id.today);
        calendarView=findViewById(R.id.calendarView);

        DateFormat formatter = new SimpleDateFormat("yyyy년 MM월 dd일");
        Date date = new Date(calendarView.getDate());


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override //캘린더에서 선택된 년도날짜일수 표시하기
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String day;
                day = year + "년" +(month+1) + "월" + dayOfMonth + "일";
                today.setText(day);
            }});
//        calendarTextView = findViewById(R.id.calendar_text_view);
//
//        // 시작 날짜
//        Calendar startDate = Calendar.getInstance();
//        startDate.add(Calendar.MONTH, -1);
//
//        // 종료 날짜(현재 날짜 + 1달 후)
//        Calendar endDate = Calendar.getInstance();
//        endDate.add(Calendar.MONTH, 1);
//
//        //  가로달력 실행
//        HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.calendarView)
//                .range(startDate, endDate)
//                .datesNumberOnScreen(5)
//                .build();
//
//        // 날짜 설정
//        year = startDate.get(Calendar.YEAR);
//        month = startDate.get(Calendar.MONTH);
//        day = startDate.get(Calendar.DAY_OF_MONTH);
//
//        calendarTextView.setText(year+"년 "+month+"월 "+day+"일");
//
//        // 날짜 선택 이벤트
//        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
//            @Override
//            public void onDateSelected(Calendar date, int position) {
//                year = date.get(Calendar.YEAR);
//                month = date.get(Calendar.MONTH) +1;
//                day = date.get(Calendar.DAY_OF_MONTH);
//
//                calendarTextView.setText(year+"년 "+month+"월 "+day+"일");
//            }
//        });

        /* 타이머로 가기 */
        Chronometer timer = (Chronometer) findViewById(R.id.timer);
        timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),TimerActivity.class);
                startActivity(intent);
            }
        });

        /* 하단바 */

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
                Intent intent = new Intent(getApplicationContext(),information_board.class);
                startActivity(intent);
            }
        });

        /* 하단바 - 메뉴 */
        ImageButton nav_menu = (ImageButton) findViewById(R.id.nav_menu);
        nav_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(intent);
            }
        });


        /* 하단바 - 랭킹 */
        ImageButton nav_rank = (ImageButton) findViewById(R.id.nav_rank);
        nav_rank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RankActivity.class);
                startActivity(intent);
            }
        });


        /* 하단바 - 알림 */
        ImageButton nav_alarm = (ImageButton) findViewById(R.id.nav_alarm);
        nav_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NofiActivity.class);
                startActivity(intent);
            }
        });
    }

    private void saveToDo(){ // 저장 버튼 클릭 시 EditText에 적힌 글을 가져와 테이블에 값을 추가
        EditText inputToDo = findViewById(R.id.inputToDo);

        String todo = inputToDo.getText().toString();

        String sqlSave = "insert into " + NoteDatabase.TABLE_NOTE + " (TODO) values (" +
                "'" + todo + "')";

        NoteDatabase database = NoteDatabase.getInstance(context);
        database.execSQL(sqlSave);


        inputToDo.setText("");
    }
}