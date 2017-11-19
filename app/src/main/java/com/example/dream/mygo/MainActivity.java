package com.example.dream.mygo;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends Activity {
    public static String userID;
    public static Activity mActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        final LinearLayout notice =(LinearLayout)findViewById(R.id.notice);
        final Button homebutton=(Button)findViewById(R.id.homebutton);
        homebutton.setBackgroundColor(getResources().getColor(R.color.colorpont3));
        final ImageButton image01 =(ImageButton)findViewById(R.id.f01);
        final Button writebutton =(Button)findViewById(R.id.writebutton);
        userID =getIntent().getStringExtra("userID");

        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(MainActivity.this, MainActivity.class);
                MainActivity.this.startActivity(registerIntent);
                finish();
            }
        });

        final Button logoutbutton=(Button)findViewById(R.id.Logoutbutton);
        logoutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutbutton.setBackgroundColor(getResources().getColor(R.color.colorpont3));
                Intent registerIntent = new Intent(MainActivity.this, LoginActivity.class);
                MainActivity.this.startActivity(registerIntent);
                finish();
            }
        });


        Button gombutton = (Button) findViewById(R.id.gombutton);
        gombutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notice.setVisibility(View.GONE);
                FragmentManager fragmentManager = mActivity.getFragmentManager();
                FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, new Course());
                fragmentTransaction.commit();
            }
        });
        image01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notice.setVisibility(View.GONE);
                FragmentManager fragmentManager = mActivity.getFragmentManager();
                FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, new Notice1());
                fragmentTransaction.commit();
            }
        });
        writebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notice.setVisibility(View.GONE);
                Intent registerIntent = new Intent(MainActivity.this, Write01.class);
                MainActivity.this.startActivity(registerIntent);
                finish();
            }
        });

    }
    private  long lastTimeBackPressed;
        @Override
        public void onBackPressed(){
            if(System.currentTimeMillis() - lastTimeBackPressed<1500)
            {
                finish();
                return;
            }
            Toast.makeText(this,"'뒤로' 버튼을 한번 더 눌러 종료합니다.",Toast.LENGTH_SHORT);
            lastTimeBackPressed = System.currentTimeMillis();
        }
}
