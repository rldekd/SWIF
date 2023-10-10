package com.example.swip;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class NoteDatabaseActivity {
    // 데이터베이스
    private static final String TAG = "NoteDatabase";

    private static NoteDatabaseActivity database;
    public static String DATABASE_NAME = "todo.db";
    public static String TABLE_NOTE = "NOTE";
    public static int DATABASE_VERSION = 1;

    private Context context;
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;

    private NoteDatabaseActivity(Context context){
        this.context = context;
    }

    public static NoteDatabaseActivity getInstance(Context context){
        if(database == null){
            database = new NoteDatabaseActivity(context);
        }

        return database;
    }

    public Cursor rawQuery(String SQL){ // 리스트를 화면에 표시할 때 현재 위치 및 이동을 나타냄

        Cursor c1 = null;
        try{
            c1 = db.rawQuery(SQL,null);
        } catch (Exception ex){
            Log.e(TAG,"Exception in rawQuery",ex);
        }

        return c1;
    }

    public boolean execSQL(String SQL) { // sql문 실행

        try {
            Log.d(TAG, "SQL : " + SQL);
            db.execSQL(SQL);
        } catch(Exception ex) {
            Log.e(TAG, "Exception in execSQL", ex);
            return false;
        }
        return true;
    }

    public boolean open(){ // 데이터베이스 생성

        dbHelper = new DatabaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = dbHelper.getWritableDatabase();

        return true;
    }

    public void close(){
        db.close();
        database = null;
    }
    private class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
            super(context, name,factory,version);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {

            String DROP_SQL = "drop table if exists " +TABLE_NOTE; // 투두리스트 아이템 삭제

            try {
                db.execSQL(DROP_SQL);

            } catch (Exception ex){
                Log.e(TAG, "Exception in DROP_SQL", ex);
            }

            String CREATE_SQL = "create table " + TABLE_NOTE + "("
                    + " _id integer NOT NULL PRIMARY KEY AUTOINCREMENT, "
                    + "  TODO TEXT DEFAULT '' "
                    + ")"; // 투두리스트 아이템 생성
            try{
                db.execSQL(CREATE_SQL);
            } catch (Exception ex){
                Log.e(TAG,"Exception in CREATE_SQL", ex);
            }

            String CREATE_INDEX_SQL = "create index " + TABLE_NOTE + "_IDX ON " + TABLE_NOTE + "("
                    + "_id"
                    + ")"; // 아이템 순서 생성
            try{
                db.execSQL(CREATE_INDEX_SQL);
            } catch (Exception ex){
                Log.e(TAG, "Exception in CREATE_INDEX_SQL",ex);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }

}
