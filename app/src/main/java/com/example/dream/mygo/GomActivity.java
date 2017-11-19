package com.example.dream.mygo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class GomActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gom);
        Button homebutton = (Button) findViewById(R.id.homebutton);
        final LinearLayout notice =(LinearLayout)findViewById(R.id.notice);
        final Button gombutton=(Button)findViewById(R.id.gombutton);
        notice.setVisibility(View.GONE);
        gombutton.setBackgroundColor(getResources().getColor(R.color.colorpont3));

        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(GomActivity.this, MainActivity.class);
                GomActivity.this.startActivity(registerIntent);
                finish();
            }
        });
        final Button logoutbutton=(Button)findViewById(R.id.Logoutbutton);
        logoutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutbutton.setBackgroundColor(getResources().getColor(R.color.colorpont3));
                Intent registerIntent = new Intent(GomActivity.this, LoginActivity.class);
                GomActivity.this.startActivity(registerIntent);
                finish();
            }
        });

        gombutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(GomActivity.this, GomActivity.class);
                GomActivity.this.startActivity(registerIntent);
                finish();
            }
        });


    }
}
