package com.example.swip;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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


    RecyclerView recyclerCommentView;// comment


    ArrayList<NewPost> newPostArrayList;


    ArrayList<NewComment> newCommentArrayList; // comment
    MyAdapter myAdapter;
    MyCommentAdapter myCommentAdapter; // comment
    FirebaseFirestore db;
    NewComment newComment; // comment
    EditText post_comment; // comment
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser(); // comment

    public static String title="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        // Dynamically add the Post cardview in the noteRecyclerView
        recyclerView = findViewById(R.id.noteRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        // Dynamically add the comment cardview in the commentRecycler view
        recyclerCommentView = findViewById(R.id.commentRecyclerView);
        recyclerCommentView.setHasFixedSize(true);
        recyclerCommentView.setLayoutManager(new LinearLayoutManager(this));

        post_comment = (EditText)findViewById(R.id.inputcomment);


        newComment = new NewComment();

        // create Firebase Firestore instance
        db = FirebaseFirestore.getInstance();

        // Create ArrayList for Post and Comment
        newPostArrayList = new ArrayList<NewPost>();
        newCommentArrayList = new ArrayList<NewComment>();


        myAdapter = new MyAdapter(Post_Detail.this,newPostArrayList);
        myCommentAdapter = new MyCommentAdapter(Post_Detail.this,newCommentArrayList);


        recyclerView.setAdapter(myAdapter);
        recyclerCommentView.setAdapter(myCommentAdapter);


        newCommentArrayList.clear();

        //Every time that user enter the activity check the changes in the Firestore database
        EventChangeListener();
        EventCommentChangeListener();



        // if Back Button (<) is pressed go to previous activity
        ImageView imageBack=findViewById(R.id.imageBack);
        imageBack.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                // startActivityForResult(new Intent(getApplicationContext(),PostList.class),
                // 1);
                onBackPressed();

            }
        });


        // if Clieck the add comment image get the data that user has entered and save it in to Comment collection in Firesotre
        ImageView imageAddComment=findViewById(R.id.add_comment);
        imageAddComment.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                newComment.setComment(post_comment.getText().toString());

                newComment.setUid(user.getUid());

                newComment.setWriterUid(WriterUid);



                db.collection("NewComment")
                        .add(newComment);

                Toast.makeText(Post_Detail.this,"data inserted succefully",Toast.LENGTH_LONG).show();

                post_comment.setText("");

            }
        });


    }


    // EventChangeListener get the Post from the Firestore database every time that user call the function and save the data in the newPostArrayList
    private void EventChangeListener() {
        db.collection("NewPost").whereEqualTo("title",title)
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
                                newPostArrayList.add(dc.getDocument().toObject(NewPost.class));
                            }
                            myAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    // EventCommentChangeListener get the Comment from the Firestore database every time that user call the function and save the data in the newCommentArrayList
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