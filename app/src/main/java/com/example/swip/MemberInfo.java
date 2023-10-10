package com.example.swip;

public class MemberInfo { // 회원 정보 기입 모델
    private String nick; // 닉네임
    private String birthDay; // 생일

    public MemberInfo(String nick, String birthDay){
        this.nick = nick;
        this.birthDay = birthDay;
    }

    public String getNick(){
        return this.nick;
    }
    public void setNick(String name){
        this.nick = nick;
    }
    public String getBirthDay(){
        return this.birthDay;
    }
    public void setBirthDay(String birthDay){
        this.birthDay = birthDay;
    }
}