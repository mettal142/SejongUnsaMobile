package com.example.dikid.unsa_m;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {
    final static private String URL = "";

    private Map<String, String> parameters;

    public RegisterRequest(String userID, String userPassword, String userName, String userDep,String userJob,String userMail, int userNum,int userPoint,String userInfo, Response.Listener<String> listener){
        super(Method.POST, URL,listener,null);
        parameters = new HashMap<>();

          parameters.put("userID", userID);
          parameters.put("userPassword", userPassword);
          parameters.put("userName", userName);
          parameters.put("userDep", userDep);
          parameters.put("userJob", userJob);
          parameters.put("userMail", userMail);
          parameters.put("userNum", userNum + "");
          parameters.put("userPoint", userPoint + "");
          parameters.put("userInfo", userInfo);

      }
    @Override
    public Map<String,String>getParams(){
        return parameters;
    }
}
