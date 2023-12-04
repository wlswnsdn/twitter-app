package com.example.myapplication;

import android.graphics.Bitmap;

public class FollowItem {
    // 이미지 리소스 ID 또는 이미지 URL을 저장하는 필드를 추가할 수 있습니다.
    private String userName;
    private String userDescription;
    private Bitmap profileImageResource;

    public FollowItem(String userName) {
        this.userName = userName;
    }

    public FollowItem(Bitmap profileImageResource, String userName, String userDescription) {
        this.profileImageResource = profileImageResource;
        this.userName = userName;
        this.userDescription = userDescription;

    }

    public String getUserName() {
        return userName;
    }

    public String getUserDescription() {
        return userDescription;
    }

    public Bitmap getProfileImageResource() {
        return profileImageResource;
    }
}

