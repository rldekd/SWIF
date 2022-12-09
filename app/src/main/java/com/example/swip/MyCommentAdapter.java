package com.example.swip;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.swip.NewComment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MyCommentAdapter extends RecyclerView.Adapter<MyCommentAdapter.MyViewHolder> {


    Context context;
    ArrayList<NewComment> newCommentArrayList;
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public MyCommentAdapter(Context context, ArrayList<NewComment> newCommentArrayList) {
        this.context = context;
        this.newCommentArrayList = newCommentArrayList;
    }

    @NonNull
    @Override
    public MyCommentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.comment,parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyCommentAdapter.MyViewHolder holder, int position) {

        NewComment newComment = newCommentArrayList.get(position);


        // Assign the variable in the Card View
        holder.comment.setText(newComment.getComment());
        holder.uid.setText(newComment.getUid());
        holder.delete.setOnClickListener(new View.OnClickListener() {


            // Code to Delete the written post, remove it from the firebase database
            // check if the user UID match with the post uploader UID
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                // show the AlertDialog for warn user that deleted post cannot be restored
                builder.setTitle("댓글을 삭제하시겠습니까?");
                builder.setMessage("한번 삭제시 되돌릴 수 없습니다");
                builder.setPositiveButton("네", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if(user.getUid().toString().equals(newComment.getUid())) {

                            // Check the writerUid of the Post and Content of the comment to specify the target comment
                            db.collection("NewComment").whereEqualTo("writerUid", newComment.getWriterUid())
                                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                                        db.collection("NewComment")
                                                .whereEqualTo("comment", newComment.getComment())
                                                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                                if (task.isSuccessful() && !task.getResult().isEmpty()) {
                                                    DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                                                    String documentID = documentSnapshot.getId();
                                                    db.collection("NewComment").document(documentID).delete()
                                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void unused) {
                                                                    Toast.makeText(context, "삭제되었습니다.", Toast.LENGTH_LONG).show();
                                                                    newCommentArrayList.remove(position);
                                                                    notifyItemRemoved(position);
                                                                    notifyItemRangeChanged(position, newCommentArrayList.size());
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
        return newCommentArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView comment,uid;
        ImageView delete;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            comment = itemView.findViewById(R.id.comment_card);
            uid = itemView.findViewById(R.id.user_id);
            delete = itemView.findViewById(R.id.delete_comment);
        }
    }
}
