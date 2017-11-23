package com.example.dream.mygo;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class Change extends Activity {
    private ArrayAdapter adapter;
    private Spinner spinner;
    private AlertDialog dialog;
    private ArrayAdapter adapter2;
    private Spinner spinner2;
    RadioGroup genderGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);

        final int courseID = getIntent().getIntExtra("courseID", -1);
        if (courseID == -1) {
            finish();
        }

        spinner = (Spinner) findViewById(R.id.worryText);
        adapter = ArrayAdapter.createFromResource(this, R.array.Worry, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner2 = (Spinner) findViewById(R.id.ageText);
        adapter2 = ArrayAdapter.createFromResource(this, R.array.Ages, android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        String worry = getIntent().getStringExtra("worry");
        String age =getIntent().getStringExtra("age");
        spinner2.setSelection(adapter2.getPosition(age));

        spinner.setSelection(adapter.getPosition(worry));
        genderGroup = (RadioGroup) findViewById(R.id.genderGroup);
        String gender = getIntent().getStringExtra("gender");
        if (gender.equals("남성")) {
            genderGroup.check(R.id.genderMan);
        } else {
            genderGroup.check(R.id.genderWoman);
        }

        final EditText titleText = (EditText) findViewById(R.id.titleText);
        String title = getIntent().getStringExtra("title");
        titleText.setText(title.toCharArray(), 0, title.length());

        final EditText StroyText = (EditText) findViewById(R.id.StroyText);
        String stroy = getIntent().getStringExtra("stroy");
        StroyText.setText(stroy.toCharArray(), 0, stroy.length());
        final EditText timeText = (EditText) findViewById(R.id.timeText);
        String time = getIntent().getStringExtra("time");
        time = time.substring(0, time.indexOf(':'));
        timeText.setText(time.toCharArray(), 0, time.length());

        final Button backButton = (Button) findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  notice.setVisibility(View.GONE);
                finish();
            }
        });

        Button okButton = (Button) findViewById(R.id.okButton);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //     bindService(intent, connection, BIND_AUTO_CREATE);
                //   running =true;
                //  new Thread(new GetCountThread()).start();
                String courseTitle = titleText.getText().toString();
                String courseWorry = spinner.getSelectedItem().toString();
                String courseStroy = StroyText.getText().toString();
                int genderGroupID = genderGroup.getCheckedRadioButtonId();
                String targetGender = ((RadioButton) findViewById(genderGroupID)).getText().toString();
                String targetAges = spinner2.getSelectedItem().toString();
                int timeToDelete = 72;
                if (!timeText.getText().toString().isEmpty()) {
                    timeToDelete = Integer.parseInt(timeText.getText().toString());
                }


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
                                Change.this.finish();

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(Change.this);
                                dialog = builder.setMessage("Gom민 수정에 실패했습니다.")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                if (courseTitle.isEmpty() || courseStroy.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Change.this);
                    dialog = builder.setMessage("최소 한글자 이상 입력하세요.")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                } else {
                    WriteFragmentRequest writeFragmentRequest = new WriteFragmentRequest(UserID.getUserID(), courseTitle, courseStroy, targetGender, targetAges, courseWorry, timeToDelete, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(Change.this);
                    queue.add(writeFragmentRequest);
                }
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
