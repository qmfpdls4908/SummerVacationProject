package com.example.earringdiary;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class setCalenderRequest extends StringRequest {
    final static private String URL = "http://svproject.dothome.co.kr/setCalenderDB.php"; // "http:// 퍼블릭 DSN 주소/Login.php";
    private Map<String, String> parameters;

    public setCalenderRequest(String Email, String title, String startDay, String startTime, String endDay, String endTime, String dateLocate, String content, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("Email", Email);
        parameters.put("title", title);
        parameters.put("startDay", startDay);
        parameters.put("startTime", startTime);
        parameters.put("endDay", endDay);
        parameters.put("endTime", endTime);
        parameters.put("dateLocate", dateLocate);
        parameters.put("content", content);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}