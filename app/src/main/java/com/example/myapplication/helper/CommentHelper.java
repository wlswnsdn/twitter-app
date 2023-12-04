package com.example.myapplication.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.myapplication.ILoginWithThis;
import com.example.myapplication.dto.CommentDTO;

import java.util.ArrayList;
import java.util.List;

public class CommentHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "sample.db";
    private static final int DATABASE_VERSION = 1;

    private ILoginWithThis iLoginWithThis;
    public CommentHelper(@Nullable Context context) {
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

    public boolean addComment(String commentContent, int post_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        iLoginWithThis = new ILoginWithThis();
        int myUser_id = login_idToUser_id(iLoginWithThis.getMyId());
        ContentValues values = new ContentValues();
        values.put("post_id", post_id);
        values.put("user_id", myUser_id);
        values.put("comment_content", commentContent);
        values.put("comment_like_cnt", 0);
        values.put("comment_time", System.currentTimeMillis());

        long result = db.insert("comment", null, values);
        db.close();
        return result != -1;
    }

    public List<CommentDTO> findCommentByPostId(int post_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "select * from comment where post_id=? order by comment_time desc";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(post_id)});
        return getCommentDTOS(cursor);
    }

    public int getCommentNum(int post_id){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "select * from comment where post_id=? order by comment_time desc";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(post_id)});
        return cursor.getCount();
    }
    private List<CommentDTO> getCommentDTOS(Cursor cursor) {
        List<CommentDTO> commentDTOList = new ArrayList<>();
        while (cursor.moveToNext()) {
            CommentDTO commentDTO = new CommentDTO();
            commentDTO.setComment_id(cursor.getInt(0));
            commentDTO.setPost_id(cursor.getInt(1));
            commentDTO.setUser_id(cursor.getInt(2));
            commentDTO.setComment_content(cursor.getString(3));
            commentDTO.setComment_like_cnt(0);
            commentDTO.setComment_time(cursor.getLong(5));
            commentDTOList.add(commentDTO);
        }
        return commentDTOList;
    }
}
