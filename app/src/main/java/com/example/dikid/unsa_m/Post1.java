package com.example.dikid.unsa_m;

import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
        import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Post1 extends AppCompatActivity {

    ArrayList<PostData> PostList = new ArrayList<PostData>();
    ArrayList<PostData> SaveList = new ArrayList<PostData>();
    RecyclerView recyclerView;
    PostRecyclerAdapeter recyclerAdapter;
    JSONObject jsonObject;
    JSONArray jsonArray;
    BackgroundTaskOfPost backgroundTaskOfPost=new BackgroundTaskOfPost();
    public static Post1 post1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post1);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);

        post1=this;

//데이터 수신
        backgroundTaskOfPost.execute();
//fab 버튼
        final ImageButton Add = (ImageButton) findViewById(R.id.AddBT);
        final ImageButton SyncBT = (ImageButton) findViewById(R.id.SyncBT);
        final ImageButton BackBT = (ImageButton)findViewById(R.id.Post1Back);

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent makepost = new Intent(Post1.this, MakePost.class);
                Post1.this.startActivity(makepost);
                finish();
            }
        });

        SyncBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent it= new Intent(Post1.this,Post1.class);
                Post1.this.startActivity(it);
                finish();
                Snackbar.make(view, "Sync All Post", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        BackBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Post1.this,Main2Activity.class);
                Post1.this.startActivity(it);
                finish();
            }
        });
    }
    @Override
    public void onRestart(){
        super.onRestart();
//        recyclerAdapter.notifyDataSetChanged();
    }

    public void BindingData(String Data){
            try{
                jsonObject = new JSONObject(Data);
                jsonArray =jsonObject.getJSONArray("response");
                int count =0;
                String PostID, PostField,PostIsUnknown, WriterName, WriterNum,WriterID,PostTitle,PostContents,PostIsvisible,PostTime;

                while(count<jsonArray.length()){
                    JSONObject object = jsonArray.getJSONObject(count);
                    PostID= object.getString("PostID");
                    PostField= object.getString("PostField");
                    PostIsUnknown= object.getString("PostIsUnknown");
                    WriterName= object.getString("WriterName");
                    WriterNum= object.getString("WriterNum");
                    WriterID= object.getString("WriterID");
                    PostTitle = object.getString("PostTitle");
                    PostContents = object.getString("PostContents");
                    PostIsvisible = object.getString("PostIsvisible");
                    PostTime= object.getString("PostTime");
                    PostData post = new PostData(WriterName,WriterNum,WriterID,PostTitle,PostContents,
                            Integer.parseInt(PostIsUnknown),Integer.parseInt(PostIsvisible),Integer.parseInt(PostField),Integer.parseInt(PostID),PostTime);
                    if(post.getPostIsvisible()==1) {
                        PostList.add(post);
                        SaveList.add(post);
                    }
                    count++;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    public void Posts(){
            recyclerAdapter = new PostRecyclerAdapeter(PostList,SaveList);
            recyclerView = (RecyclerView)findViewById(R.id.Post1List);
            recyclerView.setAdapter(recyclerAdapter);
            LinearLayoutManager llm = new LinearLayoutManager(this);
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(llm);
            recyclerAdapter.notifyDataSetChanged();
//            recyclerView.setOnLongClickListener(new View.OnLongClickListener(){
//                @Override
//                public boolean onLongClick(View v) {
//                    return false;
//                }
//            });
        }

     class BackgroundTaskOfPost extends AsyncTask<Void, Void, String>
    {
        String target;

        @Override
        protected void onPreExecute(){
//        target="http://192.168.35.188/phpMyAdmin/PostList.php";
            target="http://58.233.6.24/phpMyAdmin/PostList.php";
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
            backgroundTaskOfPost.execute();
        }
        @Override
        public void onPostExecute(String result){
            BindingData(result);
            Posts();
        }
    }
}
