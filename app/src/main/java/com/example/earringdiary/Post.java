package com.example.earringdiary;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class Post {
    private String postId;
    private String postContent;
    private String postImagePath;
    private String postDate;

    public Post(String postId, String postContent, String postImagePath, String postDate) {
        this.postId = postId;
        this.postContent = postContent;
        this.postImagePath = postImagePath;
        this.postDate = postDate;
    }

    public String getPostId() {
        return postId;
    }

    public String getPostContent() {
        return postContent;
    }

    public String getPostImagePath() {
        return postImagePath;
    }

    public String getPostDate() {
        return postDate;
    }


    public String getPostTime() {
        // 현재 시간과 날짜 정보를 가져옴
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();

        // SimpleDateFormat을 사용하여 날짜와 시간을 원하는 포맷으로 변환
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm a", Locale.getDefault());
        return sdf.format(currentDate);
    }
    }

