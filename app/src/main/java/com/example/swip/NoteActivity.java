package com.example.swip;

public class NoteActivity {
    int _id;
    String todo;

    public NoteActivity(int _id, String todo){
        this._id = _id;
        this.todo = todo;
    }

    public int get_id() {
        return _id;
    } // get_id()는 _id를 가지고 옴

    public void set_id(int _id) {
        this._id = _id;
    } // set_id()는 _id를 설정함

    public String getTodo() {
        return todo;
    } // getTodo()는 todo를 가지고 옴

    public void setTodo(String todo) {
        this.todo = todo;
    } // setTodo()는 todo를 가지고 옴
}
