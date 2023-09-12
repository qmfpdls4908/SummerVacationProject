package com.example.earringdiary;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PredictionActivity extends AppCompatActivity {

    RecyclerView recycler_view;
    TextView tv_welcome;
    EditText et_msg;
    ImageButton btn_send;

    String str_email;
    String str_name;
    String str_sex;
    String str_birthday;
    String str_spareTime;
    String str_favorite;
    String str_dislike;

    String str_q1;
    String str_q2;
    String str_q3;

    String str_cp_date;
    String str_cp_content;

    String str_c_date;
    String str_c_content;


    List<Message> messageList;
    MessageAdapter messageAdapter;

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    OkHttpClient client;

    //secret key 는 openai 홈페이지 가셔서 직접 복사해서 넣으면 됨
    private static final String MY_SECRET_KEY = "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";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpredict);

        str_email = "qmfpdls4908@naver.com";

        client = new OkHttpClient().newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();

        com.android.volley.Response.Listener<String> responseListener = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    Log.d(TAG, "onResponse: "+response);
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if(success){
                        str_name = jsonResponse.getString("name");
                        str_sex = jsonResponse.getString("sex");
                        str_birthday = jsonResponse.getString("birthday");
                        str_spareTime = jsonResponse.getString("spareTime");
                        str_favorite = jsonResponse.getString("favorite");
                        str_dislike = jsonResponse.getString("disLike");
                        Log.d(TAG, ""+str_name+str_sex+str_birthday+str_spareTime+str_favorite+str_dislike);
                        Toast.makeText(getApplicationContext(), "데이터 읽기에 성공했습니다.", Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        };
        GetUserDBRequest getUserDBRequest = new GetUserDBRequest(str_email, responseListener);
        RequestQueue queue = Volley.newRequestQueue(PredictionActivity.this);
        queue.add(getUserDBRequest);

        com.android.volley.Response.Listener<String> responseListener2 = new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    Log.d(TAG, "onResponse: "+response);
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if(success){
                        str_q1 = jsonResponse.getString("Question1");
                        str_q2 = jsonResponse.getString("Question2");
                        str_q3 = jsonResponse.getString("Question3");
                        Toast.makeText(getApplicationContext(), "데이터 읽기에 성공했습니다.", Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        };
        GetPropensityRequest getPropensityRequest = new GetPropensityRequest(str_email, responseListener2);
        RequestQueue queue2 = Volley.newRequestQueue(PredictionActivity.this);
        queue2.add(getPropensityRequest);

        recycler_view = findViewById(R.id.recycler_view);
        tv_welcome = findViewById(R.id.tv_welcome);
        et_msg = findViewById(R.id.et_msg);
        btn_send = findViewById(R.id.btn_send);

        recycler_view.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setStackFromEnd(true);
        recycler_view.setLayoutManager(manager);

        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(messageList);
        recycler_view.setAdapter(messageAdapter);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String question = et_msg.getText().toString().trim();
                addToChat(question, Message.SENT_BY_ME);
                et_msg.setText("");
                callAPI(question);
                tv_welcome.setVisibility(View.GONE);
            }
        });
    }

    void addToChat(String message, String sentBy){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                messageList.add(new Message(message, sentBy));
                messageAdapter.notifyDataSetChanged();
                recycler_view.smoothScrollToPosition(messageAdapter.getItemCount());
            }
        });
    }
    void addResponse(String response){
        messageList.remove(messageList.size()-1);
        addToChat(response, Message.SENT_BY_BOT);
    }
    void callAPI(String question){
        //okhttp
        messageList.add(new Message("...", Message.SENT_BY_BOT));

        JSONArray arr = new JSONArray();
        JSONObject baseAi = new JSONObject();
        JSONObject userMsg = new JSONObject();
        try {
            //AI 속성설정
            baseAi.put("role", "user");
            baseAi.put("content", "You are now a love psychologist.\n" +
                    "    You speak in Korean. \n" +
                    "    Answer the questions persuasive. \n" +
                    "    The user's information is [User's Information]. \n" +
                    "    The lover's information is [LoverInformation]. \n" +
                    "    The relationship propensity is analyzed by the [User's Answer] to the [Question]. \n" +
                    "    Users have had [User's Recent] and have [User's Calender] in the future. \n" +
                    "    The analysis result is the result of analyzing the user's information, relationship propensity, [User's Recent], [User's Calender]. \n" +
                    "    Based on the analysis results, it gives 3 advice on problems that users Question. \n" +
                    "    Summarize each advice in 25 words.\n" +
                    "\n" +
                    "    [UserInformation]\n" +
                    "    name : "+str_name+"\n" +
                    "    sex : male"+str_sex+"\n" +
                    "    birthday : "+str_birthday+"\n" +
                    "    spareTime : "+str_spareTime+"\n" +
                    "    favorite : "+str_favorite+"\n" +
                    "    dislike : "+str_dislike+"\n" +
                    "\n" +
                    "    [Question]\n" +
                    "    1. 연애에 대해서 어떻게 생각하시나요?\n" +
                    "    2. 갈등 상황, 감정 표현 시 스타일을 선호하나요?\n" +
                    "    3. 자신만의 개인적인 시간과 공간을 얼마나 중요하게 생각하나요?\n" +
                    "\n" +
                    "    [User's Answer]\n" +
                    "    1. "+str_q1+"\n" +
                    "    2. "+str_q2+"\n" +
                    "    3. "+str_q3+"\n");
            //유저 메세지
            userMsg.put("role", "user");
            userMsg.put("content", question);
            //array로 담아서 한번에 보낸다
            arr.put(baseAi);
            arr.put(userMsg);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        JSONObject object = new JSONObject();
        try {
            //모델명 변경
            object.put("model", "gpt-3.5-turbo");
            object.put("messages", arr);
//            아래 put 내용은 삭제하면 된다
//            object.put("model", "text-davinci-003");
//            object.put("prompt", question);
//            object.put("max_tokens", 4000);
//            object.put("temperature", 0);

        } catch (JSONException e){
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(object.toString(), JSON);
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")  //url 경로 수정됨
                .header("Authorization", "Bearer "+MY_SECRET_KEY)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                addResponse("Failed to load response due to "+e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response.body().string());
                        JSONArray jsonArray = jsonObject.getJSONArray("choices");
                        //아래 result 받아오는 경로가 좀 수정되었다.
                        String result = jsonArray.getJSONObject(0).getJSONObject("message").getString("content");
                        addResponse(result.trim());
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                } else {
                    addResponse("Failed to load response due to "+response.body().string());
                }
            }
        });
    }
}