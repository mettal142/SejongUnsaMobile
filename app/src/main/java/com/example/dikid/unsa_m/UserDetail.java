package com.example.dikid.unsa_m;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class UserDetail extends Activity {

    String userName,userNum,userDep,userJob,userMail,userInfo,userPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_user_detail);

        userName = getIntent().getStringExtra("Name_p");
        userNum = getIntent().getStringExtra("Num_p");
        userDep = getIntent().getStringExtra("Dep_p");
        userJob = getIntent().getStringExtra("Job_p");
        userMail = getIntent().getStringExtra("Email_p");
        userPoint = getIntent().getStringExtra("Point_p");
        userInfo = getIntent().getStringExtra("Info_p");


        TextView DetailName = (TextView)findViewById(R.id.DetailName);
        TextView DetailDep = (TextView)findViewById(R.id.DetailDep);
        TextView DetailJob = (TextView)findViewById(R.id.DetailJob);
        TextView DetailMail = (TextView)findViewById(R.id.DetailMail);
        TextView DetailInfo = (TextView)findViewById(R.id.DetailIntro);
        Button CloseBT = (Button)findViewById(R.id.DetailClose);

        DetailName.setText(userNum+"기 "+userName);
        DetailDep.setText(userDep);
        DetailJob.setText(userJob);
        DetailMail.setText(userMail);
        DetailInfo.setText(userInfo);

        CloseBT.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
