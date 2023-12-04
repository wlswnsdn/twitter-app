package com.example.myapplication.dto;

public class CommentDTO {
    private int comment_id;
    private int post_id;
    private int user_id;
    private String comment_content;
    private int comment_like_cnt;
    private long comment_time;

    public CommentDTO() {
    }

    public CommentDTO(int comment_id, int post_id, int user_id, String comment_content, int comment_like_cnt, long comment_time) {
        this.comment_id = comment_id;
        this.post_id = post_id;
        this.user_id = user_id;
        this.comment_content = comment_content;
        this.comment_like_cnt = comment_like_cnt;
        this.comment_time = comment_time;
    }

    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getComment_content() {
        return comment_content;
    }

    public void setComment_content(String comment_content) {
        this.comment_content = comment_content;
    }

    public int getComment_like_cnt() {
        return comment_like_cnt;
    }

    public void setComment_like_cnt(int comment_like_cnt) {
        this.comment_like_cnt = comment_like_cnt;
    }

    public long getComment_time() {
        return comment_time;
    }

    public void setComment_time(long comment_time) {
        this.comment_time = comment_time;
    }
}
