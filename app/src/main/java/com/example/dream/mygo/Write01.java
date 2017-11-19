package com.example.dream.mygo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.Calendar;

public class Write01 extends Activity {

    private ArrayAdapter adapter;
    private Spinner spinner;
    private String coursePwd;
    private int courseID;
    private int courseHit;
    private String courseReDate;
    private String courseTitle;
    private String courseStroy;
    private String courseWorry;
    private String userGender;
    private String courseDate1;
    private String userMS;
    private String WRIID;
    private String userID;
    private AlertDialog dialog;
    private boolean validate = false;
    int year_x,month_x,day_x;
    static final int DILOG_ID =0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write01);

        final java.util.Calendar cal = Calendar.getInstance();

        spinner = (Spinner)findViewById(R.id.worryText);
        adapter = ArrayAdapter.createFromResource(this, R.array.Worry, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        final EditText titleText= (EditText)findViewById(R.id.titleText);
        final EditText StroyText = (EditText)findViewById(R.id.StroyText);



        Button okButton = (Button)findViewById(R.id.okButton);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String courseTitle = titleText.getText().toString();
                String courseWorry = spinner.getSelectedItem().toString();
                String courseStroy = StroyText.getText().toString();





                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(Write01.this);
                                dialog = builder.setMessage("Gom민 등록에 성공했습니다.")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();
                                Intent registerIntent = new Intent(Write01.this, MainActivity.class);
                                Write01.this.startActivity(registerIntent);
                                finish();

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(Write01.this);
                                dialog = builder.setMessage("Gom민 등록에 실패했습니다.")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();
                            }

                        } catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                };
                    WriteFragmentRequest writeFragmentRequest = new WriteFragmentRequest(userID, courseTitle, courseStroy, courseWorry, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(Write01.this);
                    queue.add(writeFragmentRequest);
            }
        });

        final Button backButton = (Button)findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //  notice.setVisibility(View.GONE);
                Intent registerIntent = new Intent(Write01.this, MainActivity.class);
                Write01.this.startActivity(registerIntent);
                finish();
            }
        });


    }
    @Override
    protected  void onStop(){
        super.onStop();
        if(dialog != null)
        {
            dialog.dismiss();
            dialog = null;
        }
    }
}
