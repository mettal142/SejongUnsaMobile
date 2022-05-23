package com.example.dikid.unsa_m;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.example.dikid.unsa_m.MainActivity.MyID;

public class PostDetail extends AppCompatActivity {

    int cmtUnknown;
    ArrayList<CommentData> CommentList = new ArrayList<CommentData>();
    ArrayList<CommentData> SaveList = new ArrayList<CommentData>();
    RecyclerView recyclerView;
    CommentRecyclerAdapter recyclerAdapter;
    JSONObject jsonObject;
    JSONArray jsonArray;
    BackgroundTaskOfComment backgroundTaskOfComment = new BackgroundTaskOfComment();
    String PostKey;
    public static PostDetail PD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_post_detail);

        PD=this;
        final TextView PostUserName,PostTitle,PostContents,PostTime;
        final EditText CommentText;
        final ImageButton CommentBT, DeleteComment;
        final CheckBox CommentUnknown;

        PostKey = getIntent().getStringExtra("PostKey");

        PostTitle = (TextView)findViewById(R.id.DetailTitle);
        PostUserName = (TextView)findViewById(R.id.DetailPostName);
        PostTime= (TextView)findViewById(R.id.DetailTime);
        PostContents= (TextView)findViewById(R.id.DetailContents);

        CommentText = (EditText)findViewById(R.id.CommentPT);
        CommentBT = (ImageButton) findViewById(R.id.CommentBT);
        CommentUnknown=(CheckBox)findViewById(R.id.CommentUnknown);

        PostTitle.setText(getIntent().getStringExtra("PostTitle"));

        if(Integer.parseInt(getIntent().getStringExtra("PostIsunknown"))==1)
            PostUserName.setText("익명");
        else
            PostUserName.setText(getIntent().getStringExtra("PostUserNum")+"기 "+getIntent().getStringExtra("PostUserName"));

        PostTime.setText(getIntent().getStringExtra("PostTime"));
        PostContents.setText(getIntent().getStringExtra("PostContents"));

        ImageButton BackBT = (ImageButton)findViewById(R.id.BackBT);
        BackBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        backgroundTaskOfComment.execute();

//댓글

        CommentBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CommentText.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"댓글의 내용을 입력해주세요.",Toast.LENGTH_SHORT).show();
                }
                else {
                    String Comment = CommentText.getText().toString();

                    CommentBT.setClickable(false);

                    if (CommentUnknown.isChecked()) {
                        cmtUnknown = 1;
                    } else {
                        cmtUnknown = 0;
                    }

                    //TimeFormat
                    long now = System.currentTimeMillis();
                    Date date = new Date(now);
                    SimpleDateFormat sdfNow = new SimpleDateFormat("MM월dd일HH시mm분");
                    String CommentTime = sdfNow.format(date);

                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if (success) {
                                    Toast.makeText(getApplicationContext(), "새로운 댓글이 등록되었습니다.", Toast.LENGTH_SHORT).show();
                                    Intent it = new Intent(PostDetail.this, PostDetail.class);
                                    it.putExtra("PostUserNum", getIntent().getStringExtra("PostUserNum"));
                                    it.putExtra("PostUserName", getIntent().getStringExtra("PostUserName"));
                                    it.putExtra("PostTime", getIntent().getStringExtra("PostTime"));
                                    it.putExtra("PostContents", getIntent().getStringExtra("PostContents"));
                                    it.putExtra("PostTitle", getIntent().getStringExtra("PostTitle"));
                                    it.putExtra("PostKey", getIntent().getStringExtra("PostKey"));
                                    it.putExtra("PostIsunknown", getIntent().getStringExtra("PostIsunknown"));
                                    PostDetail.this.startActivity(it);
                                    finish();
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(PostDetail.this);
                                    builder.setMessage("댓글 등록에 실패했습니다.")
                                            .setNegativeButton("다시시도", null)
                                            .create()
                                            .show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    CommentRequest commentRequest = new CommentRequest(Integer.parseInt(PostKey), Comment, 1, cmtUnknown, CommentTime, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(PostDetail.this);
                    queue.add(commentRequest);
                }
            }
        });

    }
    public void BindingData(String Data){
        try{
            jsonObject = new JSONObject(Data);
            jsonArray =jsonObject.getJSONArray("response");
            int count =0;
            String CommentID, PostID,CommentIsUnknown, WriterName, WriterNum,WriterID,CommentIsvisible,CommentTime,CommentContents;

            while(count<jsonArray.length()){
                JSONObject object = jsonArray.getJSONObject(count);
                CommentID= object.getString("CommentID");
                PostID= object.getString("PostID");
                CommentIsUnknown= object.getString("CommentIsUnknown");
                WriterName= object.getString("WriterName");
                WriterNum= object.getString("WriterNum");
                WriterID= object.getString("WriterID");
                CommentIsvisible = object.getString("CommentIsvisible");
                CommentTime = object.getString("CommentTime");
                CommentContents = object.getString("CommentContents");
                CommentData comment = new CommentData(Integer.parseInt(CommentID), Integer.parseInt(PostID),Integer.parseInt(CommentIsUnknown), WriterName, WriterNum,WriterID,
                        Integer.parseInt(CommentIsvisible),CommentTime,CommentContents);
               if(comment.getCommentIsvisible()==1) {
                   CommentList.add(comment);
                   SaveList.add(comment);
               }
                count++;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
//댓글
    public void Posts(){

        CommentList.clear();
        for(int i =0; i<SaveList.size(); i++){
            if(SaveList.get(i).getPostID()==Integer.parseInt(PostKey)){
                CommentList.add(SaveList.get(i));
            }
        }
        recyclerAdapter = new CommentRecyclerAdapter(CommentList,SaveList);
        recyclerView = (RecyclerView)findViewById(R.id.CommentList);
        recyclerView.setAdapter(recyclerAdapter);

        recyclerView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.i(getClass().getName(),"sㄴ어ㅏㅗ라넝라ㅣㄴ");
                return true;
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically() { // 세로스크롤 막기
                return false;
            }

            @Override
            public boolean canScrollHorizontally() { //가로 스크롤막기
                return false;
            }
        });

//        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerAdapter.notifyDataSetChanged();

    }

    class BackgroundTaskOfComment extends AsyncTask<Void, Void, String>
    {
        String target;

        @Override
        protected void onPreExecute(){
//        target="http://192.168.35.188/phpMyAdmin/CommentList.php";
            target="http://58.233.6.24/phpMyAdmin/CommentList.php";
        }
        @Override
        protected String doInBackground(Void...voids){
            try{
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                InputStream inputStream= httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while ((temp=bufferedReader.readLine())!=null){
                    stringBuilder.append(temp+"\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }
        @Override
        public void onProgressUpdate(Void...values){
            super.onProgressUpdate(values);
            backgroundTaskOfComment.execute();
        }
        @Override
        public void onPostExecute(String result){
            BindingData(result);
            Posts();
        }
    }

}

