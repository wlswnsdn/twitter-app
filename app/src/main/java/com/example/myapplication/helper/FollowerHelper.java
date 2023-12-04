package com.example.myapplication.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import java.util.ArrayList;

public class FollowerHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "sample.db";
    private static final int DATABASE_VERSION = 1;

    public FollowerHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Define your database schema here if needed
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Handle database upgrades here if needed
    }
    //입력받은 login_id를 user_id로 변환
    public int login_idToUser_id(String myLogin_Id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT user_id FROM user WHERE login_id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{myLogin_Id});

        int user_id = -1;
        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex("user_id");

            if (columnIndex != -1) {
                user_id = cursor.getInt(columnIndex);
            }
        }

        cursor.close();
        return user_id;
    }
    //변환된 login_id의 user_id 정보를 following, follower table에 insert
    public void following(String IdToFollowing, String IdToTarget){
        SQLiteDatabase db = this.getWritableDatabase();
        int ToFollowingID = login_idToUser_id(IdToFollowing);
        int ToTargetId = login_idToUser_id(IdToTarget);
        db.execSQL("INSERT INTO following(following_id, target_user_id) VALUES ('" + ToFollowingID + "','" + ToTargetId + "')");
        db.execSQL("INSERT INTO follower(follower_id, target_user_id) VALUES ('" + ToTargetId + "','" + ToFollowingID + "')");
    }
    //변환된 login_id의 user_id 정보를 following, follwer table에서 삭제
    public void unfollow(String IdToUnFollow, String IdToTarget){
        SQLiteDatabase db = this.getWritableDatabase();
        int ToUnfollowId = login_idToUser_id(IdToUnFollow);
        int ToTargetId = login_idToUser_id(IdToTarget);
        db.execSQL("DELETE FROM following WHERE following_id =" + ToUnfollowId + " AND target_user_id = " + ToTargetId);
        db.execSQL("DELETE FROM follower WHERE follower_id =" + ToTargetId + " AND target_user_id = " + ToUnfollowId);
    }
    //변환된 login_id의 user_id를 바탕으로 follower table에서 검색을 하고
    //정보를 list에 저장 후 user의 user_id와 비교해 일치하는 부분만 follower list에 login_id로 저장
    public ArrayList<String> getFollower(String login_user_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> SaveFollowerId = new ArrayList<>();

        int target_id = login_idToUser_id(login_user_id);

        String query = "SELECT follower_id FROM follower WHERE target_user_id = " + target_id;
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            String result = cursor.getString(0);
            SaveFollowerId.add(result);
        }
        cursor.close();

        ArrayList<String> follower = new ArrayList<>();
        for(int i = 0; i < SaveFollowerId.size(); i++){
            String tmp = SaveFollowerId.get(i);
            String query1 = "SELECT login_id FROM user WHERE user_id = " + tmp;
            Cursor cursor1 = db.rawQuery(query1, null);
            cursor1.moveToNext();
            String result = cursor1.getString(0);
            follower.add(result);
            cursor1.close();
        }

        return follower;
    }
    //변환된 login_id의 user_id를 바탕으로 following table에서 검색을 하고
    //정보를 list에 저장 후 user의 user_id와 비교해 일치하는 부분만 following list에 login_id로 저장
    public ArrayList<String> getFollowing(String login_user_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> SaveFollowingId = new ArrayList<>();

        int target_id = login_idToUser_id(login_user_id);

        String query = "SELECT following_id FROM following WHERE target_user_id = " + target_id;
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            String result = cursor.getString(0);
            SaveFollowingId.add(result);
        }
        cursor.close();

        ArrayList<String> following = new ArrayList<>();
        for(int i = 0; i < SaveFollowingId.size(); i++){
            String tmp = SaveFollowingId.get(i);
            String query1 = "SELECT login_id FROM user WHERE user_id = " + tmp;
            Cursor cursor1 = db.rawQuery(query1, null);
            cursor1.moveToNext();
            String result = cursor1.getString(0);
            following.add(result);
            cursor1.close();
        }
        return following;
    }

}
