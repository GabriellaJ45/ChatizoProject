package com.example.gabriella.chatizoproject;

/**
 * Created by yungbena on 4/23/17.
 */

public class Contact {
    private String id1;
    private String nickname1;

    public Contact(String id, String nickname){
        this.id1 = id;
        this.nickname1 = nickname;
    }

    public String getId1() {
        return id1;
    }

    public void setId1(String id1) {
        this.id1 = id1;
    }

    public String getNickname1() {
        return nickname1;
    }

    public void setNickname1(String nickname1) {
        this.nickname1 = nickname1;
    }

    @Override
    public String toString() {
        if(getNickname1().equals(""))
            return getId1();
        return this.nickname1;
    }
}
