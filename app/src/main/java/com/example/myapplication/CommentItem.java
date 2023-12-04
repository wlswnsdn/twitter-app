package com.example.myapplication;

import android.graphics.Bitmap;

public class CommentItem {
    private Bitmap profileImageResource;
    private String userName;
    private String commentContent;
//    private int likeCount;
//    private int commentCount;
//    private Boolean isEditBtnVisible;

    public CommentItem(Bitmap profileImageResource, String userName, String commentContent, int likeCount, int commentCount, Boolean isEditBtnVisible) {
        this.profileImageResource = profileImageResource;
        this.userName = userName;
        this.commentContent = commentContent;
//        this.likeCount = likeCount;
//        this.commentCount = commentCount;
//        this.isEditBtnVisible = isEditBtnVisible;
    }

    public Bitmap getProfileImageResource() {
        return profileImageResource;
    }

    public String getUserName() {
        return userName;
    }

    public String getCommentContent() {
        return commentContent;
    }

//    public int getLikeCount() {
//        return likeCount;
//    }
//
//    public int getCommentCount() {
//        return commentCount;
//    }
//    public Boolean getIsEditBtnVisible() {return isEditBtnVisible; }
}
