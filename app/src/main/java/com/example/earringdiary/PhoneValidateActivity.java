package com.example.earringdiary;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PhoneValidateActivity extends AppCompatActivity {

    TextView tv_requestValidate;
    TextView tv_requestAgain;
    TextView tv_invalidate;
    TextView tv_confirmValidate;

    EditText et_phoneNumber;
    EditText et_validateNumber;

    Button btn_validateToRegistry;

    FirebaseAuth phoneAuthActivity;
    String codeSent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_validate);
        tv_requestValidate = findViewById(R.id.tv_requestValidate);
        tv_requestAgain = findViewById(R.id.tv_requestAgain);
        tv_invalidate = findViewById(R.id.tv_invalidate);
        tv_confirmValidate = findViewById(R.id.tv_confirmValidate);
        et_phoneNumber = findViewById(R.id.et_phoneNumber);
        et_validateNumber = findViewById(R.id.et_validateNumber);
        btn_validateToRegistry = findViewById(R.id.btn_validateToRegistry);

        FirebaseApp.initializeApp(this);
        phoneAuthActivity = FirebaseAuth.getInstance();

        tv_requestValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendVerificationCode();
                tv_confirmValidate.setEnabled(true);
                Log.d(TAG, "requestValidate: onClick");
            }
        });

        tv_confirmValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifySignInCode();
            }
        });

        tv_requestAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendVerificationCode();
                Log.d(TAG, "requestAgain: onClick");
            }
        });

        btn_validateToRegistry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Regist_Activity.class);
                intent.putExtra("number",et_phoneNumber.getText().toString());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void verifySignInCode(){
        String code = et_validateNumber.getText().toString();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSent, code);
        signInWithPhoneAuthCredential(credential); //call for check code
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        phoneAuthActivity.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //here you can open new activity
                            tv_invalidate.setText("인증 성공!");
                            tv_invalidate.setTextColor(Color.GREEN);
                            btn_validateToRegistry.setEnabled(true);
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(getApplicationContext(),
                                        "Incorrect Verification Code ", Toast.LENGTH_LONG).show();
                                tv_invalidate.setText("인증 실패");
                                tv_invalidate.setTextColor(Color.RED);
                                btn_validateToRegistry.setEnabled(false);
                            }
                        }
                    }
                });
    }

    private void sendVerificationCode(){
        String phone = et_phoneNumber.getText().toString();
        String number82 = phoneNumber82(phone);
        PhoneAuthOptions.Builder optionsBuilder =
                PhoneAuthOptions.newBuilder(phoneAuthActivity)
                        .setPhoneNumber(number82)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallbacks);
        PhoneAuthOptions options = optionsBuilder.build();
        PhoneAuthProvider.verifyPhoneNumber(options);
        Log.d(TAG, "sendVerificationCode: PhoneAuthProvider");
    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

        }
        @Override
        public void onVerificationFailed(FirebaseException e) {

        }
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            codeSent = s;
        }
    };
    public static String phoneNumber82(String msg) {
        String firstNumber = msg.substring(0, 3);
        String phoneEdit = msg.substring(3);

        switch (firstNumber) {
            case "010":
                phoneEdit = "+8210" + phoneEdit;
                break;
            case "011":
                phoneEdit = "+8211" + phoneEdit;
                break;
            case "016":
                phoneEdit = "+8216" + phoneEdit;
                break;
            case "017":
                phoneEdit = "+8217" + phoneEdit;
                break;
            case "018":
                phoneEdit = "+8218" + phoneEdit;
                break;
            case "019":
                phoneEdit = "+8219" + phoneEdit;
                break;
            case "106":
                phoneEdit = "+82106" + phoneEdit;
                break;
        }
        Log.d("국가코드로 변경된 번호", phoneEdit);
        return phoneEdit;
    }
}