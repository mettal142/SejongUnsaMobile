package com.example.dikid.unsa_m;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private long BackPresstime= 0;
    static String MyID, MyName,MyDep,MyJob,MyMail,MyNum,MyPoint,MyInfo;
    static String savedID,savedPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       final EditText IDtext = (EditText)findViewById(R.id.login_Id);
       final EditText Passwordtext = (EditText)findViewById(R.id.login_Password);

       final Button LoginBT = (Button)findViewById(R.id.login_LoginBt);
       final Button RegBT = (Button)findViewById(R.id.login_RegBT);
       final CheckBox Auto = (CheckBox)findViewById(R.id.AutoLogin);
       final SharedPreferences auto = getSharedPreferences("auto", 0);
       final SharedPreferences.Editor editor = auto.edit();

        savedID = auto.getString("savedID","");
        savedPassword = auto.getString("savedPassword","");

        if( !savedID.equals("") && !savedPassword.equals("")) {
            final String userID = savedID;
            final String userPassword = savedPassword;

            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try{
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");
                        if(success){
                            MyID = jsonResponse.getString("userID");
                            MyName = jsonResponse.getString("userName");
                            MyDep= jsonResponse.getString("userDep");
                            MyJob = jsonResponse.getString("userJob");
                            MyMail = jsonResponse.getString("userMail");
                            MyNum = jsonResponse.getString("userNum");
                            MyPoint = jsonResponse.getString("userPoint");
                            MyInfo = jsonResponse.getString("userInfo");
                            Toast.makeText(getApplicationContext(),"?????? ???????????? ??????????????????.",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                            MainActivity.this.startActivity(intent);
                            finish();
                        }
                        else{
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setMessage("???????????? ?????????????????????.")
                                    .setNegativeButton("????????????",null)
                                    .create()
                                    .show();
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            };

            LoginRequest loginRequest = new LoginRequest(userID,userPassword,responseListener);
            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
            queue.add(loginRequest);
        }

        IDtext.setText("");
       Passwordtext.setText("");

        RegBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent RegisterActivity = new Intent(MainActivity.this, Register.class);
                MainActivity.this.startActivity(RegisterActivity);
            }
        });
        LoginBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userID = IDtext.getText().toString();
                final String userPassword = Passwordtext.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                     try{
                         JSONObject jsonResponse = new JSONObject(response);
                         boolean success = jsonResponse.getBoolean("success");
                         if(success){
                             MyID = jsonResponse.getString("userID");
                             MyName = jsonResponse.getString("userName");
                             MyDep= jsonResponse.getString("userDep");
                             MyJob = jsonResponse.getString("userJob");
                             MyMail = jsonResponse.getString("userMail");
                             MyNum = jsonResponse.getString("userNum");
                             MyPoint = jsonResponse.getString("userPoint");
                             MyInfo = jsonResponse.getString("userInfo");

                             if(Auto.isChecked()){
                                 editor.putString("savedID",userID);
                                 editor.putString("savedPassword",userPassword);
                                 editor.commit();
                             }

                             Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                             MainActivity.this.startActivity(intent);
                             finish();
                         }
                         else{
                             AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                             builder.setMessage("???????????? ?????????????????????.")
                                     .setNegativeButton("????????????",null)
                                     .create()
                                     .show();
                         }
                     }catch(Exception e){
                         e.printStackTrace();
                        }
                    }
                };

                MyID ="gun1875 ";
                MyName = "?????????";
                MyDep= "?????????????????? ?????????";
                MyJob = "?????????";
                MyMail = "mettal142@naver.com";
                MyNum = "52";

                MyInfo ="??????????????? ????????? ????????? 52??? ??????????????????";

                Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                MainActivity.this.startActivity(intent);
                finish();

                LoginRequest loginRequest = new LoginRequest(userID,userPassword,responseListener);
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(loginRequest);
            }
        });
    }
    @Override
    public void onBackPressed(){
        if(System.currentTimeMillis()-BackPresstime>=2000){
            BackPresstime=System.currentTimeMillis();
            Toast.makeText(getApplicationContext(),"?????? ????????? ?????? ??? ????????? ???????????????.",Toast.LENGTH_SHORT).show();
        }else if(System.currentTimeMillis()-BackPresstime<2000){
            moveTaskToBack(true);
            finish();
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }
}
