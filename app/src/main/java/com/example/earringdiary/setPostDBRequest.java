package com.example.earringdiary;

        import android.util.Log;

        import com.android.volley.AuthFailureError;
        import com.android.volley.Response;
        import com.android.volley.toolbox.StringRequest;

        import java.util.HashMap;
        import java.util.Map;

public class setPostDBRequest extends StringRequest {
    private static final String URL = "http://svproject.dothome.co.kr/setPostDB.php";
    private Map<String, String> params;

    // public setPartnerRequest(String email, String UUID, Response.Listener<String> listener)
    public setPostDBRequest(String Email, String content, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        params = new HashMap<>();
        params.put("Email", Email);
        params.put("content", content);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Log.d("Debug params", params.toString());
        return params;
    }

}

