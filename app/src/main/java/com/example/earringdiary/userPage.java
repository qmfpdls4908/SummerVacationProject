package com.example.earringdiary;

import static com.android.volley.VolleyLog.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.IOException;

public class userPage extends AppCompatActivity {

    EditText edit_name;
    EditText edit_birthDay;
    EditText spareTime;
    EditText favorite;
    EditText dislike;
    RadioGroup radio;

    Button btn_test;
    Button btn_save;

    String str_name;
    String str_sex;
    String str_birthday;
    String str_spareTime;
    String str_favorite;
    String str_dislike;

    ImageView iv_userpage_myImage;
    ImageView iv_userpage_youImage;
    static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);

        edit_name = findViewById(R.id.et_userpage_name);
        edit_birthDay = findViewById(R.id.et_userpage_birthDay);
        spareTime = findViewById(R.id.et_userpage_spareTime);
        favorite = findViewById(R.id.et_userpage_favorite);
        dislike = findViewById(R.id.et_userpage_disLike);
        radio = findViewById(R.id.radioGroup3);

        btn_save = findViewById(R.id.btn_save);
        btn_test = findViewById(R.id.btn_test);

        iv_userpage_myImage = findViewById(R.id.iv_userpage_myImage);
        iv_userpage_youImage = findViewById(R.id.iv_userpage_youImage);

        iv_userpage_myImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });

        radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.radioButton) {
                    str_sex = "male";
                } else if (i==R.id.radioButton3) {
                    str_sex = "female";
                }
            }
        });

        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PropensityTest.class);
                startActivity(intent);
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //이전 화면에서 사용자 Email 정보를 가져온다.
                String str_email = "qmfpdls4908@naver.com";
                str_name = edit_name.getText().toString();
                str_birthday = edit_birthDay.getText().toString();
                str_spareTime = spareTime.getText().toString();
                str_favorite = favorite.getText().toString();
                str_dislike = dislike.getText().toString();
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse: "+response);
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                Toast.makeText(getApplicationContext(), "개인정보 등록을 성공했습니다.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "개인정보 등록을 실패했습니다.", Toast.LENGTH_SHORT).show();
                            }
                        } catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                setUserPageDataRequest setUserData = new setUserPageDataRequest(str_email, str_name, str_sex, str_birthday, str_spareTime, str_favorite, str_dislike, "null",responseListener);
                RequestQueue queue = Volley.newRequestQueue(userPage.this);
                queue.add(setUserData);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //이미지 뷰를 클릭하면 시작되는 함수

        if(requestCode== REQUEST_CODE && resultCode==RESULT_OK && data!=null) {
            //response에 getData , return data 부분 추가해주어야 한다

            Uri photoUri = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),photoUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //이미지뷰에 이미지 불러오기
            iv_userpage_myImage.setImageBitmap(bitmap);

        }else{
            Toast.makeText(this, "사진 업로드 실패", Toast.LENGTH_LONG).show();
        }
    }
}