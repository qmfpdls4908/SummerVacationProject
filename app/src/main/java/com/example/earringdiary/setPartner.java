package com.example.earringdiary;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

public class setPartner extends AppCompatActivity {
    String url = "http://svproject.dothome.co.kr/getAccountUUID.php";
    TextView tv;
    public GettingPHP gPHP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_partner);

        gPHP = new GettingPHP();
        tv = findViewById(R.id.textView18);
        gPHP.onPostExecute(url);
    }

    class GettingPHP extends AsyncTask<String, Integer, String>{

        @Override
        protected String doInBackground(String... params) {
            StringBuilder jsonHtml = new StringBuilder();
            try{
                URL phpUrl = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection)phpUrl.openConnection();

                if ( conn != null ) {
                    conn.setConnectTimeout(10000);
                    conn.setUseCaches(false);
                    if(conn.getResponseCode() == HttpURLConnection.HTTP_OK ){
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                        while ( true ) {
                            String line = br.readLine();
                            if ( line == null )
                                break;
                            jsonHtml.append(line + "\n");
                        }
                        br.close();
                    }
                    conn.disconnect();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return jsonHtml.toString();
        }

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if(success){
                        String userID = jsonResponse.getString("userID");
                        String userPassword = jsonResponse.getString("userPassword");
                        Toast.makeText(getApplicationContext(), "로그인에 성공했습니다.", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        // 로그인 하면서 사용자 정보 넘기기
                        intent.putExtra("userID", userID);
                        intent.putExtra("userPassword", userPassword);
                        startActivity(intent);

                    } else {
                        //tv_invalidate.setText("아이디가 존재하지 않거나, 비밀번호가 틀렸습니다.");
                    }
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        };

        protected void onPostExecute(String response) {
            Log.d(TAG, "onPostExecute: Active");
            try {
                // PHP에서 받아온 JSON 데이터를 JSON오브젝트로 변환
                Log.d(TAG, "onPostExecute: "+response);
                JSONObject jObject = new JSONObject(response);
                JSONArray results = jObject.getJSONArray("results");
                String zz = "";
                zz += "Status : " + jObject.get("status");
                zz += "\n";
                zz += "Number of results : " + jObject.get("num_result");
                zz += "\n";
                zz += "Results : \n";

                for ( int i = 0; i < results.length(); ++i ) {
                    JSONObject temp = results.getJSONObject(i);
                    zz += "\temail : " + temp.get("email");
                    zz += "\tUUID : " + temp.get("UUID");
                    zz += "\tpartner_email : " + temp.get("partner_email");
                    zz += "\tpartner_UUID : " + temp.get("partner_UUID");
                    zz += "\n\t--------------------------------------------\n";
                }
                tv.setText(zz);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}