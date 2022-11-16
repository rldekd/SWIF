package com.example.swip;

public class MyInfoActivity {
    public static String profileImageUrl;
    public static String nick;
    public static String uid;
    //사용자 기본 정보


    public MyInfoActivity(String nick, String profileImageUrl, String uid){
        this.nick = nick;
        this.profileImageUrl = profileImageUrl;
        this.uid = uid;
    }

    public MyInfoActivity() {

    }

    public String getNick(){
        return this.nick;
    }
    public void setNick(String nick){
        this.nick = nick;
    }

    public String getprofileImageUrl(){
        return this.profileImageUrl;
    }
    public void setprofileImageUrl(String profileImageUrl){
        this.profileImageUrl = profileImageUrl;
    }

    //public String pushToken;

}
