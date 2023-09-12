package com.example.earringdiary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.w3c.dom.Text;

public class Login_Activity extends AppCompatActivity {
    EditText editText_ID, editText_Pass;
    Button btn_login;
    TextView tv_register;
    TextView tv_invalidate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Set up the login form.
        editText_ID = (EditText) findViewById(R.id.editText_ID);
        editText_Pass = (EditText) findViewById(R.id.editText_Pass);

        // login, register
        btn_login = (Button) findViewById(R.id.btn_login); // sign in button
        tv_register = (TextView) findViewById(R.id.tv_register); // sign up
        tv_invalidate = (TextView)findViewById(R.id.tv_invalidate);

        //로그인 버튼 비활성화
        btn_login.setEnabled(false);

        //addTextChangesListener: EditText 요소에서 사용자의 입력을 감지하는 메소드 (입력전, 동안, 후 3가지 내부 메소드 존재)
        editText_ID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //입력한 값이 null이 아닐 때 Login 버튼을 활성화
                btn_login.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // 로그인 버튼 클릭
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID = editText_ID.getText().toString();
                String userPassword = editText_Pass.getText().toString();

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

                                Intent intent = new Intent(getApplicationContext(), CouplePage.class);
                                // 로그인 하면서 사용자 정보 넘기기
                                intent.putExtra("userID", userID);
                                intent.putExtra("userPassword", userPassword);
                                startActivity(intent);

                            } else {
                                tv_invalidate.setText("아이디가 존재하지 않거나, 비밀번호가 틀렸습니다.");
                            }
                        } catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                };

                LoginRequest loginRequest = new LoginRequest(userID, userPassword, responseListener);
                RequestQueue queue = Volley.newRequestQueue(Login_Activity.this);
                queue.add(loginRequest);

            }
        });

        // 회원가입 버튼 클릭
        tv_register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent_PhoneValidateActivity = new Intent(getApplicationContext(), PhoneValidateActivity.class);
                startActivity(intent_PhoneValidateActivity);
            }
        });
    }
}