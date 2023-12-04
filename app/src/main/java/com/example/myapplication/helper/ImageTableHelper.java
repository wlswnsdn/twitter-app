package com.example.myapplication.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ImageTableHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "sample.db";
    private static final int DATABASE_VERSION = 1;

    private static final String createImage = "create table userImage(\n" +
            "image_id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "user_id INTEGER NOT NULL,\n" +
            "image BLOB NOT NULL,\n" +
            "foreign key(user_id) references user(user_id)\n" +
            "on delete cascade);";


    public ImageTableHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        //db.execSQL(createImage);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void simpleFunc(){
        SQLiteDatabase db = this.getWritableDatabase();

    }

}
