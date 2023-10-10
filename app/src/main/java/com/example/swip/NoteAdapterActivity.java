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

public class NoteAdapterActivity extends RecyclerView.Adapter<NoteAdapterActivity.ViewHolder>{
    private static final String TAG = "NoteAdapter";

    ArrayList<NoteActivity> items = new ArrayList<NoteActivity>(); // 아이템이 들어갈 배열

    static class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout layoutTodo;
        CheckBox checkBox;
        Button deleteButton;

        public ViewHolder(View itemView){
            super(itemView);
            layoutTodo = itemView.findViewById(R.id.layoutTodo);
            checkBox = itemView.findViewById(R.id.checkBox);
            deleteButton = itemView.findViewById(R.id.deleteButton);

            deleteButton.setOnClickListener(new View.OnClickListener() { // deleteButton 클릭 시
                @Override
                public void onClick(View v) {
                    String TODO = (String) checkBox.getText();
                    deleteToDo(TODO); // 투두리스트의 아이템을 삭제
                    Toast.makeText(v.getContext(),"삭제되었습니다.",Toast.LENGTH_SHORT).show(); // 토스트메시지로 삭제되었습니다 출력
                }

                Context context;

                private void deleteToDo(String TODO){ // 테이블 삭제
                    String deleteSql = "delete from " + NoteDatabaseActivity.TABLE_NOTE + " where " + "  TODO = '" + TODO+"'";
                    NoteDatabaseActivity database = NoteDatabaseActivity.getInstance(context);
                    database.execSQL(deleteSql);
                }
            });
        }

        // EditText에서 입력받은 checkBox의 텍스트를 checkBox의 Text에 넣고, 아이템들을 담은 레이아웃을 보여줌
        public void setItem(NoteActivity item){checkBox.setText(item.getTodo());}
        public void setLayout(){layoutTodo.setVisibility(View.VISIBLE);
        }
    }

    public void setItems(ArrayList<NoteActivity> items){this.items = items;}

    // 아이템을 ViewGroup으로 보이게 함
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.todo_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NoteActivity item = items.get(position);
        holder.setItem(item);
        holder.setLayout();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
