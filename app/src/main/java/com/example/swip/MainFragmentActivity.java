package com.example.swip;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;

public class MainFragmentActivity extends Fragment { // Fragment 상속
    RecyclerView recyclerView;
    NoteAdapterActivity adapter;
    Context context;
    SwipeRefreshLayout swipeRefreshLayout;

    @SuppressLint("Range")
    public int loadNoteListData(){ // 투두리스트의 아이템들이 보이게 함
        String loadSql = "select _id, TODO from " + NoteDatabaseActivity.TABLE_NOTE + " order by _id desc"; // 데이터를 가지고 옴

        int recordCount = -1;
        NoteDatabaseActivity database = NoteDatabaseActivity.getInstance(context);

        if(database != null){
            Cursor outCursor = database.rawQuery(loadSql);
            recordCount = outCursor.getCount();
            ArrayList<NoteActivity> items = new ArrayList<>();

            for(int i = 0; i < recordCount; i++){
                outCursor.moveToNext();

                int _id = outCursor.getInt(0);
                String todo = outCursor.getString(1);
                items.add(new NoteActivity(_id,todo));
            }

            outCursor.close();

            adapter.setItems(items);
            adapter.notifyDataSetChanged();
        }

        return recordCount;
    }

    // 프래그먼트가 생성된 이후에 fragment_main.xml 호출
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_main, container, false);

        initUI(rootView);
        loadNoteListData();

        // 새로고침을 통해 아이템의 생성, 삭제
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadNoteListData();
                swipeRefreshLayout.setRefreshing(true);
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });

        return rootView;
    }

    private void initUI(ViewGroup rootView){
        recyclerView = rootView.findViewById(R.id.recyclerView); // RecyclerView 연결

        // 투두 리스트 아이템들이 세로로 정렬
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        // 어댑터 연결
        adapter = new NoteAdapterActivity();
        recyclerView.setAdapter(adapter);
    }
}

