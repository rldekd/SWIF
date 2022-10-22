package com.example.swip;

public class NoteActivity {
    // id 받고 todo 생성
    int _id;
    String todo;

    public NoteActivity(int _id, String todo){
        this._id = _id;
        this.todo = todo;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getTodo() {
        return todo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }
}
