package com.example.swip;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder>{
    private static final String TAG = "NoteAdapter";
    // 할 일 어댑터

    // 아이템이 들어갈 배열 정의
    ArrayList<Note> items = new ArrayList<Note>();

    static class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout layoutTodo;
        CheckBox checkBox;
        Button deleteButton;

        public ViewHolder(View itemView){
            super(itemView);
            layoutTodo = itemView.findViewById(R.id.layoutTodo);
            checkBox = itemView.findViewById(R.id.checkBox);
            deleteButton = itemView.findViewById(R.id.deleteButton);

            deleteButton.setOnClickListener(new View.OnClickListener() { // 삭제 버튼 정의
                @Override
                public void onClick(View v) {
                    String TODO = (String) checkBox.getText();
                    deleteToDo(TODO);
                    Toast.makeText(v.getContext(),"삭제되었습니다.",Toast.LENGTH_SHORT).show();
                }

                Context context;

                private void deleteToDo(String TODO){ // 테이블 삭제
                    String deleteSql = "delete from " + NoteDatabase.TABLE_NOTE + " where " + "  TODO = '" + TODO+"'";
                    NoteDatabase database = NoteDatabase.getInstance(context);
                    database.execSQL(deleteSql);
                }
            });
        }

        // EditText에서 입력받은 checkBox의 텍스트를 checkBox의 Text에 넣고, 아이템들을 담은 레이아웃을 보여줌
        public void setItem(Note item){checkBox.setText(item.getTodo());}

        //
        public void setLayout(){layoutTodo.setVisibility(View.VISIBLE);
        }
    }

    // 배열에 있는 item들을 가리킴
    public void setItems(ArrayList<Note> items){this.items = items;}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.todo_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note item = items.get(position);
        holder.setItem(item);
        holder.setLayout();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}

