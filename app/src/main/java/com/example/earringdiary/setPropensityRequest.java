package com.example.earringdiary;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class setPropensityRequest extends StringRequest {
    final static private String URL = "http://svproject.dothome.co.kr/setPropensityRequest.php"; // "http:// 퍼블릭 DSN 주소/Login.php";
    private Map<String, String>parameters;

    public setPropensityRequest(String Email, String q1Answer,String q2Answer,String q3Answer, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("Email", Email);
        parameters.put("Question1", q1Answer);
        parameters.put("Question2", q2Answer);
        parameters.put("Question3", q3Answer);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}
