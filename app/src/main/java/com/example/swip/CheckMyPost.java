package com.example.swip;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

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

public class CheckMyPost extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<NewPost> newPostArrayList;
    MyAdapter myAdapter;
    FirebaseFirestore db;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_my_post);

        // Create recycler variable and assgin noteRecycerView
        recyclerView = findViewById(R.id.noteRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));




        // Create Firebase Firestore instance
        db = FirebaseFirestore.getInstance();

        // Create Array list to save the Post object
        newPostArrayList = new ArrayList<NewPost>();
        // get the Adapter to connect arraylist and recycler view
        myAdapter = new MyAdapter(CheckMyPost.this,newPostArrayList);
        recyclerView.setAdapter(myAdapter);



        EventChangeListener();
        ImageView imageBack=findViewById(R.id.imageBack);

        // if click the < back button go back to postlist class
        imageBack.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                startActivityForResult(new Intent(getApplicationContext(),PostList.class),
                        1);

            }
        });


    }


    // Add the data from the firestore to NewPost ArrayList
    private void EventChangeListener() {
        // Only get the post of logged user
        db.collection("NewPost").whereEqualTo("uid",user.getUid())
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
                                // save it in to array list
                                newPostArrayList.add(dc.getDocument().toObject(NewPost.class));
                            }
                            myAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }
}