package com.example.dikid.unsa_m;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class DeleteUserRequest extends StringRequest {
    
    final static private String URL = "";

    private Map<String, String> parameters;

    public DeleteUserRequest(String userid, Response.Listener<String> listener){
        super(Request.Method.POST, URL,listener,null);

        parameters = new HashMap<>();

        parameters.put("userID",userid);

    }
    @Override
    public Map<String,String>getParams(){
        return parameters;
    }
}
