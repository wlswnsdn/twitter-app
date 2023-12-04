package com.example.myapplication;

public class ILoginWithThis {
    private static String myId;

    public ILoginWithThis(String myId) {
        this.myId = myId;
    }

    public ILoginWithThis() {
    }

    public String getMyId() {
        return myId;
    }

    public void setMyId(String myId) {
        this.myId = myId;
    }
}
