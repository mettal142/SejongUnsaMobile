package com.example.dikid.unsa_m;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class SettingComment extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_setting_comment);

        final Vibrator vibrator= (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        final String CommentID = getIntent().getStringExtra("CommentID");
        vibrator.vibrate(30);
        Button ReComment = (Button)findViewById(R.id.ReComment);
        Button DelComment = (Button)findViewById(R.id.DeleteComment);

        DelComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Response.Listener<String> responseListener = new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                Toast.makeText(getApplicationContext(),"댓글이 삭제되었습니다.",Toast.LENGTH_SHORT).show();
                                PostDetail postDetail = (PostDetail) PostDetail.PD;
                                Intent it = new Intent (SettingComment.this,PostDetail.class);
                                it.putExtra("PostUserNum", postDetail.getIntent().getStringExtra("PostUserNum"));
                                it.putExtra("PostUserName", postDetail.getIntent().getStringExtra("PostUserName"));
                                it.putExtra("PostTime", postDetail.getIntent().getStringExtra("PostTime"));
                                it.putExtra("PostContents", postDetail.getIntent().getStringExtra("PostContents"));
                                it.putExtra("PostTitle", postDetail.getIntent().getStringExtra("PostTitle"));
                                it.putExtra("PostKey", postDetail.getIntent().getStringExtra("PostKey"));
                                it.putExtra("PostIsunknown", postDetail.getIntent().getStringExtra("PostIsunknown"));
                                SettingComment.this.startActivity(it);
                                postDetail.finish();
                                finish();
                            }
                            else {
                                Toast.makeText(getApplicationContext(),"댓글 삭제에 실패했습니다.",Toast.LENGTH_SHORT).show();
                                finish();
                            }

                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }

                    }

                };
                DeleteCommentRequest DeleteRequest = new DeleteCommentRequest(Integer.parseInt(CommentID),responseListener);
                RequestQueue queue = Volley.newRequestQueue(SettingComment.this);
                queue.add(DeleteRequest);
            }
        });

    }
}
