package com.example.dikid.unsa_m;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
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

public class SettingUser extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_setting_user);

        final Vibrator vibrator= (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        final String UserID = getIntent().getStringExtra("userID");
        vibrator.vibrate(30);
        Button ReUser = (Button)findViewById(R.id.ReUser);
        Button DelUser = (Button)findViewById(R.id.DeleteUser);

        DelUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Response.Listener<String> responseListener = new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                Toast.makeText(getApplicationContext(),UserID+"가 삭제되었습니다.",Toast.LENGTH_SHORT).show();
//                                Post1 p1 = (Post1)Post1.post1;
//                                p1.finish();
//                                Intent it = new Intent (SettingUser.this,Post1.class);
//                                SettingUser.this.startActivity(it);
                                Profile profile = (Profile)Profile.ct_p;
                                profile.finish();
                                Intent it = new Intent(SettingUser.this,Profile.class);
                                SettingUser.this.startActivity(it);
                                finish();
                            }
                            else {
                                Toast.makeText(getApplicationContext(),"유저 삭제에 실패했습니다.",Toast.LENGTH_SHORT).show();
                                finish();
                            }

                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }

                    }

                };
                DeleteUserRequest DeleteRequest = new DeleteUserRequest(UserID,responseListener);
                RequestQueue queue = Volley.newRequestQueue(SettingUser.this);
                queue.add(DeleteRequest);
            }
        });

    }
}
