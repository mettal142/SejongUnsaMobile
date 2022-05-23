package com.example.dikid.unsa_m;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import static com.example.dikid.unsa_m.MainActivity.*;

public class ReInfoRequest extends StringRequest {
    final static private String URL = "";

    private Map<String, String> parameters;

    public ReInfoRequest(String userName, String userDep,String userJob,String userMail,String userInfo, Response.Listener<String> listener){
        super(Method.POST, URL,listener,null);
        parameters = new HashMap<>();

        parameters.put("userID",MyID);
        if(!userName.equals("")){
            parameters.put("userName", userName);
            MyName = userName;
        }
        else
        {
            parameters.put("userName", MyName);
        }
        if(!userDep.equals("")){
            parameters.put("userDep", userDep);
            MyDep=userDep;
        }
        else
        {
            parameters.put("userDep", MyDep);
        }
        if(!userJob.equals("")){
            parameters.put("userJob", userJob);
            MyJob = userJob;
        }
        else
        {
            parameters.put("userJob", MyJob);
        }
        if(!userMail.equals("")){
            parameters.put("userMail", userMail);
            MyMail = userMail;
        }
        else
        {
            parameters.put("userMail", MyMail);
        }
        if(!userInfo.equals("")){
            parameters.put("userInfo", userInfo);
            MyInfo = userInfo;
        }
        else
        {
            parameters.put("userInfo", MyInfo);
        }

    }
    @Override
    public Map<String,String>getParams(){
        return parameters;
    }
}
