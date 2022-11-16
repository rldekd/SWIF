package com.example.swip;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class PostList extends AppCompatActivity {
    public static final int REQUEST_CODE_ADD_NOTE =1;
    RecyclerView recyclerView;
    ArrayList<NewPost> newPostArrayList;
    MyAdapter myAdapter;
    FirebaseFirestore db;
    static int first = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_list);

        ProgressDialog progressDialog = new ProgressDialog(this);

        recyclerView = findViewById(R.id.noteRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db= FirebaseFirestore.getInstance();
        newPostArrayList = new ArrayList<NewPost>();
        myAdapter = new MyAdapter(PostList.this,newPostArrayList);
        recyclerView.setAdapter(myAdapter);
        newPostArrayList.clear();
        EventChangeListener();

        ImageView imageAddNoteMain = findViewById(R.id.imageAddNoteMain);

        // if user click the + Button , he or she move to writing post activity
        imageAddNoteMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getApplicationContext(),Post.class),
                        REQUEST_CODE_ADD_NOTE);
            }
        });

        ImageView imageCheckMine = findViewById(R.id.imageAddLink);

        // if user check my profile button , move to activity that can only see the post they have written
        imageCheckMine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getApplicationContext(),CheckMyPost.class),
                        REQUEST_CODE_ADD_NOTE);
            }
        });



    }

    // EventChangeListener get the Post from the Firestore database every time
    // that user call the function and save the data in the newPostArrayList
    private void EventChangeListener() {
        db.collection("NewPost").orderBy("time", Query.Direction.ASCENDING)
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
}