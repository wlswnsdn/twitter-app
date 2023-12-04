package com.example.myapplication.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextWatcher;

import androidx.annotation.Nullable;

import com.example.myapplication.ILoginWithThis;

public class SearchHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "sample.db";
    private static final int DATABASE_VERSION = 1;

    private ILoginWithThis iLoginWithThis;
    public SearchHelper(Context context) {
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
    //followerHelper의 기능과 일치
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
    //검색하고 있는 login_id가 following table에 존재하는지 확인하고
    //존재한다면 true, 존재하지 않으면 false 반환
    public boolean check(String SearchedId){
        iLoginWithThis = new ILoginWithThis();
        String loginUserId = iLoginWithThis.getMyId();

        int IdLoginUser = login_idToUser_id(loginUserId);
        int IdSearched = login_idToUser_id(SearchedId);

        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT target_user_id from following where following_id =" + IdSearched;
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            int temp = cursor.getInt(0);
            if(temp == IdLoginUser)
                return true;
        }
        return false;
    }
}
