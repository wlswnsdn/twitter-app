package com.example.myapplication.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.myapplication.ILoginWithThis;
import com.example.myapplication.dto.UserDTO;
import com.example.myapplication.user.User;

public class UserHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "sample.db";
    private static final int DATABASE_VERSION = 1;

    private ILoginWithThis iLoginWithThis;

    public UserHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public int updateUser(String name,String description,String email,String password   ) {
        SQLiteDatabase db = this.getWritableDatabase();
        iLoginWithThis = new ILoginWithThis();

        String query = "update user set username=?,user_description=?,email=?,pw=? where login_id=?";
        Cursor cursor = db.rawQuery(query, new String[]{name, description,email,password,iLoginWithThis.getMyId()});
        int count = cursor.getCount();
        return count;
    }

    public UserDTO getUser(int user_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from user where user_id=?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(user_id)});
        cursor.moveToFirst();
        UserDTO userDTO = new UserDTO();
        userDTO.setUser_id(cursor.getInt(0));
        userDTO.setLogin_id(cursor.getString(1));
        userDTO.setEmail(cursor.getString(5));
        userDTO.setUser_description(cursor.getString(7));
        cursor.close();
        return userDTO;
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


    public boolean addFollowing(int target_id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        iLoginWithThis = new ILoginWithThis();
        int myUser_id = login_idToUser_id(iLoginWithThis.getMyId());
        values.put("following_id", myUser_id);
        values.put("target_user_id", target_id);

        long result = db.insert("following", null, values);
        db.close();

        return result != -1;
    }

    public void deleteFollowing(int following_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "delete from following where target_user_id=?";
        db.execSQL(query,new Object[]{following_id});
    }

    public boolean checkFollowing(int target_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from following where target_user_id=?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(target_id)});
        return cursor.getCount() > 0;

    }

    public boolean addFollower(int target_id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        iLoginWithThis = new ILoginWithThis();
        int myUser_id = login_idToUser_id(iLoginWithThis.getMyId());
        values.put("follower_id", target_id);
        values.put("target_user_id", myUser_id);

        long result = db.insert("follower", null, values);
        db.close();

        return result != -1;
    }

    public void deleteFollower(int target_user_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        iLoginWithThis = new ILoginWithThis();
        String query = "delete from follower where follower_id=? and target_user_id=?";
        db.execSQL(query, new Object[]{String.valueOf(target_user_id), String.valueOf(iLoginWithThis.getMyId())});
    }
}
