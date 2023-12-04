package com.example.myapplication.dto;

public class LikeDTO {
    private int post_like_id;
    private int liker_id;
    private int post_id;

    public LikeDTO() {
    }

    public int getPost_like_id() {
        return post_like_id;
    }

    public void setPost_like_id(int post_like_id) {
        this.post_like_id = post_like_id;
    }

    public int getLiker_id() {
        return liker_id;
    }

    public void setLiker_id(int liker_id) {
        this.liker_id = liker_id;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }
}
