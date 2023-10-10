package com.example.swip;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class NoticeCustomAdapter extends RecyclerView.Adapter<NoticeCustomAdapter.ViewHolder> {

    NoticeActivity activity;
    List<NoticeModel> list;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public NoticeCustomAdapter(List<NoticeModel> list, NoticeActivity activity) {
        this.activity = activity;
        this.list = list;

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

        TextView  name, message;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textViewName);
            message = itemView.findViewById(R.id.textViewMessage);
        }



    }
}
