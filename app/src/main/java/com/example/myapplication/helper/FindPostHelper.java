package com.example.myapplication.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.ILoginWithThis;
import com.example.myapplication.dto.PostDTO;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class FindPostHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "sample.db";
    private static final int DATABASE_VERSION = 1;

    private ILoginWithThis iLoginWithThis;


    public FindPostHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    public int numPost(int myUser_id){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "select * from post where user_id=? order by create_time desc";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(myUser_id)});

        int count = cursor.getCount();
        return count;
    }

    public List<String> onePost(int myUser_id){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "select * from post where user_id=? order by create_time desc";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(myUser_id)});
        List<String> contentList = new ArrayList<>();
        while (cursor.moveToNext()) {
            String content = cursor.getString(6);
            contentList.add(content);
        }
        return contentList;
    }

    public PostDTO findWithPostId(int post_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "select * from post where post_id=?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(post_id)});
        cursor.moveToFirst();
        PostDTO postDTO = new PostDTO();
        postDTO.setPost_id(cursor.getInt(0));
        postDTO.setUser_id(cursor.getInt(1));
        postDTO.setTag_user_id(cursor.getInt(2));
        postDTO.setPostlike_cnt(cursor.getInt(3));
        postDTO.setCreate_time(cursor.getLong(4));
        postDTO.setRetweet_cnt(0);
        postDTO.setPostcontent(cursor.getString(6));
        postDTO.setPost_img(cursor.getString(7));
        cursor.close();
        return postDTO;
    }
    public List<PostDTO> findMyPost(int myUser_id){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "select * from post where user_id=? order by create_time desc";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(myUser_id)});
        return getPostDTOS(cursor);
    }

    public int login_idToUser_id(String myLogin_Id) {
        SQLiteDatabase db = this.getReadableDatabase();

        // login_id -> user_id
        String query = "SELECT user_id FROM user WHERE login_id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{myLogin_Id});

        int user_id = -1; // 기본값으로 -1을 설정하거나, 다른 특별한 값으로 초기화

        if (cursor.moveToFirst()) {
            // 결과가 있을 때 커서 이동 후 값 가져오기
            int columnIndex = cursor.getColumnIndex("user_id");

            if (columnIndex != -1) {
                // 컬럼이 존재할 때만 값을 가져오기
                user_id = cursor.getInt(columnIndex);
            }
        }

        cursor.close();
        return user_id;
    }

    public ArrayList<Integer> getMyFollowing() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Integer> saveFollowing = new ArrayList<>();
        iLoginWithThis = new ILoginWithThis();
        int myUser_id = login_idToUser_id(iLoginWithThis.getMyId());

        String query = "SELECT target_user_id FROM following WHERE following_id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(myUser_id)});
        System.out.println("myUser_id = " + myUser_id);
        if(cursor.moveToFirst()){
            int first = cursor.getInt(0);
            System.out.println("first = " + first);
            saveFollowing.add(first);
            while (cursor.moveToNext()) {
                int result = cursor.getInt(0);
                System.out.println("added = " + result);
                saveFollowing.add(result);
            }
        }
        else{
            System.out.println("no following");
        }


        cursor.close();
        System.out.println("saveFollowing = " + saveFollowing);
        return saveFollowing;

    }

    public ArrayList<Integer> getMyFollower() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Integer> saveFollower = new ArrayList<>();
        iLoginWithThis = new ILoginWithThis();
        int myUser_id = login_idToUser_id(iLoginWithThis.getMyId());

        String query = "SELECT target_user_id FROM follower WHERE follower_id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(myUser_id)});
        System.out.println("myUser_id = " + myUser_id);

        if(cursor.moveToFirst()){
            int first = cursor.getInt(0);
            System.out.println("first = " + first);
            saveFollower.add(first);
            while (cursor.moveToNext()) {
                int result = cursor.getInt(0);
                System.out.println("added = " + result);
                saveFollower.add(result);
            }
        }
        else{
            System.out.println("no follower");
        }


        cursor.close();
        System.out.println("saveFollower = " + saveFollower);
        return saveFollower;

    }
//    public int getMyFollower() {
//        SQLiteDatabase db = this.getReadableDatabase();
//        ArrayList<Integer> saveFollower = new ArrayList<>();
//        iLoginWithThis = new ILoginWithThis();
//        int myUser_id = login_idToUser_id(iLoginWithThis.getMyId());
//
//        String query = "SELECT target_user_id FROM follower WHERE follower_id = ?";
//        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(myUser_id)});
//        System.out.println("myUser_id = " + myUser_id);
//        int count=0;
//        if(cursor.moveToFirst()){
//            count = cursor.getCount();
//        }
//        else{
//            System.out.println("no follower");
//        }
//
//
//        cursor.close();
//        System.out.println("saveFollower = " + saveFollower);
//        return count;
//
//    }



    public List<PostDTO> findMyFriendPost(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Integer> following_user = getMyFollowing();

        String user_ids = TextUtils.join(",", following_user);
        String query = "SELECT * FROM post WHERE user_id IN (" + user_ids + ") ORDER BY create_time DESC";
        Cursor cursor = db.rawQuery(query, null);
        return getPostDTOS(cursor);
    }



    @NonNull
    private List<PostDTO> getPostDTOS(Cursor cursor) {
        List<PostDTO> postDTOList = new ArrayList<>();
        while (cursor.moveToNext()) {
            PostDTO postDTO = new PostDTO();
            postDTO.setPost_id(cursor.getInt(0));
            postDTO.setUser_id(cursor.getInt(1));
            postDTO.setTag_user_id(cursor.getInt(2));
            postDTO.setPostlike_cnt(cursor.getInt(3));
            postDTO.setCreate_time(cursor.getLong(4));
            postDTO.setRetweet_cnt(0);
            postDTO.setPostcontent(cursor.getString(6));
            postDTO.setPost_img(cursor.getString(7));

            postDTOList.add(postDTO);
        }
        return postDTOList;
    }

}
