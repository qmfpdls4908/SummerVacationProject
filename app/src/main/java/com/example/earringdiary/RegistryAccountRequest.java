package com.example.earringdiary;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegistryAccountRequest extends StringRequest {

    private static final String URL = "http://svproject.dothome.co.kr/RegisrtyAccount.php";
    private Map<String, String> params;

    public RegistryAccountRequest(String email, String UUID, String partner_email, String partner_UUID, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        params = new HashMap<>();
        params.put("email", email);
        params.put("UUID", UUID);
        params.put("partner_email", partner_email);
        params.put("partner_UUID", partner_UUID);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Log.d("Debug params", params.toString());
        return params;
    }
}
