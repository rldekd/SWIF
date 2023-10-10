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


            // 댓글을 삭제하고 파이어베이스 내의 댓글 데이터를 삭제하는 코드
            // 사용자 UID가 게시물 업로더 UID와 일치하는지 확인
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context); // 다이얼로그 생성

                // show the AlertDialog for warn user that deleted post cannot be restored
                builder.setTitle("댓글을 삭제하시겠습니까?"); // 삭제 메시지 제목 정의
                builder.setMessage("한번 삭제시 되돌릴 수 없습니다"); // 삭제 메시지 정의
                builder.setPositiveButton("네", new DialogInterface.OnClickListener() { // 삭제 메시지 버튼 정의

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) { // 네 버튼을 클릭한 경우

                        if(user.getUid().toString().equals(newComment.getUid())) { // 유저 아이디와 댓글 아이디가 같다면

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
                                                    db.collection("NewComment").document(documentID).delete() // 댓글 삭제 실행
                                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void unused) {
                                                                    Toast.makeText(context, "삭제되었습니다.", Toast.LENGTH_LONG).show(); // 완료 메시지 출력
                                                                    newCommentArrayList.remove(position); // 댓글 삭제
                                                                    notifyItemRemoved(position); // 댓글 삭제
                                                                    notifyItemRangeChanged(position, newCommentArrayList.size()); // 댓글 사이즈 다시 정의
                                                                }
                                                            });
                                                }

                                            }


                                        });
                                    } else {
                                        Toast.makeText(context, "작성자만 삭제할 수 있습니다.", Toast.LENGTH_LONG).show(); // 댓글 id과 유저 id가 같지 않은 경우
                                    }
                                }
                            });
                        }
                        else {
                            Toast.makeText(context, "작성자만 삭제할 수 있습니다.", Toast.LENGTH_LONG).show(); // 댓글 id과 유저 id가 같지 않은 경우
                        }

                    }
                });

                builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() { // 아니오 버튼을 눌렀으 경우
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // 아무런 일도 일어나지 않는다.
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
        public MyViewHolder(@NonNull View itemView) { // 각 xml에 맞는 레이아웃 정의
            super(itemView);
            comment = itemView.findViewById(R.id.comment_card);
            uid = itemView.findViewById(R.id.user_id);
            delete = itemView.findViewById(R.id.delete_comment);
        }
    }
}
