package com.example.dikid.unsa_m;

import android.app.Application;
import android.content.Intent;
import android.icu.text.IDNA;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Register extends AppCompatActivity {
    private long BackPresstime= 0;
    EditText IDtext,Passwordtext,Nametext,Deptext,Jobtext,Mailtext,Numtext,Infotext,RegisterCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

       IDtext = (EditText)findViewById(R.id.reg_Id);
       Passwordtext = (EditText)findViewById(R.id.reg_Password);
       Nametext = (EditText)findViewById(R.id.reg_Name);
       Deptext = (EditText)findViewById(R.id.reg_Dep);
       Jobtext = (EditText)findViewById(R.id.nav_Job);
       Mailtext = (EditText)findViewById(R.id.nav_Mail);
       Numtext = (EditText)findViewById(R.id.reg_Num);
       Infotext = (EditText)findViewById(R.id.Info);
       RegisterCode = (EditText)findViewById(R.id.RegisterCode);

        Button RegButton = (Button)findViewById(R.id.reg_RegBt);

        RegButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(IDtext.getText().toString().equals("")||Passwordtext.getText().toString().equals("")||Nametext.getText().toString().equals("")||
                        Deptext.getText().toString().equals("")||Mailtext.getText().toString().equals("")||Numtext.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"필수 입력칸을 모두 채워주세요.",Toast.LENGTH_SHORT).show();
                }
                else if(IDtext.getText().toString().contains("admin")){
                    Toast.makeText(getApplicationContext(),"ID에 사용할 수 없는 텍스트가 포함되어 있습니다.",Toast.LENGTH_SHORT).show();
                }
                else if(IDtext.getText().toString().length()<6){
                    Toast.makeText(getApplicationContext(),"아이디가 너무 짧습니다.",Toast.LENGTH_SHORT).show();
                }
                else if(Passwordtext.getText().toString().length()<8){
                    Toast.makeText(getApplicationContext(),"비밀번호가 너무 짧습니다.",Toast.LENGTH_SHORT).show();
                }
                else{
                    String RegCode = RegisterCode.getText().toString();

                    if(RegCode.equals("0530")){
                    try{
                        String userID=IDtext.getText().toString();
                        String userPassword=Passwordtext.getText().toString();
                        String userName=Nametext.getText().toString();
                        String userDep=Deptext.getText().toString();
                        String userJob=Jobtext.getText().toString();
                        String userMail=Mailtext.getText().toString();
                        String userInfo = Infotext.getText().toString();

                        int userNum=Integer.parseInt(Numtext.getText().toString());
                        int userPoint = 0;
                        Response.Listener<String> responseListener = new Response.Listener<String>(){
                            @Override
                            public void onResponse(String response){
                                try{
                                    JSONObject jsonResponse = new JSONObject(response);
                                    boolean success = jsonResponse.getBoolean("success");
                                    if(success){
                                        AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                                        Toast.makeText(getApplicationContext(),"회원가입에 성공했습니다.",Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(Register.this,MainActivity.class);
                                        Register.this.startActivity(intent);
                                        finish();
                                    }
                                    else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                                        builder.setMessage("회원 등록에 실패했습니다.")
                                                .setNegativeButton("다시시도",null)
                                                .create()
                                                .show();
                                    }
                                }
                                catch (JSONException e){
                                    e.printStackTrace();
                                }
                            }
                        };
                        RegisterRequest registerRequest = new RegisterRequest(userID,userPassword,userName,userDep,userJob,userMail,userNum,userPoint,userInfo,responseListener);
                        RequestQueue queue = Volley.newRequestQueue(Register.this);
                        queue.add(registerRequest);
                    } catch (NumberFormatException e)
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                        builder.setMessage("기수는 숫자만 입력 해 주세요..")
                                .setNegativeButton("다시시도",null)
                                .create()
                                .show();
                    }
                }
                else{
                        Toast.makeText(getApplicationContext(),"잘못된 가입코드입니다.",Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }
    public void onBackPressed(){
        if(System.currentTimeMillis()-BackPresstime>=2000){
            BackPresstime=System.currentTimeMillis();
            Toast.makeText(getApplicationContext(),"뒤로 버튼을 한번 더 누르면 로그인 화면으로 돌아갑니다.",Toast.LENGTH_SHORT).show();
        }else if(System.currentTimeMillis()-BackPresstime<2000){
            IDtext.setText("");
            Passwordtext.setText("");
            Deptext.setText("");
            Jobtext.setText("");
            Nametext.setText("");
            Numtext.setText("");
            Mailtext.setText("");
            Infotext.setText("");
            finish();
        }
    }
}
