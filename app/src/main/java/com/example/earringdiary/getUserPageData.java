package com.example.earringdiary;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class getUserPageData {

    String str_email;
    String str_name;
    String str_sex;
    String str_birthday;
    String str_spareTime;
    String str_favorite;
    String str_dislike;

    public void getUserData(){
        String Url = "http://svproject.dothome.co.kr/getUserPageData.php";
        JsonArrayRequest jasonArrayRequest = new JsonArrayRequest(Request.Method.POST, Url,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    Log.d(TAG, "onResponse: "+response);
                    for(int i=0;i<response.length();i++){
                        JSONObject jsonObject= response.getJSONObject(i);

                        String str_email=jsonObject.getString("Email");
                        String str_name=jsonObject.getString("name");
                        String str_sex=jsonObject.getString("sex");
                        String str_birthday=jsonObject.getString("birthday");
                        String str_spareTime=jsonObject.getString("spareTime");
                        String str_favorite=jsonObject.getString("favorite");
                        String str_dislike=jsonObject.getString("dislike");
                    }
                } catch (JSONException e) {e.printStackTrace();}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
    }

    public String getUD_name(){return str_name;}
    public String getUD_email(){return str_email;}
    public String getUD_sex(){return str_sex;}
    public String getUD_birthday(){return str_birthday;}
    public String getUD_spareTime(){return str_spareTime;}
    public String getUD_favorite(){return str_favorite;}
    public String getUD_dislike(){return str_dislike;}
}
