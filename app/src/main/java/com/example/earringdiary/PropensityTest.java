package com.example.earringdiary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.w3c.dom.Text;

public class PropensityTest extends AppCompatActivity {
    int questionCount;
    static String q1Answer;
    static String q2Answer;
    static String q3Answer;

    static TextView label;
    static TextView Question;
    static TextView Answer1;
    static TextView Answer2;
    static TextView Answer3;
    static TextView Answer4;

    Button next;
    Button prev;
    Button done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_propensity_test);

        label = findViewById(R.id.textView17);
        Question = findViewById(R.id.Question);
        Answer1 = findViewById(R.id.Answer_1);
        Answer2 = findViewById(R.id.Answer_2);
        Answer3 = findViewById(R.id.Answer_3);
        Answer4 = findViewById(R.id.Answer_4);
        next = findViewById(R.id.btn_next);
        prev = findViewById(R.id.btn_prev);
        done = findViewById(R.id.btn_done);

        questionCount = 1;

        Answer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAnswer(questionCount, 1);
            }
        });

        Answer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAnswer(questionCount, 2);
            }
        });

        Answer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAnswer(questionCount, 3);
            }
        });

        Answer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAnswer(questionCount, 4);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(questionCount > 2)return;
                questionCount+=1;
                refresh(questionCount);
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(questionCount < 1)return;
                questionCount-=1;
                refresh(questionCount);
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //이전 화면에서 사용자 Email 정보를 가져온다.
                String str_email = "qmfpdls4908@naver.com";
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
                setPropensityRequest setPropensity = new setPropensityRequest(str_email, q1Answer, q2Answer, q3Answer, responseListener);
                RequestQueue queue = Volley.newRequestQueue(PropensityTest.this);
                queue.add(setPropensity);
            }
        });
    }

    static void setAnswer(int questionCount, int i){
        if(questionCount==1){
            if(i==1)q1Answer = Answer1.getText().toString();
            if(i==2)q1Answer = Answer2.getText().toString();
            if(i==3)q1Answer = Answer3.getText().toString();
            if(i==4)q1Answer = Answer4.getText().toString();
        }else if(questionCount==2){
            if(i==1)q2Answer = Answer1.getText().toString();
            if(i==2)q2Answer = Answer2.getText().toString();
            if(i==3)q2Answer = Answer3.getText().toString();
            if(i==4)q2Answer = Answer4.getText().toString();
        }else if(questionCount==3){
            if(i==1)q3Answer = Answer1.getText().toString();
            if(i==2)q3Answer = Answer2.getText().toString();
            if(i==3)q3Answer = Answer3.getText().toString();
            if(i==4)q3Answer = Answer4.getText().toString();
        }
    }

    static void refresh(int questionCount){
        if (questionCount == 1) {
            Question.setText("연애에 대해서 어떻게 생각하시나요?");
            label.setText("질문 " + questionCount);

            Answer1.setText("A. 진지하게 고민하며 중요한 삶의 일부로 여김");
            Answer2.setText("B. 행복을 주는 하나의 경험으로 생각");
            Answer3.setText("C. 혼자의 시간과 균형을 유지하며 접근");
            Answer4.setText("D. 연애에 별다른 가치를 두지 않음");
        } else if (questionCount == 2) {
            Question.setText("갈등 상황, 감정 표현 시 스타일을 선호하나요?");
            label.setText("질문 " + questionCount);

            Answer1.setText("A. 갈등 상황에서도 직접 대화로 해결하는 편");
            Answer2.setText("B. 감정이 수그러들 때 대화하려고 노력");
            Answer3.setText("C. 갈등을 피하고 시간을 갖고 대화 선호");
            Answer4.setText("D. 갈등 상황에서 말하기보다는 글로 의사소통");
        } else if (questionCount == 3) {
            Question.setText("자신만의 개인적인 시간과 공간을 얼마나 중요하게 생각하나요?");
            label.setText("질문 " + questionCount);

            Answer1.setText("A. 개인적인 시간과 공간을 무척 중요하게 여김");
            Answer2.setText("B. 중요하지만 상대방과의 균형을 유지하려 함");
            Answer3.setText("C. 유연하게 조절하며 양쪽 모두의 필요를 고려");
            Answer4.setText("D. 상대방과 함께 시간을 보내는 것을 선호");
        }

        Log.d("TAG",""+q1Answer);
        Log.d("TAG",""+q2Answer);
        Log.d("TAG",""+q3Answer);
    }
}