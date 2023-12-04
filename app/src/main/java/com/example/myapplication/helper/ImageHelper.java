package com.example.myapplication.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.myapplication.ILoginWithThis;

public class ImageHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "sample.db";
    private static final int DATABASE_VERSION = 1;

    private ILoginWithThis iLoginWithThis;


    public ImageHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

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

    public boolean addImage(byte[] imageBytes) {
        SQLiteDatabase db = this.getWritableDatabase();
        iLoginWithThis = new ILoginWithThis();
        // 내 로그인 id -> user_id(auto_increment되는)
        int myUser_id = login_idToUser_id(iLoginWithThis.getMyId());
        ContentValues values = new ContentValues();
        values.put("user_id", myUser_id);
        values.put("image", imageBytes);
        long result = db.insert("userImage", null, values);
        db.close();

        return result != -1;

    }

    public byte[] getMyImage() {
        SQLiteDatabase db = this.getReadableDatabase();
        iLoginWithThis = new ILoginWithThis();
        int myUser_id = login_idToUser_id(iLoginWithThis.getMyId());
        String query = "select image from userImage where user_id=?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(myUser_id)});
        cursor.moveToFirst();
        byte[] image = cursor.getBlob(0);
        return image;

    }

    public byte[] getImage(int user_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select image from userImage where user_id=?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(user_id)});
        cursor.moveToFirst();
        byte[] image = cursor.getBlob(0);
        return image;
    }
}
