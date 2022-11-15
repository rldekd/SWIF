package com.example.swip;

public class MemberInfo {
    private String nick;
    private String birthDay;

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