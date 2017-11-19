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

public class ChangeRedate extends Activity {
    private ArrayAdapter adapter;
    private Spinner spinner;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changeredate);

        final int redateID = getIntent().getIntExtra("redateID", -1);
        if (redateID == -1) {
            finish();
        }

        final EditText redateText = (EditText) findViewById(R.id.redatetext);
        String text = getIntent().getStringExtra("redateText");
        redateText.setText(text.toCharArray(), 0, text.length());

        final Button backButton = (Button) findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  notice.setVisibility(View.GONE);
                Intent registerIntent = new Intent(ChangeRedate.this, Redate.class);
                registerIntent.putExtra("courseID", ChangeRedate.this.getIntent().getIntExtra("courseID", -1));
                registerIntent.putExtra("title", ChangeRedate.this.getIntent().getStringExtra("title"));
                ChangeRedate.this.startActivity(registerIntent);
                finish();
            }
        });

        Button okButton = (Button) findViewById(R.id.okButton);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String text = redateText.getText().toString();


                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(ChangeRedate.this);
                                dialog = builder.setMessage("댓글 수정에 성공했습니다.")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();
                                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                    @Override
                                    public void onDismiss(DialogInterface dialog) {
                                        Intent registerIntent = new Intent(ChangeRedate.this, Redate.class);
                                        registerIntent.putExtra("courseID", ChangeRedate.this.getIntent().getIntExtra("courseID", -1));
                                        registerIntent.putExtra("title", ChangeRedate.this.getIntent().getStringExtra("title"));
                                        ChangeRedate.this.startActivity(registerIntent);
                                        finish();
                                    }
                                });
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(ChangeRedate.this);
                                dialog = builder.setMessage("댓글 수정에 실패했습니다.")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                EditRedateFragmentRequest editFragmentRequest = new EditRedateFragmentRequest(redateID, UserID.getUserID(), text, responseListener);
                RequestQueue queue = Volley.newRequestQueue(ChangeRedate.this);
                queue.add(editFragmentRequest);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }
}
