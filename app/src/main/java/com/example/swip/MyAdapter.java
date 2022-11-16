package com.example.swip;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

    Context context;
    View v;
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    ArrayList<NewPost> newPostArrayList;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public MyAdapter(Context context, ArrayList<NewPost> newPostArrayList) {
        this.context = context;
        this.newPostArrayList = newPostArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        v = LayoutInflater.from(context).inflate(R.layout.newcard,parent,false);
        return new MyViewHolder(v);
    }

    public String id;

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {



        NewPost newpost = newPostArrayList.get(position);

        // Assign the variable in the Card View
        holder.title_card.setText(newpost.getTitle());
        holder.subtitle_card.setText(newpost.getSubtitle());
        holder.content_card.setText(newpost.getContent());
        holder.time_card.setText(newpost.getTime());
        holder.tag.setText(newpost.getTag());

        // If click the each card view , move to new Activity that can see comment of the post
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Post_Detail.title=newpost.getTitle();
                Post_Detail.WriterUid = newpost.getUid()+newpost.getTime();

                Intent intent = new Intent(v.getContext(), Post_Detail.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.getContext().startActivity(intent);

            }
        });


        // Code to Delete the written post, remove it from the firebase database
        // check if the user UID match with the post uploader UID
        holder.delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("정말 삭제하시겠습니까?");
                builder.setMessage("한번 삭제시 되돌릴 수 없습니다");
                builder.setPositiveButton("네", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if(user.getUid().toString().equals(newpost.getUid())) {

                            // Check the user UID to allow only writer delete the post
                            db.collection("NewPost").whereEqualTo("uid", user.getUid())
                                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                                        db.collection("NewPost")
                                                .whereEqualTo("title", newpost.getTitle())
                                                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful() && !task.getResult().isEmpty()) {
                                                    DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                                                    String documentID = documentSnapshot.getId();
                                                    db.collection("NewPost").document(documentID).delete()
                                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void unused) {
                                                                    Toast.makeText(context, "삭제되었습니다.", Toast.LENGTH_LONG).show();
                                                                    newPostArrayList.remove(position);
                                                                    notifyItemRemoved(position);
                                                                    notifyItemRangeChanged(position, newPostArrayList.size());
                                                                }
                                                            });
                                                }

                                            }


                                        });
                                    } else {
                                        Toast.makeText(context, "작성자만 삭제할 수 있습니다.", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                        else {
                            Toast.makeText(context, "작성자만 삭제할 수 있습니다.", Toast.LENGTH_LONG).show();
                        }

                    }
                });

                builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                AlertDialog ad = builder.create();
                ad.show();

            }
        });


    }


    @Override
    public int getItemCount() {
        return newPostArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView title_card;
        TextView subtitle_card,content_card;
        TextView time_card;
        TextView tag;
        static ImageView delete;
        String docTitle;
        TextView uid;
        CardView card;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title_card = itemView.findViewById(R.id.title_card);
            subtitle_card = itemView.findViewById(R.id.subtitle_card);
            content_card = itemView.findViewById(R.id.content_card);
            time_card = itemView.findViewById(R.id.content_time);
            delete = itemView.findViewById(R.id.delete_card);
            card = itemView.findViewById(R.id.card_view);

        }
    }
}
