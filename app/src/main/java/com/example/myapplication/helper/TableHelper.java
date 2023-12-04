package com.example.myapplication.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import java.util.ArrayList;

public class TableHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "sample.db";
    private static final int DATABASE_VERSION = 1;

    public TableHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String createUser = "CREATE table user(\n" +
            "    user_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
            "    login_id varchar(30) unique not null,\n" +
            "    pw VARCHAR(255) NOT NULL,\n" +
            "    profile_img VARCHAR(255),\n" +
            "    username VARCHAR(60) NOT NULL,\n" +
            "    email VARCHAR(30) NOT NULL, \n" +
            "    interesting VARCHAR(20),\n" +
            "    user_description varchar(255)\n" +
            "); ";

    private static final String createPost = "CREATE table post (\n" +
            "  post_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ,\n" +
            "  user_id INTEGER NOT NULL,\n" +
            "  tag_user_id INTEGER,\n" +
            "  postlike_cnt INT,\n" +
            "  create_time TIMESTAMP NOT NULL,\n" +
            "  retweet_cnt INT,\n" +
            "  postcontent VARCHAR(300),\n" +
            "  post_img VARCHAR(255),\n" +
            "  foreign key (user_id) references user(user_id) on delete no action on update cascade,\n" +
            "  foreign key (tag_user_id) references user(user_id)\n" +
            "  on delete no action on update cascade\n" +
            " );";

    private static final String createComment = "create table comment (\n" +
            "    comment_id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "    post_id INTEGER NOT NULL,\n" +
            "    user_id INTEGER NOT NULL,\n" +
            "    comment_content varchar(300) NOT NULL,\n" +
            "    comment_like_cnt INT,\n" +
            "    comment_time timestamp NOT NULL,\n" +
            "    foreign key (post_id) references post(post_id) on update cascade,\n" +
            "    foreign key (user_id) references user(user_id) on update cascade\n" +
            ");";

    private static final String createchildComment = "create table child_comment (\n" +
            "    child_comment_id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "    parent_comment_id INTEGER NOT NULL,\n" +
            "    child_comment_content varchar(255) NOT NULL,\n" +
            "    child_comment_like_cnt INT,\n" +
            "    child_comment_time timestamp NOT NULL,\n" +
            "    foreign key(parent_comment_id) references comment(comment_id) on update cascade\n" +
            ");";

    private static final String createPostLike = " create table post_like(\n" +
            "    post_like_id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "    liker_id INTEGER NOT NULL,\n" +
            "    post_id int NOT NULL,\n" +
            "    foreign key (liker_id) references user(user_id) on update cascade,\n" +
            "    foreign key (post_id) references post(post_id) on update cascade\n" +
            ");";

    private static final String createcommentLike = "create table comment_like(\n" +
            "    comment_like_id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "    liker_id INTEGER NOT NULL,\n" +
            "    comment_id INTEGER NOT NULL,\n" +
            "    foreign key (liker_id) references user(user_id) on update cascade,\n" +
            "    foreign key (comment_id) references comment(comment_id) on update cascade\n" +
            ");";

    private static final String createFollowing = "create table following(\n" +
            "    follow_id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "    following_id INTEGER NOT NULL,\n" +
            "    target_user_id INTEGER NOT NULL,\n" +
            "    foreign key (following_id) references user(user_id)\n" +
            "    on update cascade\n" +
            ");";

    private static final String createFollower = "create table follower(\n" +
            "   follow_id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "   follower_id INTEGER NOT NULL, \n" +
            "    target_user_id INTEGER NOT NULL,\n" +
            "    foreign key (target_user_id) references user(user_id)\n" +
            "    on update cascade,\n" +
            "    foreign key (follower_id) references user(user_id)\n" +
            "    on update cascade\n" +
            "    );";

    private static final String createBan = "create table ban(\n" +
            "ban_id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "iban INT not null,\n" +
            "youbanned INT not null,\n" +
            "foreign key(iban) references user(user_id)\n" +
            "on update cascade,\n" +
            "foreign key(youbanned) references user(user_id) on update cascade);";



    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL(createUser);
        db.execSQL(createPost);
        db.execSQL(createComment);
        db.execSQL(createchildComment);
        db.execSQL(createPostLike);
        db.execSQL(createcommentLike);
        db.execSQL(createFollowing);
        db.execSQL(createFollower);
        db.execSQL(createBan);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public ArrayList<String> getUserUsernames() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT username FROM user", null);
        ArrayList<String> usernames = new ArrayList<>();
        while (cursor.moveToNext()) {
            usernames.add(cursor.getString(0));
        }
        cursor.close();
        return usernames;
    }

    public boolean addUser(String loginId, String password, String username, String email, String userDescription) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("login_id", loginId);
        values.put("pw", password);
        values.put("profile_img", "0");
        values.put("username", username);
        values.put("email", email);
        values.put("interesting", "0");
        values.put("user_description", userDescription);

        long result = db.insert("user", null, values);
        db.close();

        return result != -1;
    }

    public boolean addFollowing(int follower_id, int target_id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("following_id", follower_id);
        values.put("target_user_id", target_id);

        long result = db.insert("following", null, values);
        db.close();

        return result != -1;
    }

    public boolean isUserExists(String loginId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM user WHERE login_id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{loginId});

        boolean exists = cursor.moveToFirst();
        cursor.close();
        db.close();

        return exists;
    }


    public int insertSampleData() {
        //addUser("user1", "pass1", "img1", "Username1", "email1@example.com", "interest1", "desc1");
        //addUser("user2", "pass2", "img2", "Username2", "email2@example.com", "interest2", "desc2");
        addFollowing(14, 15);
        addFollowing(14, 16);
        return 1;
        // 일단 대충 넣어본것
    }

    public void simpleFunc(){
        SQLiteDatabase db = this.getWritableDatabase();
    }

}

