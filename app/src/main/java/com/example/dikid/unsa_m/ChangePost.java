package com.example.dikid.unsa_m;

import android.content.Intent;
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

import org.json.JSONException;
import org.json.JSONObject;

public class ChangePost extends AppCompatActivity {

        int IsUnknown;
        String PostTitle,PostContents,PostID;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_post);

        final EditText Title = (EditText)findViewById(R.id.CP_Title);
        final EditText Contents = (EditText)findViewById(R.id.CP_Contents);

        final CheckBox Annoy = (CheckBox)findViewById(R.id.CP_anony);
        Button GetPost = (Button)findViewById(R.id.CP_GetPost);

        Title.setText(getIntent().getStringExtra("PostTitle"));
        Contents.setText(getIntent().getStringExtra("PostContents"));
        PostID=getIntent().getStringExtra("PostID");

        GetPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                IsUnknown=0;

                if (Annoy.isChecked()) {
                    IsUnknown=1;
                } else {
                    IsUnknown=0;
                }

                PostTitle=Title.getText().toString();
                PostContents = Contents.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                Post1 p1 = (Post1)Post1.post1;
                                p1.finish();
                                Intent intent = new Intent(ChangePost.this,Post1.class);
                                ChangePost.this.startActivity(intent);
                                Toast.makeText(getApplicationContext(),"수정된 글이 등록되었습니다.",Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(ChangePost.this);
                                builder.setMessage("글 수정에 실패했습니다.")
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
                ChangePostRequest changePostRequest = new ChangePostRequest(PostID,PostTitle,PostContents,IsUnknown,responseListener);
                RequestQueue queue = Volley.newRequestQueue(ChangePost.this);
                queue.add(changePostRequest);
            }
        });

    }
}
