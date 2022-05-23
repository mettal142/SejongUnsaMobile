package com.example.dikid.unsa_m;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class DeletePostRequest extends StringRequest {
    final static private String URL = "";

    private Map<String, String> parameters;

    public DeletePostRequest(int postId, Response.Listener<String> listener){
        super(Request.Method.POST, URL,listener,null);

        parameters = new HashMap<>();

        parameters.put("PostID",postId+"");

    }
    @Override
    public Map<String,String>getParams(){
        return parameters;
    }
}
