package com.example.earringdiary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public DatabaseManager(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long insertPost(Post post) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_CONTENT, post.getPostContent());
        values.put(DatabaseHelper.COLUMN_IMAGE_PATH, post.getPostImagePath());
        values.put(DatabaseHelper.COLUMN_DATE, post.getPostDate());

        return database.insert(DatabaseHelper.TABLE_POSTS, null, values);
    }
    public void clearAllPosts() {
        database.delete(DatabaseHelper.TABLE_POSTS, null, null);
    }
    public List<Post> getAllPosts() {
        List<Post> posts = new ArrayList<>();
        Cursor cursor = database.query(DatabaseHelper.TABLE_POSTS, null, null, null, null, null, null);

        if (cursor != null) {

            int idIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_ID);
            int contentIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_CONTENT);
            int imagePathIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_IMAGE_PATH);
            int dateIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_DATE);
            while (cursor.moveToNext()) {
                String postId = cursor.getString(idIndex);
                String content = cursor.getString(contentIndex);
                String imagePath = cursor.getString(imagePathIndex);
                String date = cursor.getString(dateIndex);

                Post post = new Post(postId, content, imagePath, date);
                posts.add(post);
            }
            cursor.close();
        }

        return posts;
    }
}
