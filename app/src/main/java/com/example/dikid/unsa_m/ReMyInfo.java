package com.example.dikid.unsa_m;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.dikid.unsa_m.MainActivity.*;

public class ReMyInfo extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_re_my_info);
        final TextView reName = (TextView)findViewById(R.id.re_Name);
        final TextView reDep = (TextView)findViewById(R.id.re_Dep);
        final TextView reJob = (TextView)findViewById(R.id.re_Job);
        final TextView reMail = (TextView)findViewById(R.id.re_Mail);
        final TextView reInfo = (TextView)findViewById(R.id.re_info);

        Button CloseBT = (Button)findViewById(R.id.reClose);

        Button CommitBT = (Button)findViewById(R.id.re_Change);

        CloseBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        CommitBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               final String userName=reName.getText().toString();
               final String userDep=reDep.getText().toString();
               final String userJob=reJob.getText().toString();
               final String userMail=reMail.getText().toString();
               final String userInfo = reInfo.getText().toString();



                Response.Listener<String> responseListener = new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                Toast.makeText(getApplicationContext(),"회원정보 변경에 성공했습니다.",Toast.LENGTH_SHORT).show();
                                Main2Activity ma2 = (Main2Activity)Main2Activity.main2Activity;
                                ma2.finish();
                                Intent it = new Intent(ReMyInfo.this,Main2Activity.class);
                                ReMyInfo.this.startActivity(it);
                                finish();
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(ReMyInfo.this);
                                builder.setMessage("회원 정보 변경에 실패했습니다.")
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
                ReInfoRequest reInfoRequest = new ReInfoRequest(userName,userDep,userJob,userMail,userInfo,responseListener);
                RequestQueue queue = Volley.newRequestQueue(ReMyInfo.this);
                queue.add(reInfoRequest);
            }
        });

    }
}
