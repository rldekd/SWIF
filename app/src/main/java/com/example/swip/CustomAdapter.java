package com.example.swip;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    ShowMessage activity;
    List<Model> list;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public CustomAdapter(List<Model> list, ShowMessage activity) {
        this.activity = activity;
        this.list = list;

    }



    public void updateData(int position) {
        Model model = list.get(position);

        Bundle bundle = new Bundle();
        bundle.putString("id", model.getId());
        bundle.putString("name", model.getName());
        bundle.putString("message", model.getMessage());

        Intent intent = new Intent(activity, Insert.class);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    public void deleteData(int position) {
        Model model = list.get(position);

        db.collection("초등학생").document(model.id).delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            notifyRemoved(position);
                            Toast.makeText(activity, "게시글을 삭제했습니다.", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(activity, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void notifyRemoved(int position) {
        list.remove(position);
        notifyItemRemoved(position);
        activity.showData();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(activity).inflate(R.layout.message_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.name.setText(list.get(position).getName());
        holder.message.setText(list.get(position).getMessage());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, message;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.textViewName);
            message = itemView.findViewById(R.id.textViewMessage);
        }



    }
}
