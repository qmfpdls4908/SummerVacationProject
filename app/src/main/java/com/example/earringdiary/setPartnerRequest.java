package com.example.earringdiary;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class setPartnerRequest extends StringRequest {
    private static final String URL = "http://svproject.dothome.co.kr/getAccountUUID.php";
    private Map<String, String> params;

    // public setPartnerRequest(String email, String UUID, Response.Listener<String> listener)
    public setPartnerRequest(String UUID, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        params = new HashMap<>();
        //params.put("email", email);
        params.put("UUID", UUID);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Log.d("Debug params", params.toString());
        return params;
    }

}
