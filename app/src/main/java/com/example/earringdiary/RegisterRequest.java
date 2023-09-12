package com.example.earringdiary;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {

    private static final String URL = "http://svproject.dothome.co.kr/Register.php";
    private Map<String, String> params;

    public RegisterRequest(String userId, String userPassword, String userEmail, String userName, String userPhoneNumber, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        params = new HashMap<>();
        params.put("userID", userId);
        params.put("userPassword", userPassword);
        params.put("userEmail", userEmail);
        params.put("userName", userName);
        params.put("userPhoneNumber", userPhoneNumber);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Log.d("Debug params", params.toString());
        return params;
    }
}
