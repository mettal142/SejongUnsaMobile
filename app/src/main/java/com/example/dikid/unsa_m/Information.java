package com.example.dikid.unsa_m;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class Information extends Activity {

    static boolean dontagain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_information);

        final SharedPreferences Info = getSharedPreferences("Info", 0);
        final SharedPreferences.Editor editor = Info.edit();

        dontagain= Info.getBoolean("again",true);

        TextView Infotext = (TextView)findViewById(R.id.InfoText);

        Infotext.setText("안녕하세요. 멘토링 운영팀입니다. 멘토링 어플은 효과적으로 '온라인 멘토링'을 진행하기 위해 만든 어플입니다. \n" +
                "'온라인 멘토링'뿐만 아니라 세종운사 재학생, 졸업생들이 자유롭게 교류할 수 있는 장소가 되었으면 합니다.\n" +
                "\n" +
                "\n" +
                "* 멘토링 어플은 동아리 구성원으로 인증을 받은 분들에 한하여 이용가능하며, 개인 프로필(실명,기수,학과 등)을 정확히 입력해주시기 바랍니다. \n" +
                "\n" +
                "* 코드인증을 통해 회원가입 절차를 거치지만 부정확한 프로필로 활동시 관리자에 의해 차단될 수 있습니다.");

        Button CloseBT = (Button)findViewById(R.id.InfoClose);
        final CheckBox DontAgain = (CheckBox)findViewById(R.id.Again);



        CloseBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(DontAgain.isChecked()) {
                    editor.putBoolean("again", false);
                    editor.commit();
                }
                finish();
            }
        });
    }
}
