package com.example.earringdiary;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class CouplePage extends AppCompatActivity {

    private GridView gridView;
    private GridAdapter adapter;
    private DatabaseManager databaseManager;

    Button goAi;
    Button goCalender;
    ImageView profile;
    private static final int POST_CREATION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_couplepage);

        profile = findViewById(R.id.imageView5);
        gridView = findViewById(R.id.post_grid);
        goAi = findViewById(R.id.button10);
        goCalender = findViewById(R.id.button6);

        databaseManager = new DatabaseManager(this);
        databaseManager.open();

        List<Post> posts = databaseManager.getAllPosts();

        adapter = new GridAdapter(this, posts);
        gridView.setAdapter(adapter);

        FloatingActionButton fabCreatePost = findViewById(R.id.floatingActionButton3);
        fabCreatePost.setOnClickListener(view -> {
            // PostCreationActivity로 전환
            Intent intent = new Intent(CouplePage.this, PostCreationActivity.class);
            startActivityForResult(intent, POST_CREATION_REQUEST_CODE);
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),userPage.class);
                startActivity(intent);
            }
        });

        goAi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PredictionActivity.class);
                startActivity(intent);
            }
        });

        goCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),activity_day.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == POST_CREATION_REQUEST_CODE && resultCode == RESULT_OK) {
            // 새로운 게시물이 생성되었을 경우
            List<Post> updatedPosts = databaseManager.getAllPosts();
            adapter.updateData(updatedPosts);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseManager.close();
    }
}
