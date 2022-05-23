package com.example.dikid.unsa_m;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main2Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private long BackPresstime= 0;
    static Main2Activity main2Activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        main2Activity=this;
//버튼 Start
        Button PostBT = (Button)findViewById(R.id.Post);
        Button FindBT = (Button)findViewById(R.id.FindUser);
        Button WebBT = (Button)findViewById(R.id.Web);

        SharedPreferences Again = getSharedPreferences("Info", 0);
        SharedPreferences.Editor editor = Again.edit();

        PostBT.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent Post =  new Intent(Main2Activity.this, Post1.class);
                Main2Activity.this.startActivity(Post);
            }
        });

        FindBT.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                new BackgroundTask().execute();
            }
        });

        WebBT.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent WebIntent = new Intent(Main2Activity.this, Website.class);
                Main2Activity.this.startActivity(WebIntent);
            }
        });
//End
        if(Again.getBoolean("again",true)){
            Intent it = new Intent(Main2Activity.this,Information.class);
            Main2Activity.this.startActivity(it);
        }
//네비게이션 바 Start
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);

        TextView nav_Name = (TextView)headerView.findViewById(R.id.nav_Name);
        TextView nav_Dep = (TextView)headerView.findViewById(R.id.nav_Dep);
        TextView nav_Job = (TextView)headerView.findViewById(R.id.nav_Job);
        TextView nav_Mail = (TextView)headerView.findViewById(R.id.nav_Mail);

        nav_Name.setText(MainActivity.MyNum+ "기 "+ MainActivity.MyName);
        nav_Dep.setText(MainActivity.MyDep);
        nav_Job.setText(MainActivity.MyJob);
        nav_Mail.setText(MainActivity.MyMail);

    }
//End

//뒤로가기 Start
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(System.currentTimeMillis()-BackPresstime>=2000){
                BackPresstime=System.currentTimeMillis();
                Toast.makeText(getApplicationContext(),"뒤로 버튼을 한번 더 누르면 종료합니다.",Toast.LENGTH_SHORT).show();
            }else if(System.currentTimeMillis()-BackPresstime<2000){
                moveTaskToBack(true);
                finish();
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        }
    }
//End
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
//네비게이션 버튼 Start
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


          if (id == R.id.nav_new) {
            Intent it = new Intent(Main2Activity.this,Information.class);
            Main2Activity.this.startActivity(it);

        } else if (id == R.id.nav_mypost) {
              Toast.makeText(getApplicationContext(),"post",Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_myinfo) {
              Intent it = new Intent(Main2Activity.this,ReMyInfo.class);
              Main2Activity.this.startActivity(it);
        } else if (id == R.id.nav_grade) {
              Toast.makeText(getApplicationContext(),"grade",Toast.LENGTH_SHORT).show();

        }else if (id == R.id.nav_logout) {

              SharedPreferences auto = getSharedPreferences("auto", 0);
              SharedPreferences.Editor editor = auto.edit();

              MainActivity.MyID=null;
              MainActivity.MyName=null;
              MainActivity.MyNum=null;
              MainActivity.MyDep=null;
              MainActivity.MyPoint=null;
              MainActivity.MyMail=null;
              MainActivity.MyJob=null;
              MainActivity.MyInfo=null;

              editor.putString("savedID","");
              editor.putString("savedPassword","");
              editor.commit();

              Intent logout = new Intent(Main2Activity.this,MainActivity.class);
              Main2Activity.this.startActivity(logout);
              finish();
              Toast.makeText(getApplicationContext(),"안전하게 로그아웃 되었습니다.",Toast.LENGTH_SHORT).show();

          }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
//End

//유저정보 Start
    class BackgroundTask extends AsyncTask<Void, Void, String>
    {
        String target;

        @Override
        protected void onPreExecute(){
//            target="http://192.168.35.188/phpMyAdmin/List.php";
            target="http://58.233.6.24/phpMyAdmin/List.php";
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
        }
        @Override
        public void onPostExecute(String result){
            Intent FindIntent = new Intent(Main2Activity.this,Profile.class);
            FindIntent.putExtra("userList",result);
            Main2Activity.this.startActivity(FindIntent);
        }
    }
//End

}
