package com.example.myapplication;

import com.example.myapplication.helper.UserHelper;
import com.example.myapplication.user.User;

public class SearchItem {
    private int userProfileImgResId;
    private String userName;

    //Boolean isAlreadyFollow;

    public SearchItem(int profileImageResource, String userName, Boolean isAlreadyFollow) {
        this.userProfileImgResId = profileImageResource;
        this.userName = userName;
        //this.isAlreadyFollow = isAlreadyFollow;

    }

    public int getUserProfileImgResId() {
        return userProfileImgResId;
    }

    public String getUserName() {
        return userName;
    }

//    public Boolean getIsAlreadyFollow() {
//
//        return isAlreadyFollow;
//    }


}
