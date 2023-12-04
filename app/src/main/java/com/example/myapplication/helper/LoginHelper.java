package com.example.myapplication.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class LoginHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "sample.db";
    private static final int DATABASE_VERSION = 1;

    public LoginHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    public boolean login(String inputID, String inputPW) {
        SQLiteDatabase db = this.getReadableDatabase();

        // 입력받은 ID와 PW를 사용하여 데이터베이스에서 일치하는 사용자 조회
        String query = "SELECT * FROM user WHERE login_id = ? AND pw = ?";
        Cursor cursor = db.rawQuery(query, new String[]{inputID, inputPW});

        // 결과가 있는지 확인
        boolean loginSuccessful = cursor.getCount() > 0;

        // 리소스 정리
        cursor.close();
        db.close();

        return loginSuccessful;
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
