package com.example.swip;

public class Post_Model {
    public  String postTitle;
    public String postContent;
    public String imageUri;
    private String key;
    private String ID;


    public Post_Model(String postTitle, String postContent, String imageUri,  String key, String ID) {
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.imageUri = imageUri;

        this.key = key;
        this.ID = ID;

    }

    public Post_Model() {

    }

    public Post_Model(String mpostTitle, String mpostContent, String sImage) {
        this.postTitle = mpostTitle;
        this.postContent = mpostContent;
        this.imageUri = sImage;


    }






    public String getPostTtile() {
        return postTitle;
    }

    public void setPostTtile(String postTtile) {
        this.postTitle = postTtile;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }



    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }


}
