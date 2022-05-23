package com.example.dikid.unsa_m;

import android.content.Intent;
import android.os.FileObserver;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.dikid.unsa_m.MainActivity.*;

public class MakePost extends AppCompatActivity {
    private long BackPresstime= 0;
    EditText Title,Contents;
    RadioGroup RG;
    RadioButton Bt1,Bt2;
    Button PostBT;
    CheckBox An;
    int Field=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_post);

        RG= (RadioGroup)findViewById(R.id.MP_Field);
        PostBT = (Button)findViewById(R.id.GetPost);
        Title=(EditText)findViewById(R.id.MP_Title);
        Contents = (EditText)findViewById(R.id.MP_Contents);
        An=(CheckBox)findViewById(R.id.anony);

        Bt1=(RadioButton)findViewById(R.id.Post1BT);
        Bt2=(RadioButton)findViewById(R.id.Post2BT);

        RG.setOnCheckedChangeListener(radioGroupButtonChangeListener);

        PostBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Title.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"제목을 입력해주세요.",Toast.LENGTH_SHORT).show();

                }
                else if(Contents.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(),"내용을 입력해주세요.",Toast.LENGTH_SHORT).show();
                }
                    else{
                    PostBT.setClickable(false);
                    PostBT.setText("게시중...");

                //TimeFormat
                    long now = System.currentTimeMillis();
                    Date date = new Date(now);
                    SimpleDateFormat sdfNow = new SimpleDateFormat("MM월dd일HH시mm분");
                    String PostTime = sdfNow.format(date);

                    String PostTitle=Title.getText().toString();
                    String PostContents=Contents.getText().toString();
                    int Isvisible =1;
                    int IsUnknown=0;

                    if (An.isChecked()) {
                        IsUnknown=1;
                    } else {
                        IsUnknown=0;
                    }
//                    if(Field!=0){

                    Response.Listener<String> responseListener = new Response.Listener<String>(){
                        @Override
                        public void onResponse(String response){
                            try{
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if(success){
                                    Intent intent = new Intent(MakePost.this,Post1.class);
                                    MakePost.this.startActivity(intent);
                                    Toast.makeText(getApplicationContext(),"새로운 글이 등록되었습니다.",Toast.LENGTH_SHORT).show();

                                    finish();
                                }
                                else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(MakePost.this);
                                    builder.setMessage("글 등록에 실패했습니다.")
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
                    PostRequest postRequest = new PostRequest(PostTitle,PostContents,Field,Isvisible,IsUnknown,PostTime,responseListener);
                    RequestQueue queue = Volley.newRequestQueue(MakePost.this);
                    queue.add(postRequest);
//            }
//            else{
//                        AlertDialog.Builder builder = new AlertDialog.Builder(MakePost.this);
//                        builder.setMessage("카테고리를 선택해 주세요.")
//                                .setNegativeButton("다시시도",null)
//                                .create()
//                                .show();
//                    }
            }}
        });

    }

    RadioGroup.OnCheckedChangeListener radioGroupButtonChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if(checkedId==R.id.Post1BT){
                Field=1;
            }
            else if(checkedId==R.id.Post2BT){
                Field=2;
            }
            else{

            }
        }
    };

    @Override
    public void onBackPressed(){
        if(System.currentTimeMillis()-BackPresstime>=2000){
            BackPresstime=System.currentTimeMillis();
            Toast.makeText(getApplicationContext(),"뒤로 버튼을 한번 더 누르면 입력을 취소하고 뒤로 갑니다.",Toast.LENGTH_SHORT).show();
        }else if(System.currentTimeMillis()-BackPresstime<2000){
            Intent it = new Intent(MakePost.this,Post1.class);
            MakePost.this.startActivity(it);
            finish();
        }
    }
}
