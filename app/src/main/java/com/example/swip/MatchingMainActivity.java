package com.example.swip;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class MatchingMainActivity  extends AppCompatActivity {
    private ImageButton btn_send;
    private EditText et_send;
    private ListView lv_chating;

    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> arr_room = new ArrayList<>();

    private String str_room_name;
    private String str_user_name;

    private DatabaseReference reference;
    private String key;
    private String chat_user;
    private String chat_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matching_main);

        lv_chating = (ListView) findViewById(R.id.lv_chating);
        btn_send = (ImageButton) findViewById(R.id.btn_send);
        et_send = (EditText) findViewById(R.id.et_send);

        str_room_name = getIntent().getExtras().get("room_name").toString();
        str_user_name = getIntent().getExtras().get("user_name").toString();
        reference = FirebaseDatabase.getInstance().getReference().child(str_room_name);

        setTitle(str_room_name);

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arr_room);
        lv_chating.setAdapter(arrayAdapter);
        // 리스트뷰가 갱신될 때 하단으로 자동 스크롤
        lv_chating.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // map을 사용해 name과 메시지를 가져오고, key에 값 요청
                Map<String, Object> map = new HashMap<String, Object>();
                key = reference.push().getKey();
                reference.updateChildren(map);

                DatabaseReference root = reference.child(key);

                // updateChildren을 호출하여 데이터베이스 최종 업데이트
                Map<String, Object> objectMap = new HashMap<String, Object>();
                objectMap.put("name", str_user_name);
                objectMap.put("message", et_send.getText().toString());

                root.updateChildren(objectMap);

                et_send.setText("");
            }
        });

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                chatConversation(snapshot);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                chatConversation(snapshot);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    // addChildEventListener를 통해 실제 데이터 베이스에 변경된 값이 있으면 화면에 보여지고 있는 ListView의 값을 갱신함
    private  void chatConversation(DataSnapshot snapshot) {
        Iterator i = snapshot.getChildren().iterator();

        while (i.hasNext()) {
            chat_message = (String) ((DataSnapshot) i.next()).getValue();
            chat_user = (String) ((DataSnapshot) i.next()).getValue();

            arrayAdapter.add(chat_user + " : " + chat_message);
        }

        arrayAdapter.notifyDataSetChanged();
    }


}
