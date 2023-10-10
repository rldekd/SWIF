package com.example.swip;

public class NoticeModel {

    String id;
    String name;
    String message;

    public NoticeModel(String id, String name, String message) {

        this.id = id;
        this.name = name;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Model{" +
                "name='" + name + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
