package com.example.myapplication.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class UserTableHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "sample.db";
    private static final int DATABASE_VERSION = 1;

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

    private static final String createPostLike = " create table post_like(\n" +
            "    post_like_id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "    liker_id INTEGER NOT NULL,\n" +
            "    post_id int NOT NULL,\n" +
            "    foreign key (liker_id) references user(user_id) on update cascade,\n" +
            "    foreign key (post_id) references post(post_id) on update cascade\n" +
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

    private static final String createImage = "create table userImage(\n" +
            "image_id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "user_id INTEGER NOT NULL,\n" +
            "image BLOB NOT NULL,\n" +
            "foreign key(user_id) references user(user_id)\n" +
            "on delete cascade);";


    public UserTableHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createUser);
        db.execSQL(createPost);
        db.execSQL(createComment);
        db.execSQL(createPostLike);
        db.execSQL(createFollowing);
        db.execSQL(createFollower);
        db.execSQL(createImage);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void simpleFunc(){
        SQLiteDatabase db = this.getWritableDatabase();

    }

}
