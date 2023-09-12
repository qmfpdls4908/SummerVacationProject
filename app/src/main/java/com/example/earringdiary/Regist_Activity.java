package com.example.earringdiary;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Regist_Activity extends AppCompatActivity {
    EditText EditText_email;
    EditText EditText_name;
    EditText EditText_pass;
    EditText EditText_confirm;

    TextView TextView_EmailInvalid;
    TextView TextView_PasswordInvalid;
    Button btn_register;

    Boolean PasswordValidate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        EditText_email = findViewById(R.id.EditText_emil);
        EditText_name = findViewById(R.id.EditText_name);
        EditText_pass = findViewById(R.id.EditText_pass);
        EditText_confirm = findViewById(R.id.EditText_confirm);

        TextView_EmailInvalid = findViewById(R.id.Tv_EmailInvalid);
        TextView_PasswordInvalid = findViewById(R.id.Tv_PasswordInvalid);

        btn_register = findViewById(R.id.btn_register);


        EditText_confirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d("PasswordVlidate beforeTexetChange", " Active");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("PasswordVlidate onTextChanged", " Active");
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("PasswordVlidate afterTextChanged", " Active");
                String str_Password = EditText_pass.getText().toString();
                String str_PasswordConfirm = EditText_confirm.getText().toString();
                if(str_Password.equals(str_PasswordConfirm)){
                    PasswordValidate = false;
                    TextView_PasswordInvalid.setText("");
                    Log.d("PasswordVlidate : ", "false");
                }
                else{
                    PasswordValidate = true;
                    btn_register.setEnabled(true);
                    Log.d("PasswordVlidate : ", "true");
                }
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_email = EditText_email.getText().toString();
                String str_userID = str_email;
                String str_name = EditText_name.getText().toString();
                String str_pass = EditText_pass.getText().toString();
                String str_phoneNumber = getIntent().getStringExtra("number");
                Log.d("Debug str_email",str_email+"");
                Log.d("Debug str_userID",str_userID+"");
                Log.d("Debug str_name",str_name+"");
                Log.d("Debug str_phoneNumber",str_phoneNumber+"");
                Log.d("Debug str_pass",str_pass+"");

                if(PasswordValidate) TextView_PasswordInvalid.setText("비밀번호가 다릅니다.");
                Response.Listener<String> responseListner = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Debug response", response + "");
                        try {
                            JSONObject jasonObject = new JSONObject(response);
                            boolean success = jasonObject.getBoolean("success");
                            if (success && !PasswordValidate) {
                                Toast.makeText(getApplicationContext(), "회원 등록에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), Login_Activity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "회원 등록에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                TextView_EmailInvalid.setText("이미 가입된 이메일 입니다.");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                RegisterRequest registerRequest = new RegisterRequest(str_userID, str_pass, str_email, str_name, str_phoneNumber, responseListner);
                RequestQueue queue = Volley.newRequestQueue(Regist_Activity.this);
                queue.add(registerRequest);
            }
        });
    }
}
