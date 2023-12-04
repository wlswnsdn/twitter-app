package com.example.myapplication;

import android.graphics.Bitmap;

public class PostItem {
    private Bitmap profileImageResource;
    private String userName;
    private String postContent;
    private int likeCount;
    private int commentCount;
    private Boolean isEditBtnVisible;

    private int postId;


    public PostItem(int postId) {
        this.postId = postId;
    }

    public PostItem(Bitmap profileImageResource, String userName, String postContent, int likeCount, int commentCount, Boolean isEditBtnVisible) {
        this.profileImageResource = profileImageResource;
        this.userName = userName;
        this.postContent = postContent;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.isEditBtnVisible = isEditBtnVisible;
    }

    public Bitmap getProfileImageResource() {
        return profileImageResource;
    }

    public String getUserName() {
        return userName;
    }

    public String getPostContent() {
        return postContent;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public int getCommentCount() {
        return commentCount;
    }
    public Boolean getIsEditBtnVisible() {return isEditBtnVisible; }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }
}
