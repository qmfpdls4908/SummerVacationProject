package com.example.earringdiary;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class setUserPageDataRequest extends StringRequest {

    private static final String URL = "http://svproject.dothome.co.kr/setUserPageDataRequest.php";
    private Map<String, String> params;

    public setUserPageDataRequest(String email, String name, String sex, String birthday, String spareTime, String favorite,
                                  String dislike, String image, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        params = new HashMap<>();
        params.put("Email", email);
        params.put("Name", name);
        params.put("Sex", sex);
        params.put("Birthday", birthday);
        params.put("SpareTime", spareTime);
        params.put("Favorite", favorite);
        params.put("Dislike", dislike);
        params.put("Image", image);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Log.d("Debug params", params.toString());
        return params;
    }
}
