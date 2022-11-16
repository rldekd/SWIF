package com.example.swip;

public class NewComment {


    // Class for NewComment object , save uid, comment , Post WriterUid
    private String comment;
    private String uid;
    private String writerUid;
    private String time;

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
