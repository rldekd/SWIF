package com.example.swip;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Post_Detail extends AppCompatActivity {

    public static String WriterUid;
    public static String Time;

    RecyclerView recyclerView;
    RecyclerView recyclerCommentView;
    ArrayList<NewComment> newCommentArrayList;
    MyCommentAdapter myCommentAdapter;
    FirebaseFirestore db;
    NewComment newComment;
    EditText post_comment;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public static String title="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_detail);

        recyclerView = findViewById(R.id.noteRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        recyclerCommentView = findViewById(R.id.commentRecyclerView);
        recyclerCommentView.setHasFixedSize(true);
        recyclerCommentView.setLayoutManager(new LinearLayoutManager(this));

        post_comment = (EditText)findViewById(R.id.inputcomment);


        newComment = new NewComment();

        db = FirebaseFirestore.getInstance();

        newCommentArrayList = new ArrayList<NewComment>();


        myCommentAdapter = new MyCommentAdapter(Post_Detail.this,newCommentArrayList);


        recyclerCommentView.setAdapter(myCommentAdapter);


        newCommentArrayList.clear();


        EventCommentChangeListener();



        ImageView imageBack=findViewById(R.id.imageBack);
        imageBack.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                onBackPressed();

            }
        });


        ImageView imageAddComment=findViewById(R.id.add_comment);
        imageAddComment.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                newComment.setComment(post_comment.getText().toString());

                newComment.setUid(user.getUid());

                newComment.setWriterUid(WriterUid);



                db.collection("NewComment").add(newComment); // NewComment에 post_comment, user, WriterUid 넣기

                Toast.makeText(Post_Detail.this,"댓글이 등록되었습니다.",Toast.LENGTH_LONG).show(); // 댓글 등록 완료 메시지

                post_comment.setText(""); // 텍스트 출력 설정

            }
        });


    }


    private void EventCommentChangeListener() {
        db.collection("NewComment").whereEqualTo("writerUid",WriterUid)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {


                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error != null)
                        {
                            Log.e("Firestore error",error.getMessage());
                            return;


                        }

                        for (DocumentChange dc : value.getDocumentChanges())
                        {
                            if(dc.getType() == DocumentChange.Type.ADDED)
                            {
                                newCommentArrayList.add(dc.getDocument().toObject(NewComment.class));
                            }
                            myCommentAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }
}