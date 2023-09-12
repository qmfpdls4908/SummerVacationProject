package com.example.earringdiary;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class PostCreationActivity extends AppCompatActivity {

    private EditText postTitleEditText;
    private Button submitButton;
    private DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_creation);

        // 레이아웃의 UI 요소들을 찾아서 변수에 연결
        postTitleEditText = findViewById(R.id.editTextPostTitle);
        submitButton = findViewById(R.id.buttonSubmit);

        // 데이터베이스 관리 클래스 초기화
        databaseManager = new DatabaseManager(this);
        databaseManager.open();

        // 작성 완료 버튼 클릭 리스너 설정
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 작성한 게시물 제목을 가져옴
                String Email = "qmfpdls4908@naver.com";
                String postTitle = postTitleEditText.getText().toString();
                //이전 화면에서 사용자 Email 정보를 가져온다.
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                Toast.makeText(getApplicationContext(), "성향 등록에 성공했습니다.", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "성향 등록에 실패했습니다.", Toast.LENGTH_SHORT).show();
                            }
                        } catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                setPostDBRequest setPost = new setPostDBRequest(Email, postTitle, responseListener);
                RequestQueue queue = Volley.newRequestQueue(PostCreationActivity.this);
                queue.add(setPost);

                if (!postTitle.isEmpty()) {
                    // 새로운 게시물 객체 생성 (postId는 null로 설정)
                    Post newPost = new Post(null, postTitle, "", "");
                    long newPostId = databaseManager.insertPost(newPost);

                    if (newPostId != -1) {
                        // 게시물 추가 성공 메시지 출력
                        Toast.makeText(PostCreationActivity.this, "게시물이 추가되었습니다.", Toast.LENGTH_SHORT).show();

                        // 결과를 성공으로 설정하고 액티비티 종료
                        setResult(RESULT_OK);
                        finish();
                    } else {
                        // 게시물 추가 실패 메시지 출력
                        Toast.makeText(PostCreationActivity.this, "게시물 추가에 실패했습니다.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // 게시물 제목이 비어있을 경우 메시지 출력
                    Toast.makeText(PostCreationActivity.this, "게시물 제목을 입력하세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 데이터베이스 관리 클래스를 닫아 리소스를 정리합니다.
        databaseManager.close();
    }
}
