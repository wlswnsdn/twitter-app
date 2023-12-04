package com.example.myapplication.dto;

import java.sql.Timestamp;

public class PostDTO {
    private int post_id;
    private int user_id;
    private int tag_user_id;
    private int postlike_cnt;
    private int retweet_cnt;
    private String postcontent;
    private String post_img;

    private long create_time;

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
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

    public int getTag_user_id() {
        return tag_user_id;
    }

    public void setTag_user_id(int tag_user_id) {
        this.tag_user_id = tag_user_id;
    }

    public int getPostlike_cnt() {
        return postlike_cnt;
    }

    public void setPostlike_cnt(int postlike_cnt) {
        this.postlike_cnt = postlike_cnt;
    }

    public int getRetweet_cnt() {
        return retweet_cnt;
    }

    public void setRetweet_cnt(int retweet_cnt) {
        this.retweet_cnt = retweet_cnt;
    }

    public String getPostcontent() {
        return postcontent;
    }

    public void setPostcontent(String postcontent) {
        this.postcontent = postcontent;
    }

    public String getPost_img() {
        return post_img;
    }

    public void setPost_img(String post_img) {
        this.post_img = post_img;
    }
//post_id, user_id, tag_user_id, postlike_cnt, comment_id, create_time, retweet_cnt, postcontent, post_img


    public PostDTO() {

    }

    public PostDTO(int post_id, int user_id, int tag_user_id, int postlike_cnt, Long create_time, int retweet_cnt,  String postcontent, String post_img) {
        this.post_id=post_id;
        this.user_id = user_id;
        this.tag_user_id = tag_user_id;
        this.postlike_cnt = postlike_cnt;
        this.create_time=create_time;
        this.retweet_cnt = retweet_cnt;
        this.postcontent = postcontent;
        this.post_img = post_img;
    }
}
