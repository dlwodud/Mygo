package com.example.dream.mygo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class Change extends Activity {
    private ArrayAdapter adapter;
    private Spinner spinner;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);

        final int courseID = getIntent().getIntExtra("courseID", -1);
        if(courseID == -1) {
            finish();
        }

        spinner = (Spinner)findViewById(R.id.worryText);
        adapter = ArrayAdapter.createFromResource(this, R.array.Worry, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        String worry = getIntent().getStringExtra("worry");
        spinner.setSelection(adapter.getPosition(worry));

        final EditText titleText= (EditText)findViewById(R.id.titleText);
        String title = getIntent().getStringExtra("title");
        titleText.setText(title.toCharArray(),0,title.length());

        final EditText stroyText = (EditText)findViewById(R.id.StroyText);
        String stroy = getIntent().getStringExtra("stroy");
        stroyText.setText(stroy.toCharArray(),0,stroy.length());

        final Button backButton = (Button)findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  notice.setVisibility(View.GONE);
                Intent registerIntent = new Intent(Change.this, MainActivity.class);
                Change.this.startActivity(registerIntent);
                finish();
            }
        });

        Button okButton = (Button)findViewById(R.id.okButton);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String courseTitle = titleText.getText().toString();
                String courseWorry = spinner.getSelectedItem().toString();
                String courseStroy = stroyText.getText().toString();


                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(Change.this);
                                dialog = builder.setMessage("Gom민 수정에 성공했습니다.")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();
                                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                    @Override
                                    public void onDismiss(DialogInterface dialog) {
                                        Intent registerIntent = new Intent(Change.this, MainActivity.class);
                                        Change.this.startActivity(registerIntent);
                                        finish();
                                    }
                                });
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(Change.this);
                                dialog = builder.setMessage("Gom민 수정에 실패했습니다.")
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
                EditFragmentRequest editFragmentRequest = new EditFragmentRequest(courseID, UserID.getUserID(), courseTitle, courseStroy, courseWorry, responseListener);
                RequestQueue queue = Volley.newRequestQueue(Change.this);
                queue.add(editFragmentRequest);
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
