package com.example.blrlight_908_3;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class WelActivity extends AppCompatActivity {
    private View go=null;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_wel);
        ActionBar acb=getSupportActionBar();
        if(acb!=null){
            acb.hide();
        }

        go=findViewById(R.id.go);
        SharedPreferences userInfo = getSharedPreferences("start", MODE_PRIVATE);
        SharedPreferences.Editor editor = userInfo.edit();
        int x;//获取记录启动次数的值，若获取不到就默认为1
                x=userInfo.getInt("start",1);
        //判断第几次启动
        if(x==1)
        {//为启动数加一
                x++;
            editor.putInt("start",x);
            editor.commit();

        }
        else {
//若不是第一次登录就直接跳转MainActivity
            x++;
            editor.putInt("start",x);
            editor.commit();
            Intent it=new Intent();
            it.setClass(WelActivity.this,MainActivity.class);
            startActivity(it);
            WelActivity.this.finish();
        }
//欢迎界面进入应用的按钮
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent();
                it.setClass(WelActivity.this,MainActivity.class);
                startActivity(it);
                WelActivity.this.finish();


            }});
    }
}