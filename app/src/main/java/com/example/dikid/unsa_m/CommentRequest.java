package com.example.dikid.unsa_m;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import static com.example.dikid.unsa_m.MainActivity.MyID;
import static com.example.dikid.unsa_m.MainActivity.MyName;
import static com.example.dikid.unsa_m.MainActivity.MyNum;

public class CommentRequest extends StringRequest {
    final static private String URL = "";

    private Map<String, String> parameters;

    public CommentRequest( int postId, String commentContents, int commetIsvisible, int commentIsunknown,String commentTime, Response.Listener<String> listener){
        super(Request.Method.POST, URL,listener,null);

        parameters = new HashMap<>();

        parameters.put("PostID",postId+"");
        parameters.put("CommentIsUnknown",commentIsunknown+"" );
        parameters.put("WriterName",MyName );
        parameters.put("WriterNum", MyNum);
        parameters.put("WriterID", MyID);
        parameters.put("CommentIsvisible", commetIsvisible+"");
        parameters.put("CommentTime", commentTime);
        parameters.put("CommentContents",commentContents);
    }
    @Override
    public Map<String,String>getParams(){
        return parameters;
    }
}
