package com.example.swip;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.swip.databinding.ActivityHotelListBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class PostGitaZagiActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerAdapter mAdapter;
    private FirebaseStorage storage;
    private DatabaseReference mDatabase;
    private ValueEventListener mDBListener;
    List<Post_Model> mPostModel;
    List<Post_Model> filteredmPostModel;
    LinearLayoutManager layoutManager;
    EditText searchText;
    CardView circleP_bar;
    TextView defaultView;

    Button extended_fab;



    ActivityHotelListBinding hotelListBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_list);

        recyclerView = findViewById(R.id.recyclerVw);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        extended_fab= findViewById(R.id.extended_fab);


        searchText = findViewById(R.id.editTextSearch);
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (!s.toString().isEmpty()) {

                    settAdapter(s.toString());
                } else {
                    mAdapter = new RecyclerAdapter(PostGitaZagiActivity.this, mPostModel);
                    recyclerView.setAdapter(mAdapter);
                }

            }
        });
        circleP_bar = findViewById(R.id.progressBarCircle);
        mDatabase = FirebaseDatabase.getInstance().getReference("피드백");

        storage = FirebaseStorage.getInstance();

        mPostModel = new ArrayList<>();
        filteredmPostModel = new ArrayList<>();

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter  = new RecyclerAdapter(PostGitaZagiActivity.this, mPostModel);
        recyclerView.setAdapter(mAdapter);

        if (isNetworkConnected()) {
            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    mPostModel.clear();
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Post_Model model = postSnapshot.getValue(Post_Model.class);


                        mPostModel.add(model);
                    }

                    if (mPostModel.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "등록된 글이 없습니다.", Toast.LENGTH_SHORT).show();
                        circleP_bar.setVisibility(View.INVISIBLE);
                    }


                    mAdapter.notifyDataSetChanged();
                    circleP_bar.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                    Toast.makeText(PostGitaZagiActivity.this, "Permission Denied...", Toast.LENGTH_SHORT).show();
                    Toast.makeText(PostGitaZagiActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    circleP_bar.setVisibility(View.INVISIBLE);
                }
            });
        } else {
            circleP_bar.setVisibility(View.INVISIBLE);
            defaultView.setVisibility(View.VISIBLE);
            defaultView.setText(R.string.No_network);
        }

        extended_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddPostActivity_feed.class));
                return;
            }
        });


    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();

    }

    private void settAdapter(String toString) {

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                filteredmPostModel.clear();
                recyclerView.removeAllViews();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String uid = snapshot.getKey();
                    String hotelLocation = snapshot.child("hotelLocation").getValue(String.class);
                    String hotelName = snapshot.child("hotelName").getValue(String.class);

                    Post_Model modelFiltered = snapshot.getValue(Post_Model.class);

                    if (hotelLocation.equals(null)) {
                        Toast.makeText(PostGitaZagiActivity.this, "Location is null", Toast.LENGTH_SHORT).show();
                    } else {
                        if (hotelLocation.toLowerCase().contains(toString.toLowerCase())) {
                            filteredmPostModel.add(modelFiltered);
                        }

                        if (hotelName.equals(null)) {
                            Toast.makeText(PostGitaZagiActivity.this, "Hotel Name is Null", Toast.LENGTH_SHORT).show();
                        } else {
                            if (hotelName.toLowerCase().contains(toString.toLowerCase())) {
                                filteredmPostModel.add(modelFiltered);
                            }

                        }
                    }
                }

                if (!filteredmPostModel.isEmpty()) {
                    defaultView.setVisibility(View.INVISIBLE);
                    mAdapter = new RecyclerAdapter(PostGitaZagiActivity.this, filteredmPostModel);
                    recyclerView.setAdapter(mAdapter);
                } else {
                    defaultView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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