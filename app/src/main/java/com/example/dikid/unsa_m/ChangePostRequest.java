package com.example.dikid.unsa_m;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ChangePostRequest extends StringRequest {
    final static private String URL = " ";

    private Map<String, String> parameters;

    public ChangePostRequest( String postId, String posttitle, String postcontents, int postIsUnknown, Response.Listener<String> listener){
        super(Request.Method.POST, URL,listener,null);

        parameters = new HashMap<>();

        parameters.put("PostID",postId);
        parameters.put("PostTitle",posttitle);
        parameters.put("PostContents",postcontents);
        parameters.put("PostIsUnknown",postIsUnknown+"");

    }
    @Override
    public Map<String,String>getParams(){
        return parameters;
    }
}
