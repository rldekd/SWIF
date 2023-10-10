package com.example.swip;

public class NewComment { // 댓글 모델 정의


    // Class for NewComment object , save uid, comment , Post WriterUid
    private String comment; // 댓글
    private String uid; // uid
    private String writerUid; // 작성자 아이디

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getWriterUid() {
        return writerUid;
    }

    public void setWriterUid(String writerUid) {
        this.writerUid = writerUid;
    }
}
