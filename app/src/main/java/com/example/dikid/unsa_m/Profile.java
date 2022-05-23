package com.example.dikid.unsa_m;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Profile extends AppCompatActivity {

    ArrayList<UserData> userList = new ArrayList<UserData>();
    private ArrayList<UserData> saveList = new ArrayList<UserData>();
    static Context ct_p;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ct_p=this;

        try{
            JSONObject jsonObject = new JSONObject(getIntent().getStringExtra("userList"));
            JSONArray jsonArray =jsonObject.getJSONArray("response");
            int count =0;
            String userID,userName, userNum,userDep, userJob, userEmail,userPoint,userInfo;
            while(count<jsonArray.length()){
                JSONObject object = jsonArray.getJSONObject(count);
                userID = object.getString("userID");
                userName= object.getString("userName");
                userNum= object.getString("userNum");
                userDep= object.getString("userDep");
                userJob= object.getString("userJob");
                userEmail= object.getString("userMail");
                userPoint= object.getString("userPoint");
                userInfo = object.getString("userInfo");
                UserData user = new UserData(userID,userName,userNum,userDep,userJob,userEmail,userPoint,userInfo);
                if(!user.Name.equals("관리자")){
                userList.add(user);
                saveList.add(user);
                }
                count++;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        final RecyclerAdapter recyclerAdapter = new RecyclerAdapter(userList,saveList);
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.List);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager((Profile.this)));
        recyclerAdapter.notifyDataSetChanged();

        EditText finduser = (EditText)findViewById(R.id.findPT);
        finduser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchUser(s.toString(),recyclerAdapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public void searchUser (String search, RecyclerAdapter ra){
        userList.clear();
        for(int i =0; i<saveList.size(); i++){
            if(saveList.get(i).getName().contains(search)){
                userList.add(saveList.get(i));
            }
            else if(saveList.get(i).getDep().contains(search)){
                userList.add(saveList.get(i));
            }
            else if(saveList.get(i).getNum().contains(search)){
                userList.add(saveList.get(i));
            }
        }
        ra.notifyDataSetChanged();
    }
}