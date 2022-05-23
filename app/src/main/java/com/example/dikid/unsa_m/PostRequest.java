package com.example.dikid.unsa_m;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import static com.example.dikid.unsa_m.MainActivity.*;

public class PostRequest extends StringRequest {
    final static private String URL = "";

    private Map<String, String> parameters;

    public PostRequest( String postTitle, String postContents, int postField, int postIsvisible, int postIsunknown,String PostTime, Response.Listener<String> listener){
        super(Method.POST, URL,listener,null);
        parameters = new HashMap<>();

        parameters.put("PostField", postField+"");
        parameters.put("PostIsUnknown",postIsunknown+"");
        parameters.put("WriterName", MyName);
        parameters.put("WriterNum", MyNum);
        parameters.put("WriterID", MyID);
        parameters.put("PostTitle", postTitle);
        parameters.put("PostContents", postContents);
        parameters.put("PostIsvisible", postIsvisible+"");
        parameters.put("PostTime",PostTime);
    }
    @Override
    public Map<String,String>getParams(){
        return parameters;
    }
}
